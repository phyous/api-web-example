# Java Webscraper API example

This project is an example of how one can use modern java libraries to quickly & easily create a web api for any existing 3rd party web page (Similar to what sites like [kimonolabs](https://www.kimonolabs.com) and [import.io](https://www.import.io/) do).

Specifically, we're going to create a web service that scrapes [techmeme.com](https://www.techmeme.com) to serve up tech headlines as json from any point in time. 

### Libraries used

* [Immutables](http://immutables.github.io/) --- Used to create our models. By using a handful of very powerful annotations along with code generation, we'll be able to create immutable objects and builders to represent our data models.
* [Jsoup](https://github.com/jhy/jsoup/) --- Used for retrieving html and parsing it. This is an older library that has stood the test of time. Simply pass in css selectors to get the relevant html sections needed.
* [Pippo](http://www.pippo.ro/) --- Used as our webserver. This is a relatively new webserver for java that combines a very simple interface with a minimal footprint and a high degree of customizability. Reminds me of a [Dropwizard](http://www.dropwizard.io/0.9.2/docs/) with a simpler interface.
* [Java 8](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) --- We'll be making use of Java 8 streams and optionals to process the incoming data. 

### Setup Instructions

1. Be sure to have java 8 & maven installed
2. Compile the source code: ```mvn package```
3. Run the server (defaults to port 8081): ```java -jar target/apiweb-1.0-SNAPSHOT.jar```
4. Make a request to the server. 
    * Let's get the tech headlines form new years day in 2015: ```http://localhost:8081/headlines?date=2015-01-01```
    * Let's get the tech headlines for today: ```http://localhost:8081/headlines```
    
### Sample output:
```json
[
{
reporter: "Sarah Frier",
source: "Bloomberg",
title: "Snapchat raises $485.6M at $10B+ valuation from 23 investors",
summary: "  —  Snapchat Raises $485.6 Million to Close Out Big Fundraising Year  —  Snapchat Inc., among a pack of elite technology startups that has attained a valuation of $10 billion or more, capped the year with a filing that disclosed it raised $485.6 million.",
url: "http://www.bloomberg.com/news/2015-01-01/snapchat-raises-485-6-million-to-close-out-big-fundraising-year.html"
},
{
reporter: "William Turton",
source: "The Daily Dot",
title: "U.K. police allegedly arrest Lizard Squad hacker",
summary: "… Lizard Squad took credit for the Dec. 25 distributed denial-of-service (DDoS) attacks against the PlayStation Network and Xbox Live.  DDoS attacks overwhelm a network with too much traffic, leaving targeted networks inaccessible for legitimate users.",
url: "http://www.dailydot.com/crime/lizard-squad-vinnie-omari-arrested/"
}
]
```