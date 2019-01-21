#FROM openjdk:8-jdk-alpine
FROM jeanblanchard/java:jdk-8
RUN mkdir -p /usr/local/autocomplete-data
VOLUME /usr/local/autocomplete-data
ADD target/autocomplete-data-1.0.0-SNAPSHOT.jar /usr/local/autocomplete-data/autocomplete-data.jar
ADD key/autocomplete-demo.json /usr/local/autocomplete-data/autocomplete-demo.json
ENV JAVA_OPTS="-Dspring.profiles.active=prod"
ENV GOOGLE_APPLICATION_CREDENTIALS="/usr/local/autocomplete-data/autocomplete-demo.json"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/usr/local/autocomplete-data/autocomplete-data.jar"]

