# E-Leaf 🌱

**E-Leaf** is a web application built using the Spring Framework. It enables product management, a shopping cart feature, and order processing. The project is based on the **MVC (Model-View-Controller)** architecture, ensuring clean code separation between business logic, data handling, and presentation layers.

---

## 📋 Table of Contents

1. [Features](#features)
2. [Technologies](#technologies)
3. [Requirements](#requirements)
4. [Installation and Setup](#installation-and-setup)
5. [Project Structure](#project-structure)

---

## ✨ Features

- **Product Management**:
  - Add, edit, delete,filter,sort and view products.
- **Shopping Cart**:
  - Add products to the cart, remove them, or update quantities.
- **Order Processing**:
  - Create and manage orders.
- **User Authentication**:
  - Register and log in users with different roles (e.g., admin, customer).

---

## 🛠 Technologies

- **Backend**: Java 17, Spring Boot, Spring MVC, Spring Data JPA, Hibernate
- **Frontend**: HTML, CSS, Thymeleaf
- **Database**: MySQL
- **Testing**: JUnit
- **Other Tools**: Maven, Lombok, IntelliJ IDEA

---

## 📂 Requirements

- **Java**: 17 or later
- **Maven**: 3.8.0 or later
- **Web Browser**: Any modern browser (for testing the interface)

---

## 🚀 Installation and Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/Grodelek/Spring-e-commerce.git
   cd Spring-e-commerce
2. Build the project using Maven:
   ```bash
   mvn clean install
3. Run the application:
   ```bash
   mvn spring-boot:run
4. Access the application in your browser:
   ```bash
   http://localhost:8080
🧱 Project Structure

The project follows the MVC architecture:

    Model: Represents application data.
    Location: src/main/java/com/example/ecommerce/model
    Examples: Product, User.

    Controller: Handles HTTP requests and sends data to views.
    Location: src/main/java/com/example/ecommerce/controller
    Examples: ProductController, CartController.

    View: HTML templates generated using Thymeleaf.
    Location: src/main/resources/templates

Additional Directories:

    Repository: Handles database operations using Spring Data JPA.
    Location: src/main/java/com/example/ecommerce/repository

    Service: Contains business logic.
    Location: src/main/java/com/example/ecommerce/service
