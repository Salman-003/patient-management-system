# 🏥 Patient Management Microservices

A **simplified microservices-based backend** built using **Spring Boot** and **Spring Cloud**, demonstrating all **essential microservice concepts** —  
including **Eureka Service Discovery**, **API Gateway**, **JWT-based Authentication**, **Feign Client communication**, and **per-service H2 databases**.
---

## 🚀 Services Overview

| Service | Port | Description |
|----------|------|-------------|
| **Eureka Server** | 8761 | Service registry |
| **API Gateway** | 8080 | Routes & validates JWT |
| **Auth Service** | 8081 | User register/login, issues JWT |
| **Patient Service** | 8082 | CRUD for patients |
| **Appointment Service** | 8083 | CRUD + Feign to patient-service |

---

## ⚙️ Tech Stack
- Java 17, Spring Boot 3.x  
- Spring Cloud (Eureka, Gateway, OpenFeign)  
- JWT (via JJWT)  
- H2 Database (per service)  
- Maven  

---

## 🔐 Authentication Flow
1. **Register:** `POST /auth/register`
2. **Login:** `POST /auth/login` → returns JWT token  
3. **Use JWT:** Add `Authorization: Bearer <token>` to all secured requests  
4. Gateway validates JWT before routing to services  

---

## 🧠 How It Works

### 🔐 Authentication
- User registers via `/auth/register`.
- Logs in via `/auth/login` to get a **JWT token**.
- Token is passed in the `Authorization: Bearer <token>` header for all secured endpoints.

### 🛡️ API Gateway
- Every request passes through **API Gateway**.
- Gateway validates JWT (via `JwtAuthFilter`).
- If valid → forwards request to the respective service.
- If invalid/expired → responds `401 Unauthorized`.

### 🧩 Microservices
- **Patient Service:** CRUD endpoints for patient data.
- **Appointment Service:** CRUD + calls `patient-service` via Feign Client.
- Both services register with **Eureka** for discovery.

---


