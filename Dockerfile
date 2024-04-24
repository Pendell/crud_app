FROM eclipse-temurin:22-jdk-alpine
WORKDIR /app
RUN apk add --no-cache chromium chromium-chromedriver
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]