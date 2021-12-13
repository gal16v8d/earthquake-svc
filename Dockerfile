FROM openjdk:8-jdk-alpine
LABEL maintainer="alex.galvis.sistemas@gmail.com"
ADD target/earthquake-svc-1.0.001.jar earthquake-svc.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar","/earthquake-svc.jar"]