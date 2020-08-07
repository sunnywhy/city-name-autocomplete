# city-name-autocomplete

# Java version 8

# How to run the project
Method 1: Import the project into Intellij, directly run the main method in file AutocompleteApplication.java

Method 2: after ./gradlew build, an executable war will be generated under "build/libs". It can be executed by "java -jar ./build/libs/autocomplete-0.0.1-SNAPSHOT.jar"

Method 3: run ./gradlew bootRun 

# How to test the API
After running the application locally, the API endpoint is "http://localhost:8080/suggestions"
1. Since the query parameter "q" is required, that means we have to include this parameter in the url, like: http://localhost:8080/suggestions?q=Londo
2. The latitude/longitude parameters are optional, we can add it to improve the city scores, ex: http://localhost:8080/suggestions?q=Londo&latitude=43.70011&longitude=-79.4163

# Bonus questions:
1. Can you think of more scoring parameters to make the suggestions better?
A: I) When I checking the city data from Gennames, I found there is a field called "popularity". We can use it to calculate the city score too. 
   II) If we implemented the API-KEY per user feature in question 2, we can track the searching history for the current user(autocomplete will give user a list of options, then we track the option user selected every time). Then we can maintain a struture like: user id - searching term - selected city - frenquency. We can use the history data to calcualte score too.
   III) If we don't have API-KEY feature, we still can use the history to help to improve the score, in this case, it's based on the hisotry of all users.
   
2. Sketch out a design for per user API keys and billing for our future city-suggestion-service startup.
A: The general idea is when calling the API, include the API-KEY in the http request header, then we use a filter/interceptor to get the API Key, then validate the API key and get the user information from it. After we having the user information, we can record how many times the user called the service and bill them.
The biggest concern here is how to design/generate the API key, easiest solution is adding a new field called "api-key" in "users" table, then use some random algorithems to generate a key. This way is really simple and fast, however it's not safe since it will not expire, once hacker get the Key, they can use it forever.
The proper way is building another Auth service, the Auth service uses user information + current timestamp + encryption algorithm to generate a token, with a short expiring time. In the filter/interceptor of the autocomplete service, use the same encryption algorithm to extract user information and timestamp from the token, if validate, continue processing, otherwise, return error message with correct http error code.
