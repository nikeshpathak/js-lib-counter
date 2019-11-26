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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class JslibcounterApplicationTests {

	@Autowired
	JsLibCounterUtils jsLibCounterUtils;

	static Logger logger = LoggerFactory.getLogger(JslibcounterApplicationTests.class);

	@Test
	void getListOfJsLibUsedOnWebUsingExecutorServiceTest() throws InterruptedException {
		List<String> listOfWebLink = new ArrayList<>();
		listOfWebLink.add("https://www.educative.io/edpresso/how-to-write-hello-world-in-java?https://www.educative.io/courses/grokking-the-object-oriented-design-interview?aid=5082902844932096&utm_source=google&utm_medium=cpc&utm_campaign=blog-dynamic&gclid=EAIaIQobChMIj6v8weqC5gIVmAVyCh3vvQUhEAAYASAAEgKvpfD_BwE");
		listOfWebLink.add("https://www.geeksforgeeks.org/beginning-java-programming-with-hello-world-example/");
		listOfWebLink.add("https://introcs.cs.princeton.edu/java/11hello/HelloWorld.java.html");
		listOfWebLink.add("https://www.programiz.com/java-programming/hello-world");
		listOfWebLink.add("https://www.javatpoint.com/simple-program-of-java");
		List<String> listOfLibrary = jsLibCounterUtils.getListOfJsLibUsedOnWebUsingExecutorService(listOfWebLink);
        System.out.println("=================================");
        System.out.println("Used library count : "+ listOfLibrary.size());
        listOfLibrary.forEach(s ->{
			logger.info(s);
		});
		Assertions.assertNotNull(listOfLibrary);
	}

	@Test
	void mostUsedLibraryTest(){
		List<String> libsList = Arrays.asList("require.min.js", "platform.js", "jquery.js?ver=1.12.4", "jquery-migrate.min.js?ver=1.4.1", "gfg.min.js?ver=10.31", "cookieconsent.min.js", "adsbygoogle.js", "materialize.min.js", "adsbygoogle.js", "adsbygoogle.js", "adsbygoogle.js", "adsbygoogle.js", "adsbygoogle.js", "adsbygoogle.js", "adsbygoogle.js", "wp-embed.min.js?ver=4.9.8", "monetization.js", "ScrollMagic.min.js", "jquery.js?ver=1.12.4-wp", "jquery-migrate.min.js?ver=1.4.1", "navigation.js?ver=1571101192", "skip-link-focus-fix.js?ver=20151215", "ScrollMagic.min.js?ver=1.0.0", "debug.addIndicators.min.js?ver=1.0.0", "comment-reply.min.js?ver=5.2.4", "custom.js?ver=1571101192", "sassy-social-share-public.js?ver=3.3.3", "wp-embed.min.js?ver=5.2.4", "marketoforms2.min.js?ver=5.2.4", "cookieconsent.js?ver=5.2.4");
		String mostUsedText = jsLibCounterUtils.mostUsedLibrary(libsList,5);
		logger.info(mostUsedText);
		Assertions.assertNotNull(mostUsedText);
	}

	@Test
	void getListOfWebLibrary(){

		List<String> listOfLibrary = jsLibCounterUtils.getListOfWebLibrary("https://www.javatpoint.com/simple-program-of-java");
		Assertions.assertNotNull(listOfLibrary);

	}

}
