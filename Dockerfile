# ---------- Stage 1: compile the servlets/classes ----------
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

RUN mkdir -p src/main/webapp/WEB-INF/classes && \
    find src/main/java -name "*.java" > /tmp/sources.txt && \
    javac -encoding UTF-8 -cp "src/main/webapp/WEB-INF/lib/*" -d src/main/webapp/WEB-INF/classes @/tmp/sources.txt && \
    (cp src/db.properties src/main/webapp/WEB-INF/classes/ 2>/dev/null || true)

# ---------- Stage 2: runtime ----------
FROM tomcat:10.1-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/src/main/webapp /usr/local/tomcat/webapps/ROOT

EXPOSE 8080
CMD ["catalina.sh", "run"]