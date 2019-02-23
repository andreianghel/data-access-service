proof of concept project:

common part:

   ------
   | UI |
   ------
     |
------------      ----------       ---------------
|    UI    | <--- | Kafka  | <---  | Data Access |  
|  Service | ---> | Broker | --->  |   Service   |
------------      ----------       ---------------
                      |                   |
                ----------------   -----------------
                | User Actions |   | ElasticSearch |
                |   Analytics  |   -----------------
                ----------------
                		|
                -----------------
                | Some nosql db |
                ----------------- 



Project Name: collaborative online notepad 

Description: ensemble of microservices that offer a functionality of a notepad;
Async communication between the front end and backend, so as to facilitate multiple user usage;

Logical components:

-UI: for now, just a REST Api, a real gui will be made with thymeleaf or flask;
-> future development, the ui will use web sockets or somehow pool for new messages and display them.

-UI service: gets requests from the user and puts the requests on a kafka topic; receives messages from the broker and sends them to the gui;

-messaging broker: used to move messages from components;

-data access service: get messages and stores entries, query the ES, etc.; pushes responses back to the topic;

-persistence: used mainly for persistence and the fuzzy query feature;

- (future) user action analytics: collects data about the users actions and stores them to be later procesed;



Under the hood: clasical Netflix cloud components: Eureka for service discovery, Zuul for routing and filtering, Spring boot for everything, Kafka for messaging, ElasticSearch for persistence and quering;

TO DO:

[X] add service discovery and api gateway

[] implement the services/expose basic rest;

[]

[] Dockerize every component;

[] docker stack all;

[] implement the gui;

[] deploy on cloud;

[] enrich the user posible actions

[] create and integrate the user analytics





specific part:
