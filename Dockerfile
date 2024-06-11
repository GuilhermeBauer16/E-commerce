FROM openjdk:21-jdk-slim

#ARG DB_NAME
#ARG DB_USERNAME
#ARG DB_PASSWORD
#
#ENV DB_NAME=${DB_NAME} \
#    DB_USERNAME=${DB_USERNAME} \
#    DB_PASSWORD=${DB_PASSWORD}

WORKDIR /app
COPY target/*.jar /app/E-commerce.jar

ENTRYPOINT ["java", "-jar", "E-commerce.jar"]
