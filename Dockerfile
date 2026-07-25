# ---------- Stage 1: compile the servlets/classes ----------
# This is a plain Eclipse Dynamic Web Project (no Maven/Gradle), so we compile
# directly with javac against the jars already vendored in WebContent/WEB-INF/lib.
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copy the whole project (src + WebContent) into the build stage
COPY . .

# Compile every .java file under src/, using the jars in WEB-INF/lib as the classpath.
# Output goes straight into WebContent/WEB-INF/classes, which is where the JVM
# expects compiled classes + db.properties to live on the runtime classpath.
RUN mkdir -p WebContent/WEB-INF/classes && \
    find src -name "*.java" > /tmp/sources.txt && \
    javac -encoding UTF-8 -cp "WebContent/WEB-INF/lib/*" -d WebContent/WEB-INF/classes @/tmp/sources.txt && \
    (cp src/db.properties WebContent/WEB-INF/classes/ 2>/dev/null || true)

# ---------- Stage 2: runtime ----------
# Tomcat 10.1 is required (not 9.x) because this project uses the jakarta.servlet
# namespace (Jakarta EE 9+), while Tomcat 9 still uses javax.servlet.
FROM tomcat:10.1-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*

# Deploy as the ROOT app so it's served at "/" instead of "/Medical"
COPY --from=build /app/WebContent /usr/local/tomcat/webapps/ROOT

EXPOSE 8080
CMD ["catalina.sh", "run"]