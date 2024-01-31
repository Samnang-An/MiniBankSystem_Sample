FROM openjdk:17-jdk-alpine
EXPOSE 8080
RUN apk add --no-cache maven
WORKDIR /eureka
COPY ./ ./
RUN mvn package
CMD ["java", "-jar","/eureka/target/Euraka-0.0.1-SNAPSHOT.jar"]