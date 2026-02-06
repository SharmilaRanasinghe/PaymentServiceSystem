# 🏦 Payment Service System

A comprehensive Spring Boot backend service for merchant onboarding and payment transaction processing with clean architecture, comprehensive error handling, and Docker support.

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- Docker & Docker Compose (optional)
- PostgreSQL 16+ (if running without Docker)

### Running with Docker Compose (Recommended)
```bash
# Clone and navigate to project
cd "Payment Service System"

# Start all services
docker-compose up --build

# Or run in background
docker-compose up -d --build

# Check status
docker-compose ps

# View logs
docker-compose logs -f app