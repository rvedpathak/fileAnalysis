FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/task-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /opt/demo/task/bin/task.jar
COPY src/main/resources /opt/demo/task/config
ENTRYPOINT ["java","-jar","/opt/demo/task/bin/task.jar","--spring.config.location=/opt/demo/task/config/application.properties, /opt/demo/task/config/jdbc.properties"]