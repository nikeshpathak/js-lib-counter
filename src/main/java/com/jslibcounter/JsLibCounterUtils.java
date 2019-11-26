package com.jslibcounter;

/* Copyright 2019 Nikesh Pathak
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. */

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.counting;

@Component
public class JsLibCounterUtils {

    static Logger logger = LoggerFactory.getLogger(JsLibCounterUtils.class);

    /**
     * use this method to get list of js library used on web using executor service
     * @param webPageLinks
     * @return List<String> return list of library name
     * @throws InterruptedException
     */
     List<String> getListOfJsLibUsedOnWebUsingExecutorService(List<String> webPageLinks) throws InterruptedException {
        List<String> libraryName = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(webPageLinks.size() <= 15?webPageLinks.size():15);
        webPageLinks.stream().map(s -> executorService.submit(() -> {
            libraryName.addAll(getListOfWebLibrary(s));
        })).collect(Collectors.toList());
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        return libraryName;
    }

    /**
     * this will grouped most used library and return as String value.
     * @param listOfLibName list of lib name and max ordering we looking for.
     * @return return most used library name as String
     */
      String mostUsedLibrary(List<String> listOfLibName,int max){
        int counter = 0;
        StringBuilder stringBuilder = new StringBuilder();
        Map<String,Long> libCountMap =listOfLibName.stream().collect(Collectors.groupingBy(Function.identity(), counting()));
        Iterator iterator = libCountMap.entrySet().stream().sorted(Collections.reverseOrder(comparingByValue())).iterator();
        while (iterator.hasNext() &&  counter++<max){
            stringBuilder.append(iterator.next().toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * this will return list of js library when pass single website link
     * @param webLink web link string as parameter
     * @return List of string
     */
      List<String> getListOfWebLibrary(String webLink){
        try {
            Document doc = Jsoup.connect(webLink).get();
            List<String> results = doc.getElementsByTag("script").eachAttr("src");
            return results.stream().map(s -> s.substring(s.lastIndexOf('/') + 1)).filter(s->s.contains(".js")).collect(Collectors.toList());
        }catch (IOException e) {
            logger.error(e.getMessage());
            if(e instanceof HttpStatusException){
                if(((HttpStatusException) e).getStatusCode() == 999) {
                    logger.error("Access not allowed by web host");
                }
            }
        }
        return null;
    }
}
