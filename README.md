# Target Case Study => myRetail Restful Service 

This is the  Proof of Concept for a Target Red Sky Product Api, which fetches product data from third party API and price information from NO-SQL DB   and return it as JSON.

## Environment Setup

Used Eclipse IDE, 
Import as Maven Project, 
used spring boot, 
Install MongoDb server, open command prompt and type mongod to start mongo server
Open another command prompt and type mongo to run server instance 
 mongodb://127.0.0.1:27017
when the maven build is done, In main class (MyRetailRestfulApiApplication.java) Right click and  run as Spring Boot App.
With Spring Boot default Tomcat App server  is available 


### Prerequisites

```
Language Used Java 1.8
Any IDE => Used Eclipse 
Spring Boot 2.4.2
Git bash for Commit and PULL
Tomcat App Server (In Built)
MongoDb Database
Postman for testing

Third Party RedSky Service: https://redsky.target.com/v3/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics&key=candidate 
```

### Demo

When the application is started for first time, I have inserted sample input data in NOSQL Mongo DB with initial data like id=> 13860428, price => $13.49, currency code=> USD


Sample Request  & Response

GET Request - 
This service returns the product details based on the id. 

```
Url: http://localhost:8080/products/13860428
Request Method: GET
{
    "id": 13860428,
    "name": "The Big Lebowski (Blu-ray)",
    "current_price": {
        "value": 13.49,
        "currency_code": "USD"
    }
}
```

PUT Request - 
The PUT call is used to update Product Price in Mongo database and returns Product details.

```
Url: http://localhost:8080/products/13860428
Content-Type: application/json
Request Method: PUT
Request Body:
 {
    "name": "The Big Lebowski (Blu-ray)",
    "current_price": {
        "value": 33.49,
        "currency_code": "INR"
    }
}

I am sending a 200 Success response

If you pass invalid id
Url: http://localhost:8080/products/1

I am sending 400 Bad request as output.
```


## Developed the application with

* SpringBoot
* Spring Initializer
* Maven Dependency Management
* No-SQl MongoDB
