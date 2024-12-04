FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/listadousuario-0.0.1.jar
COPY ${JAR_FILE} app_listadousuario.jar
EXPOSE  8085
ENTRYPOINT ["java", "-jar"]