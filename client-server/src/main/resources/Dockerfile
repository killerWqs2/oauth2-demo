FROM java:8

MAINTAINER killerWqs killerWqs@gmail.com

ADD F:/2019-7/client-server/target/client-server-0.0.1-SNAPSHOT.jar /usr/src/client-server-0.0.1-SNAPSHOT.jar

WORKDIR /usr/src

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "/usr/src/client-server-0.0.1-SNAPSHOT.jar"]