FROM maven:3.8.6-openjdk-18 as builder
COPY . /usr/src/ryp
WORKDIR /usr/src/ryp
RUN mvn clean install -f /usr/src/ryp 

FROM openjdk:18
COPY --from=builder /usr/src/ryp/target/psip-*.jar ryp.jar
ENTRYPOINT ["java", "-jar", "/ryp.jar"]
EXPOSE 80

