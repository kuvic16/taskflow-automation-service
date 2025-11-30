# taskflow-automation-service
askFlow Automation Service — A modular task scheduling and reminder backend built using Java, Spring Boot, and C4 architecture modeling.

# TaskFlow Automation Service  
A modular task scheduling and reminder backend built using **Java**, **Spring Boot**, and **C4 architecture modeling**.

TaskFlow is a lightweight backend service that allows users to manage tasks, notes, tags, and automatic reminders. It includes a scheduler module, JWT-based authentication, and clean architecture principles following the C4 Model (Context, Container, Component views).

---

## 🚀 Features

### ✅ Core Features
- User authentication (JWT)
- Create, update, delete tasks
- Optional reminder time for each task
- Background scheduler to trigger reminders
- Notes module (CRUD)
- Tagging support for both tasks & notes
- Search tasks & notes
- Clean and extensible architecture
- C4-based system documentation

### 🔧 Technology Stack
- Java 17+
- Spring Boot (Web, JPA, Security, Scheduling)
- PostgreSQL (or MySQL)
- Hibernate / JPA
- Lombok
- Spring Scheduler (`@Scheduled`)
- Maven
- Docker (optional)

---

## 🧩 Architecture (C4 Model)

This project follows **C4 Architecture Modeling**:

### Level 1 — Context Diagram
Shows the high-level interactions between:
- User  
- TaskFlow API  
- Database  
- Notification Service  

### Level 2 — Container Diagram
Key components:
- Spring Boot Application  
- Scheduler Module  
- Email/Notification Adapter  
- Database  

### Level 3 — Component Diagram
Internal components:
- AuthService  
- TaskService  
- TagService  
- NoteService  
- ReminderScheduler  
- Repositories (JPA)  

📁 All diagrams are available in the `/diagrams` folder.

---

## 📦 Project Structure

project-root/
├── src/main/java/com/thousand31/taskflow
│ ├── controller
│ ├── service
│ ├── repository
│ ├── model
│ ├── config
│ ├── scheduler
│ ├── exception
├── src/test/java
├── diagrams/
│ ├── context.png
│ ├── container.png
│ ├── component.png
├── README.md
