FROM openjdk:21-jdk-slim

ARG DB_NAME
ARG DB_USERNAME
ARG DB_PASSWORD
ARG DB_PORT

ENV DB_NAME=${DB_NAME} \
    DB_USERNAME=${DB_USERNAME} \
    DB_PASSWORD=${DB_PASSWORD} \
    DB_PORT=${DB_PORT}

WORKDIR /app
COPY target/*.jar /app/E-commerce.jar

ENTRYPOINT ["java", "-jar", "E-commerce.jar"]


#
#  DB_USERNAME=root
#  DB_PASSWORD=testDocker
#  DB_NAME=E_commerce
#  DB_PORT=3308
#  DB_HOST=localhost