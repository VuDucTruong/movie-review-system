# 🎬 Movie Review System

<div align="center">
  
[![GitHub stars](https://img.shields.io/github/stars/VuDucTruong/movie-review-system?style=for-the-badge)](https://github.com/VuDucTruong/movie-review-system/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/VuDucTruong/movie-review-system?style=for-the-badge)](https://github.com/VuDucTruong/movie-review-system/network)
[![GitHub issues](https://img.shields.io/github/issues/VuDucTruong/movie-review-system?style=for-the-badge)](https://github.com/VuDucTruong/movie-review-system/issues)

**A microservices-based movie review system.**

</div>

## 📖 Overview

This project implements a movie review system using a microservices architecture.  It allows users to review movies, manage their profiles, and receive notifications. The system is designed for scalability and maintainability, leveraging Docker for containerization, a service discovery mechanism (Eureka) and monitoring by Prometheus/Grafana.

## ✨ Features

- **User Authentication & Profiles:** Secure user registration, login, and profile management.
- **Movie Service:** Users can search for movie information and admins can create, update, delete movies
- **Movie Reviews:** Users can rate and review movies.
- **File Upload:**  Users can upload images related to movies or profiles.
- **Notifications:** Users receive notifications regarding new reviews and other activities.
- **Microservices Architecture:** Modular design for better scalability and maintainability.
- **Docker Containerization:** Simplified deployment and management using Docker containers.
- **Service Discovery (Eureka):**  Dynamic service registration and discovery.
- **Monitoring:** Using Prometheus to collect metrics and displaying them via Grafana

## 🛠️ Tech Stack

**Backend:**
- ![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
- ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
- ![Eureka](https://img.shields.io/badge/Eureka-FF6F00?style=for-the-badge) <!-- no official icon -->
- ![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
- ![Grafana](https://img.shields.io/badge/Grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white)
- ![Prometheus](https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white)
- ![Cloudinary](https://img.shields.io/badge/Cloudinary-3448C5?style=for-the-badge&logo=cloudinary&logoColor=white)
- ![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
- ![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
- ![Kafka](https://img.shields.io/badge/Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
- ![Brevo](https://img.shields.io/badge/Brevo-0A1B2B?style=for-the-badge) <!-- no official icon -->
## 🚀 Quick Start

### Prerequisites

- Docker
- Docker Compose
- Java Development Kit (JDK) 23
- Java 21
- Brevo API
- Mongodb URI
- Cloudinary URI

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/VuDucTruong/movie-review-system.git
   cd movie-review-system
   ```

2. **Build and run with Docker Compose:**
   ```bash
   docker-compose up -d
   ```

3. **Access Services:**
- Prometheus UI: http://localhost:9090
- Grafana: http://localhost:3000 ( username: admin / password: admin )
- Eureka Server: http://localhost:8761
- Swagger for all services: http://localhost:8080/swagger-ui/index.html

## 📁 Project Structure

```
movie-review-system/
├── api-gateway/
├── auth-service/
├── docker-compose.yaml
├── eureka-server/
├── file-service/
├── init-multiple-db.sh
├── monitoring/
├── movie-service/
├── notification-service/
├── profile-service/
├── review-service/
└── ...
```

## ⚙️ Configuration

For complication, create an env file with these variables:
```
POSTGRES_URL=jdbc:postgresql://localhost:5432/movie_review
POSTGRES_USER=postgres
POSTGRES_PASSWORD=642003
JWT_SECRET=####
JWT_EXP=86400000 # 1 day
REDIS_HOSTNAME=localhost
KAFKA_BOOTSTRAP_SERVERS=localhost:9094
BREVO_URL=https://api.brevo.com
BREVO_API_KEY=xkeysib-###
BREVO_SENDER_EMAIL=###
```

## 🐳 Docker Configuration

For running with docker compose, every service has its own env file. So this is the folder structure:
```
environments/
├── auth.env
├── db.env
├── file.env
├── movie.env
├── notification.env
├── init-multiple-db.sh
├── path.env
├── profile.env
├── review.env
```

---

```
#auth.env
POSTGRES_URL=jdbc:postgresql://postgres:5432/movie_review_auth
POSTGRES_USER=postgres
POSTGRES_PASSWORD=642003
JWT_SECRET=###
JWT_EXP=86400000
REDIS_HOSTNAME=redis_cache
KAFKA_BOOTSTRAP_SERVERS=kafka:9094
```

```
#db.env
POSTGRES_DB=default-movie-review
POSTGRES_USER=postgres
POSTGRES_PASSWORD=642003
```

```
#file.env
MONGODB_URI=###
CLOUDINARY_URL=####
```

```
#movie.env
POSTGRES_URL=jdbc:postgresql://postgres:5432/movie_review_movie
POSTGRES_USER=postgres
POSTGRES_PASSWORD=642003
```

```
#notification.env
KAFKA_BOOTSTRAP_SERVERS=kafka:9094
BREVO_URL=https://api.brevo.com
BREVO_API_KEY=xkeysib-####
BREVO_SENDER_EMAIL=####
```

```
#path.env
EUREKA_URL=http://eureka-server:8761/eureka
API_GATEWAY_URL=http://api-gateway:8080
EUREKA_HOSTNAME=eureka-server
```

```
#profile.env
POSTGRES_URL=jdbc:postgresql://postgres:5432/movie_review_profile
POSTGRES_USER=postgres
POSTGRES_PASSWORD=642003
```

```
#review.env
POSTGRES_URL=jdbc:postgresql://postgres:5432/movie_review_rating
POSTGRES_USER=postgres
POSTGRES_PASSWORD=642003
KAFKA_BOOTSTRAP_SERVERS=kafka:9094
```

---
<div align="center">

**⭐ Star this repo if you find it helpful!**

</div>
