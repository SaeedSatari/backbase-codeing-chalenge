FROM openjdk:11
ADD target/backbase.jar backbase.jar
ENTRYPOINT ["java", "-jar","backbase.jar"]
EXPOSE 8080