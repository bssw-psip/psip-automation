FROM maven:3-openjdk-17-slim as build
RUN curl -sL https://deb.nodesource.com/setup_14.x | bash -
RUN apt-get update -qq && apt-get install -qq --no-install-recommends nodejs

# Stop running as root at this point
#RUN useradd -m ryp
WORKDIR /usr/src/app/
#RUN chown ryp:ryp /usr/src/app/
#USER ryp

# Copy pom.xml and prefetch dependencies so a repeated build can continue from the next step with existing dependencies
#COPY --chown=ryp pom.xml ./
COPY pom.xml ./
RUN mvn dependency:go-offline -Pproduction

# Copy all needed project files to a folder
#COPY --chown=ryp:ryp src src
#COPY --chown=ryp:ryp frontend frontend
#COPY --chown=ryp:ryp package.json ./
#COPY --chown=ryp:ryp package-lock.json ./
COPY src src
COPY frontend frontend
COPY package.json ./
COPY package-lock.json ./

# Build the production package, assuming that we validated the version before so no need for running tests again
RUN mvn clean package -DskipTests -Pproduction

# Running stage: the part that is used for running the application
FROM openjdk:17-jdk-slim
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
#RUN useradd -m ryp
#USER ryp
EXPOSE 80
ENTRYPOINT ["java", "-jar", "/usr/app/app.jar"]

