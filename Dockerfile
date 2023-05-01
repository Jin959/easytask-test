FROM openjdk:11
LABEL maintainer="J Lee from E-Team"
EXPOSE 8080
ARG JAR_FILE=/build/libs/easytask-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /easytask.jar
ENTRYPOINT ["java","-jar","/easytask.jar"]