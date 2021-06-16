FROM maven:3.3-jdk-8 as builder
COPY . /usr/src/ryp
WORKDIR /usr/src/ryp
RUN mvn clean install -f /usr/src/ryp && mkdir /usr/src/wars/
RUN find /usr/src/ryp/ -iname '*.war' -exec cp {} /usr/src/wars/ \;

FROM tomcat:8.5.43
COPY --from=builder /usr/src/wars/ryp-api-0.1.war /usr/local/tomcat/webapps/ryp-api.war
#COPY data /opt/data/
ENV NEO4J_URL http://neo4j:coney@ryp-neo4j:7474
ENV RETE_PATH /opt/data/
ENV SWAGGER_ENABLE true
EXPOSE 8080
