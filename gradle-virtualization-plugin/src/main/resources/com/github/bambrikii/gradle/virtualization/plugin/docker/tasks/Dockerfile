FROM openjdk:latest

WORKDIR /app
COPY build/libs/${app-name}-${app-version}.jar /app/

CMD java -jar ./${app-name}-${app-version}.jar
