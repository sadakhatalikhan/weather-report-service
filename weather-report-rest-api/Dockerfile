FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Weather-Report-API.jar
ENTRYPOINT ["java","-jar","Weather-Report-API.jar"]