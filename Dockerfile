FROM eclipse-temurin:21-jre

# Set working directory
WORKDIR /app

# Add a non-root user to run the application
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Copy the jar file
COPY epl-web/target/*.jar app.jar

# Set ownership to the non-root user
RUN chown -R appuser:appuser /app

# Switch to non-root user
USER appuser

# Expose the application port
EXPOSE 8080

# Set JVM options (adjust as needed)
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Run the application - simplified without upload file base URI
ENTRYPOINT java $JAVA_OPTS -jar app.jar