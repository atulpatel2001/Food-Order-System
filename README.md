# Food-Order-System

The Food-Order-System is a microservices-based application built using Spring Boot that provides functionality similar to Swiggy. It allows users to order food from restaurants, manage addresses, and handle authentication with Keycloak.

## Microservices Architecture

### 1. **User Service** (Port: 8080)
- Manages user registration and authentication.
- Requires PostgreSQL and Keycloak for authentication.

### 2. **Config Server** (Port: 8071)
- Centralized configuration management for all microservices.
- Uses Spring Cloud Config.

### 3. **Eureka Server** (Port: 8073)
- Service registry for microservices.
- Uses Spring Cloud Netflix Eureka.

### 4. **RabbitMQ** (Port: 15672)
- Message broker for event-driven communication.
- Used for Spring Cloud Bus to refresh configurations dynamically.

### 5. **Address Management Service**
- Manages user addresses.
- Uses PostgreSQL as the database.

## Technologies Used
- **Spring Boot** - Backend framework
- **Spring Cloud Config** - Configuration management
- **Spring Security & Keycloak** - Authentication and authorization
- **Eureka Server** - Service registry
- **RabbitMQ** - Event-driven architecture
- **PostgreSQL** - Database for all services
- **Docker & Kubernetes** - Containerization and orchestration
- **Google Jib** - Container image building
- **Helm** - Kubernetes package manager

## Setup & Installation

### **1. Keycloak Setup**
Run the following command to start Keycloak:
```bash
docker run -d -p 8072:8080 \
-e KEYCLOAK_ADMIN=admin \
-e KEYCLOAK_ADMIN_PASSWORD=admin \
quay.io/keycloak/keycloak:24.0.3 start-dev
```

### **2. Keycloak Database Setup**
```bash
docker run -d --name keycloakdb \
-e POSTGRES_PASSWORD=root123 \
-p 5432:5432 \
-e POSTGRES_DB=keyclaokdb \
-d postgres
```

### **3. User Service Database Setup**
```bash
docker run -d --name userservicedb \
-e POSTGRES_PASSWORD=root123 \
-p 5433:5432 \
-e POSTGRES_DB=userservicedb \
-d postgres
```

### **4. Config Server Setup** (Port: 8071)
- Add the required dependencies for Spring Cloud Config.
- Store configurations in a Git repository.

### **5. Eureka Server Setup** (Port: 8073)
Run the Eureka server to register all microservices.

### **6. RabbitMQ Setup**
```bash
docker run -d -it --rm --name rabbitmq \
-p 5672:5672 -p 15672:15672 \
rabbitmq:3.13-management
```

### **7. Address Management Service Database Setup**
```bash
docker run -d --name addressservicedb \
-e POSTGRES_PASSWORD=root123 \
-p 5434:5432 \
-e POSTGRES_DB=addressservicedb \
-d postgres
```

## Build and Run Services
Run the following commands to build and run the services:

### **Build Services**
```bash
mvn clean install
```

### **Run Services**
```bash
mvn spring-boot:run
```

### **Generate Docker Image**
```bash
mvn package jib:build
```

### **Run Docker Container**
```bash
docker run -p 8080:8080 my-demo-app:latest
```

## Kubernetes Deployment

### **1. Install Kubernetes Dashboard**
```bash
helm upgrade --install kubernetes-dashboard kubernetes-dashboard/kubernetes-dashboard --create-namespace --namespace kubernetes-dashboard
```

### **2. Port Forwarding for Dashboard**
```bash
kubectl -n kubernetes-dashboard port-forward svc/kubernetes-dashboard-kong-proxy 8443:443
```

### **3. Generate Admin Token**
```bash
kubectl -n kubernetes-dashboard create token admin-user
```

### **4. Get Token**
```bash
kubectl get secret admin-user -n kubernetes-dashboard -o jsonpath={".data.token"} | base64 -d
```

### **5. Check Deployments & Services**
```bash
kubectl get deployments
kubectl get services
kubectl get replicaset
```

### **6. Helm Deployment Commands**
```bash
helm install food-order-system ./helm-chart
helm upgrade food-order-system ./helm-chart
helm list
helm rollback food-order-system 1
helm uninstall food-order-system
```

---
This project follows a **microservices architecture** with **containerized deployment** using **Docker and Kubernetes**. 🚀

