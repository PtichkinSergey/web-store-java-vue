FROM nexus.nic.etu:8181/nic/maven:3.9.6-eclipse-temurin-8 as build
WORKDIR /backend

COPY . . 
RUN mvn clean install -DskipTests

FROM nexus.nic.etu:8181/nic/maven:3.9.6-eclipse-temurin-8
WORKDIR /backend
COPY --from=build /backend/target/*.jar /backend/app.jar

ENTRYPOINT ["java", "-jar", "/backend/app.jar"]