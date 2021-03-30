#Multistage Build
## Build Stage
#FROM maven:3.6-openjdk-8-slim AS build
#COPY pom.xml /app/
#COPY src /app/src
#RUN mvn -f /app/pom.xml clean package
#
## Run Stage
#FROM openjdk:8-jre-alpine
#RUN mkdir -p config
#COPY ./config config
#COPY --from=build /app/target/api*.jar app.jar
#ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]

#Normal Build
FROM openjdk:8-jre-alpine
RUN mkdir -p config
COPY ./config config
COPY target/api*.jar app.jar
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]

