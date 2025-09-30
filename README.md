# Identity Service 🔐

**Identity Service** is a backend service built with **Java Spring Boot**, providing user authentication and authorization features.  
It uses **JWT (JSON Web Token)** for session management and **Role-Based Access Control (RBAC)** for permission handling.

---

## ✨ Features
- User registration (sign up)
- User login with JWT
- Issue **Access Token** and **Refresh Token**
- Role-based authorization (e.g., USER, ADMIN)
- Middleware to validate JWT for every protected request
- Secured APIs accessible only with valid roles

---

## 🛠️ Tech Stack
- **Java 21**
- **Spring Boot 3+**
- **Spring Security 6**
- **Spring Data JPA (Hibernate)**
- **MySQL** (can be swapped with PostgreSQL)
- **JWT (jjwt / Nimbus JOSE + JWT)**

