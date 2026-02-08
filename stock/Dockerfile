FROM openjdk:26-ea-21-slim
 
COPY target/stock-0.0.1-SNAPSHOT.jar stock.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/stock.jar"]