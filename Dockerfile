FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/ecommerce-0.0.1-SNAPSHOT.jar /app/ecommerce-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "ecommerce-api.jar"]
