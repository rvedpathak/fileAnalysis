This is a Distributed File Analysis System based on - 
1. Java 8
2. Redis
3. Spring MVC REST
4. Maven

By default this application points to redis server on localhost at port 6379.
In case any other server needs to be used, update "jdbc.properties" file with correct details accordingly.

All Supported APIs and input/output details can be found at -
http://localhost:8080/demo/swagger-ui.html

To build the appication run - 

mvn clean install

To run the application, run below command -

mvn spring-boot:run
