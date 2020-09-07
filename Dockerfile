FROM openjdk:11.0.8-jre-slim

COPY ./build/hotels-backend*.jar /usr/src/app/hotels-backend.jar

WORKDIR /usr/src/app

EXPOSE 8080

ENTRYPOINT java ${ADDITIONAL_OPTS} -jar hotels-backend.jar