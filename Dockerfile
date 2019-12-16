FROM quay.io/quarkus/centos-quarkus-maven:19.0.2 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
COPY checkstyle.xml /usr/src/app
COPY pmd-ruleset.xml /usr/src/app
USER root
RUN chown -R quarkus /usr/src/app
USER quarkus
RUN mvn -f /usr/src/app/pom.xml clean package

FROM fabric8/java-alpine-openjdk8-jre
COPY --from=build /usr/src/app/target/hotels-backend-*.jar /deployments/app.jar
ENTRYPOINT [ "/deployments/run-java.sh" ]