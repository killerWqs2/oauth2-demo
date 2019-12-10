FROM java:8

MAINTAINER killerWqs killerWqs@gmail.com

ADD F:/2019-7/resource-server/target/resource-server-0.0.1-SNAPSHOT.jar /usr/src/resource-server-0.0.1-SNAPSHOT.jar

WORKDIR /usr/src

EXPOSE 8003

ENTRYPOINT ["java", "-jar", "/usr/src/resource-server-0.0.1-SNAPSHOT.jar"]