FROM openjdk:17-jdk-alpine
MAINTAINER ARTUR KUFA
COPY build/libs/product-discount-0.0.1-SNAPSHOT.jar product-discount-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/product-discount-0.0.1-SNAPSHOT.jar"]