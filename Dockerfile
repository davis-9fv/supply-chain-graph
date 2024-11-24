# Build stage
FROM eclipse-temurin:21.0.1_12-jdk-jammy as build
WORKDIR /builder

COPY build/libs/supply-chain-graph-v1.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract --destination .

# API
FROM eclipse-temurin:21.0.1_12-jre-jammy as app
WORKDIR /app

ARG SOURCE=/builder
COPY --from=build ${SOURCE}/dependencies/ ./
COPY --from=build ${SOURCE}/spring-boot-loader/ ./
COPY --from=build ${SOURCE}/snapshot-dependencies/ ./
COPY --from=build ${SOURCE}/application/ ./

EXPOSE 80

CMD ["org.springframework.boot.loader.launch.JarLauncher"]
ENTRYPOINT ["java", "-XshowSettings:vm", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-XX:+CrashOnOutOfMemoryError"]

