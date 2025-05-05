# Use a Maven image to build the app
FROM eclipse-temurin:21 AS builder
WORKDIR /app

# Make Maver into a Maven Wrapper
COPY mvnw .
COPY .mvn ./.mvn

COPY pom.xml .
COPY src ./src

# Package the compiled code into a .jar.
RUN ./mvnw clean package -DskipTests

# Use a lighter JDK to run it
FROM eclipse-temurin:21 AS runner
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
