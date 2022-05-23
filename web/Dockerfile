FROM maven:3.8-openjdk-16 as builder
COPY . /usr/src/ryp
WORKDIR /usr/src/ryp
RUN mvn clean install -f /usr/src/ryp

FROM openjdk:16
ARG JAR_FILE=/usr/src/ryp/target/psip-*.jar
COPY --from=builder ${JAR_FILE} /app.jar
ENV SWAGGER_ENABLE true
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/app.jar"]
