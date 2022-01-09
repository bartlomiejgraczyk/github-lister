FROM docker.io/library/maven:3.8.2-adoptopenjdk-11 as build
COPY src /home/app/src
COPY pom.xml /home/app
WORKDIR /home/app/
RUN mvn clean
RUN mvn dependency:resolve
RUN mvn package


FROM openjdk:11
ARG JAR_FILE=/home/app/target/github-lister-config-server.jar
COPY --from=build ${JAR_FILE} config-server.jar
ENTRYPOINT ["java","-jar","config-server.jar"]
