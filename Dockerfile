FROM openjdk:17
EXPOSE 8080
RUN mkdir -p /app
WORKDIR /app
COPY build/libs/paw-paradise-back-0.0.1-SNAPSHOT.jar /app/paw-paradise-back-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/paw-paradise-back-0.0.1-SNAPSHOT.jar"]
