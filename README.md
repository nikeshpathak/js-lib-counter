# Getting Started

A command line application that counts top JavaScripts libraries used in web​ ​pages​ ​found​ ​on​ ​Google.

###Build project
./gradlew clean build

###Execute project
java -jar <application jar> "search term" numberOfResult
####Example:
java -jar build/libs/jslibcounter-0.0.1-SNAPSHOT.jar "Hello world" 5

####output
![Alt text](output_result.png? "Output result")