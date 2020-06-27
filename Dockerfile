FROM openjdk:8u171-jdk-alpine3.8

LABEL by="João Arthur - joaoajm@gmail.com"

ENV LANG C.UTF-8

RUN apk add --update bash

ADD target/rh-api-1.0.0.0.jar /app/rh-api.jar

CMD java -jar /app/rh-api.jar