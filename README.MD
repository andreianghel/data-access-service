proof of concept project:

common part:

   ------
   | UI |
   ------
     |
------------      ----------       ---------------
|    UI    | <--- | Kafka  | <---  | Data Access |  
|  Service | ---> | Broker | --->  |   Service   |
------------      ----------       ---------------
										 |
								  -----------------
   								  | ElasticSearch |
   								  -----------------

Project Name: online notepad
Description: ensemble of microservices that offer a functionality of a notepad;
Logical ocmponents:
-UI: for now, just a REST Api, a real gui will be made with thymeleaf or flask;
-UI service: gets requests from the user and puts the requests on a kafka topic; receives messages from the broker and sends them to the gui;
-messaging broker: used to move messages from components;
-data access service: get messages and stores entries, query the ES, etc.; pushes responses back to the topic;
-persistence: used mainly for persistence and the fuzzy query feature;


Under the hood: clasical Netflix cloud components: Eureka for service discovery, Zuul for routing and filtering, Spring boot for everything, Kafka for messaging, ElasticSearch for persistence and quering;

TO DO:
[X] add service discovery and api gateway
[] implement the services/expose basic rest;
[]
[] Dockerize every component;
[] docker stack all;
[] implement the gui;
[] deploy on cloud;


specific part:
