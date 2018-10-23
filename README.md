# Web Crawler for Scout 24

This is a web crawler which is designed for crawling any url which is provided to it. The Web crawler analyses the url and the web page received from the web server of the url and returns back a brief analysis of the internal and external URLs which were found inside the web page.

  - Enter a url which you want to analyse
  - The cralwer will crawl and analyse the web page
  - You will receive an analysis of the web page


### Technologies used

* Java 8
* Spring Boot 2.0.6
* Maven 3.5.3
* JSoup 1.11.3
* Swagger 2.9.2

### Installation

This Web crawler is a maven project, so it is necessary that you have maven installed on your machine.

##### Install the dependencies 

Open the shell and type

```sh
$ cd web-crawler
$ mvn clean install
```
The above command will build and install the dependencies along with that it will run the test cases for the application.
If you want to skip the tests.

```sh
mvn install -DskipTests
```
For starting the application

```sh
mvn spring-boot:run
```

### Design
The design of the application surrounds around the standard template followed by Microservices architecture.

The requirement was to build a web crawler which can analyse the contents on an HTML page. The contents which were of interest were :
 * The number of hypermedia links on the page
 * The classsification of the hypermedid links into :
 * Internal links 
 * External links
 * The availability, redirection status and error codes if any related to these links

The initial design decision was to save all these analysis data pertaining to the urls into the database, because pinging each and every document and verifying their status was a time consuming task. As per the requirement the responsiveness of the API was of importance. But later the decision to save the analysis data in the database was rejected by acknowledging the fact that the HTML content of the page can keep changing.
So it was decided to use Java's Executor Service concurrency mechanism to verify the status of all the links inside the HTML page.

The Architectural Layers which are involved are

* Controller - which will be having the REST API methods 
* Model - which will be holding the response data
* Service - where the document parsing and population of the model object happend
* Concurrency Task - the concurrent task which is used for polling the links parallel to achieve better API responsiveness
* Error Handlers - which handles the exceptions which occur while analysing the url and gracefully returns an appropriate status or sends them as part of the response.

### Development

There is only 1 API which is there for this web crawler which is:

#####   GET /api/webCrawler/v1/analyse 
It returns an analysis of the URL and the HTML content of the URL.

The API takes a url, which is sent as a query parameter, and passes it onto the Service which collects the HTML page of the URL and parses it. While parsing the page it will collect all the links and parallely processes the operation of collecting the link's related data.


### Constraints

Some constraints which were taken into consideration was the timeout of the links.
Initially it was found that the processing of the links will be a time consuming task, because some of the links will have a redirection policy and the redirection of these links happen after a certain timeout. So if the timeout was more the chances of link to get redirected was more and if the timeout was less the chances of the link getting redirected was less. 
##### Timeout for redirected links
If the timeout was reduced and kept to 10 milliseconds then none of the links were getting redirected and the API response time was less around 2 seconds. If a timeout of 100 ms was set then the no of links which were getting redirected was more, but the API response time was around 15 - 20 seconds. This number was arrived by testing with a familiar link, [Google].
To strike a balance between the redirection and the API responsiveness it was decided that a timeout of 50 millisecond will be only allowed for each link. 


### TODO
* More tests need to be added.

License
----

Apache License Version 2.0

[Google]: <https://www.google.com>
