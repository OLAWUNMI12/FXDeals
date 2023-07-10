**#FX DATA WAREHOUSE**


This is a  spring boot project that act as a fx deal service that persist fx deals


**Libraries**

Spring boot
MYSQL
Mapstruct
Apache common lang


**Building the project**

Java JDK 11 
Maven 3.1.1
Git


**Steps to run**
Build the project using mvn clean install
Run using mvn spring-boot:run or docker-compose up (for docker)
The endpoints are accessible via localhost:8080



Endpoints
api/v1/deals/  (POST) 

```JSON
{
"fromCurrency" : "EUR",
"toCurrency" : "USD",
"date" : "2023-07-09T00:30:00",
"amount" : 6000
}
```

api/v1/deals/  (GET)

api/v1/deals/batch   (POST)
```JSON
[
{
"fromCurrency" : "NGN",
"toCurrency" : "USD",
"date" : "2023-07-09T00:30:00",
"amount" : 6000

},
{
"fromCurrency" : "ALL",
"toCurrency" : "USD",
"date" : "2023-07-09T00:30:00",
"amount" : 6000

},
{
"fromCurrency" : "AFL",
"toCurrency" : "USD",
"date" : "2023-07-09T00:30:00",
"amount" : 6000

},
{
"fromCurrency" : "NGN",
"toCurrency" : "BSD",
"date" : "2023-07-09T00:30:00",
"amount" : 6000

},
{
"fromCurrency" : "NGN",
"toCurrency" : "BBD",
"date" : "2023-07-09T00:30:00",
"amount" : 6000

}
]
```







