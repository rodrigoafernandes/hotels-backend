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
ENV AB_ENABLED=jmx_exporter
COPY --from=build /usr/src/app/target/lib/* /deployments/lib/
COPY --from=build /usr/src/app/target/*-runner.jar /deployments/app.jar
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENTRYPOINT [ "/deployments/run-java.sh" ]