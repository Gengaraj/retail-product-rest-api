FROM openjdk:8-jre-alpine
LABEL com.retail.tgt.version="0.0.1-SNAPSHOT"
LABEL maintainer="imgengaraj@gmail.com"
RUN apk update && apk add bash
RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app
COPY ./target/retail-product-rest-api-0.0.1-SNAPSHOT.jar $PROJECT_HOME/retail-product-rest-api.jar

WORKDIR $PROJECT_HOME

EXPOSE 8080
CMD ["java", "-Dspring.profiles.active=production","-Dspring.data.mongodb.uri=mongodb://product-mongo:27017/retail-product-rest-api-db","-Djava.security.egd=file:/dev/./urandom","-jar","./retail-product-rest-api.jar"]