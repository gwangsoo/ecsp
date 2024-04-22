FROM openjdk:17.0.2-jdk-buster

RUN useradd appuser
USER appuser

WORKDIR /app

EXPOSE 8080

ENV TZ=Asia/Seoul
ENV _JAVA_OPTIONS='-Xms256m -Xmx256m'

ADD ./build/libs/*.jar app.jar

CMD java ${_JAVA_OPTIONS} -Duser.timezone=${TZ} -jar /app/app.jar
