FROM openjdk:8
MAINTAINER sk
LABEL name="demo" version="1.0" author="sk"
COPY netty-ws-1.0-SNAPSHOT.jar netty-ws-1.0-SNAPSHOT.jar
CMD ["java", "-jar", "netty-ws-1.0-SNAPSHOT.jar"]