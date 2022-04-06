FROM openjdk:17.0
LABEL maintainer="alex.galvis.sistemas@gmail.com"
ADD target/earthquake-svc-2.0.1.jar earthquake-svc.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar","/earthquake-svc.jar"]