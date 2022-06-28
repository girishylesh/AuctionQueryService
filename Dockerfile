FROM openjdk:11.0.7-jre-slim
EXPOSE 8080
ADD target/auction-query-service-0.0.1-SNAPSHOT.jar auction-query-service.jar 
ENTRYPOINT ["java","-jar","/auction-query-service.jar"]