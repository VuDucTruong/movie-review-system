# ---------- STAGE 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy file pom.xml trước để cache dependencies
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download dependencies (tận dụng cache)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy source code vào container
COPY src src

# Build project (skip test để nhanh hơn, có thể bỏ nếu cần test)
RUN ./mvnw clean package -DskipTests

# ---------- STAGE 2: Runtime ----------
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

# Thêm user không phải root để tăng bảo mật
RUN addgroup -S spring && adduser -S spring -G spring

# Copy jar từ builder stage
COPY --from=builder /app/target/*.jar app.jar

# Chạy app bằng user non-root
USER spring

# Thiết lập biến môi trường mặc định
ENV JAVA_OPTS="-Xms256m -Xmx512m" \
    TZ=Asia/Ho_Chi_Minh


# Entrypoint chạy ứng dụng
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]