FROM amazoncorretto:17-alpine-jdk
MAINTAINER dev@nectar.software
WORKDIR /etc/api-gateway
ARG JAR_FILE=build/libs/api-gateway-3.0.3-alpha.jar
COPY ${JAR_FILE} api-gateway.jar
ENTRYPOINT ["java","-jar","api-gateway.jar"]
