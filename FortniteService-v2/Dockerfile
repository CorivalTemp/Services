FROM eclipse-temurin:23-jdk
EXPOSE 7883
ADD target/realityfn-fortnite-service.jar realityfn-fortnite-service.jar

COPY src/main/resources /app/resources
ENTRYPOINT ["java","-jar","/realityfn-fortnite-service.jar"]