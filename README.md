# Case Study :: retail-product-rest-api

## Technology Stack

* Java
* Spring Boot, Spring Security, Spring Web, Spring AOP, Spring Data
* Swagger
* lombok
* slf4j, log4j
* Junit, Mockito, AssertJ
* Maven
* Docker
* Embedded Mongo DB 


### Prerequisites

```
Java 1.8.0_112
Maven 3.3.9
Docker 18.03.1
```

### Installing

```
* Clone the Repository or download the zip folder from GITHub. https://github.com/Gengaraj/retail-product-rest-api
```

## Running the tests

```
* Navigate to application folder and execute mvn clean test
```

## Built With Maven and run from Java artifact

```
* mvn clean install
* java -jar target/retail-product-rest-api-0.0.1-SNAPSHOT.jar
```


## Start as Spring Boot Application using Maven

```
* mvn spring-boot:run (To Start as Spring boot application)
```

###Bonus 
## Built With Maven, build docker image and run Container  

```
To run the application and mongo db in docker container, please update the maven pom.xml file in the application folder to change the embedded mongo db dependency scope to test [uncomment the line# 69])

mvn clean install

docker-compose build

docker-compose up
```

## Application Endpoints:

```
Login Endpoint:  http://localhost:8080/auth/login

Product End point to GET and PUT: http://localhost:8080/api/v1/products/{productId}
```

## Test with curl

```
curl -XPOST -d 'username=test&password=test' http://localhost:8080/auth/login

curl -H 'Authorization: Bearer <<<TokenReceived From Login Response>>>' http://localhost:8080/api/v1/products/13860416

curl -X PUT -H 'Authorization: Bearer <<<TokenReceived From Login Response' -H 'Content-Type: application/json' -d '{"id":13860416,"name":"Progressive power yoga:Sedona experie (DVD)","current_price":{"value":1000,"currency_code":"USD"}}' http://localhost:8080/api/v1/products/13860416

Sample Product Ids: 13860416, 13860418, 13860420, 13860421, 13860424, 13860425
```

## Swagger API reference
```
http://localhost:8080/swagger-ui.html
```

## Postman Document reference for sample request and response
```
https://documenter.getpostman.com/view/4447752/RW8AnTnd
```

## Security Implementation

* Token Based Security has been implemented using Spring Security
* Login to application using Login Endpoint and copy the response token
* Use the token as the Authorization Bearer token in the response header to access product Endpoints


## Author
* **Gengaraj Subbiah** 

