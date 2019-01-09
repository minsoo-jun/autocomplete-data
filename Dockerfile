FROM openjdk:8-jdk-alpine
RUN mkdir -p /usr/local/autocomplete-data
VOLUME /usr/local/autocomplete-data
ADD target/autocomplete-data-1.0.0-SNAPSHOT.jar /usr/local/autocomplete-data/autocomplete-data.jar
ADD key/minsoojun-222707-7a0dfe1ba9a0.json /usr/local/autocomplete-data/minsoojun-222707-7a0dfe1ba9a0.json
ENV JAVA_OPTS="-Dspring.profiles.active=prod"
RUN export GOOGLE_APPLICATION_CREDENTIALS="/usr/local/autocomplete-data/minsoojun-222707-7a0dfe1ba9a0.json"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/usr/local/autocomplete-data/autocomplete-data.jar"]

