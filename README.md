This is education API developed using Java, SpringBoot, Docker, Prometheus and Grafana

Purpose :
This application allow student to participate in courses taught by teachers. Once teacher granted student
to participate then student can join the course.

-- Java 17 or higher version and Docker must be installed in system   
-- start cmd 
-- provide project path 
-- docker-compose up  
-- docker-compose down (command to be executed to stop docker) --rmi all


To start application in local environment without docker
use the following command
cd [PATH TO PROJECT]
mvn clean package  && mvn spring-boot:run -Dspring.profiles.active=local


