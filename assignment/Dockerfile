FROM openjdk:8-jdk-alpine
RUN mkdir -p /opt/addressbook
RUN addgroup -S pwc && adduser -S pwc -G pwc
USER pwc:pwc
COPY target/addressbook.csv /opt/addressbook
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.pwc.assignment.Application"]
