FROM java:8

MAINTAINER killerWqs killerWqs@gmail.com

ADD F:/2019-7/authorization-server/target/authorization-server-0.0.1-SNAPSHOT.jar /usr/src/authorization-server-0.0.1-SNAPSHOT.jar

WORKDIR /usr/src

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "/usr/src/authorization-server-0.0.1-SNAPSHOT.jar"]