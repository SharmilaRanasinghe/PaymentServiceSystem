# Dockerfile for JDK 23
# Multi-stage build for optimal image size

# ============================================
# STAGE 1: Build with JDK 23
# ============================================
FROM eclipse-temurin:23-jdk-alpine AS builder

WORKDIR /app

# Install Maven (since we need it for building)
RUN apk add --no-cache maven

# Copy Maven files
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Make mvnw executable
RUN chmod +x mvnw

# Download dependencies (cached layer)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# ============================================
# STAGE 2: Runtime with JRE 23 (smaller image)
# ============================================
FROM eclipse-temurin:23-jre-alpine

# Add labels
LABEL maintainer="payment-service-team"
LABEL version="1.0"
LABEL description="Payment Service with Merchant Onboarding - JDK 23"

# Create non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Set working directory
WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Create logs directory
RUN mkdir -p /app/logs

# Expose port
EXPOSE 8080

# JVM options optimized for JDK 23
ENV JAVA_OPTS="-Xmx512m -Xms256m \
    -XX:+UseZGC \
    -XX:+ZGenerational \
    -XX:MaxGCPauseMillis=200 \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/app/logs/heapdump.hprof \
    -Xlog:gc*:file=/app/logs/gc.log:time,uptime,level,tags:filecount=5,filesize=10m"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]