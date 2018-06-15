FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/mpesa-sender.jar /mpesa-sender/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/mpesa-sender/app.jar"]
