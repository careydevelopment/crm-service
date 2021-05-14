FROM adoptopenjdk/openjdk11:jre-11.0.10_9-alpine
COPY ./target/crm-service.jar /
EXPOSE 32040
ENTRYPOINT ["java", "-jar", "./crm-service.jar"]
