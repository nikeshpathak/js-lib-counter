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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

@SpringBootApplication
public class JslibcounterApplication implements CommandLineRunner {

	static Logger logger = LoggerFactory.getLogger(JslibcounterApplication.class);
	public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_BLUE = "\u001B[34m";

	@Autowired
	JsLibCounterUtils jsLibCounterUtils;

	public static void main(String[] args) {
		SpringApplication.run(JslibcounterApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			if(args.length !=2){
				System.out.println(args.length);
				logger.error("Please enter valid command line argument e.g : java -jar build/libs/jslibcounter-0.0.1-SNAPSHOT.jar \"Hello world\" 5");
			} else {
				List<String> webLink = new ArrayList<>();
				String searchURL = GOOGLE_SEARCH_URL + "?q=" + args[0] + "&num=" + (Integer.parseInt(args[1]) + 1);
				Document doc = Jsoup.connect(searchURL).get();

				Elements elts = doc.getElementsByClass("rc");
				Elements results = elts.select("a");
				List<String> title = doc.getElementsByClass("S3Uucc").eachText();
				List<String> summery = doc.getElementsByClass("st").eachText();
				for (int i = 0; i < title.size(); i++) {
					System.out.println(ANSI_PURPLE + " " + title.get(i) + " " + ANSI_RESET);
					System.out.println(ANSI_GREEN + " " + summery.get(i) + " " + ANSI_RESET);
					System.out.println("---------------------------------------------------");
				}

				for (int i = 0, resultsSize = results.size(); i < resultsSize; i++) {
					Element result = results.get(i);
					String linkHref = result.attr("href");
					if (!linkHref.trim().equals("#") && linkHref.trim().startsWith("http")) {
						webLink.add(linkHref);
					}
				}
				List<String> listJsLib = jsLibCounterUtils.getListOfJsLibUsedOnWebUsingExecutorService(webLink);
				String mostUsedLib = jsLibCounterUtils.mostUsedLibrary(listJsLib, 5);
				System.out.println(String.format(ANSI_BLUE + "Most used top %d library:" + ANSI_RESET + " \n %s ", 5, mostUsedLib));
			}
		}catch (Exception e){
			logger.error(e.getMessage());
			if(e instanceof UnknownHostException){
				logger.error("Please check your internet connection!");
			}
			if(e instanceof InputMismatchException){
				logger.error("Please input valid number");
			}
		}
	}

}
