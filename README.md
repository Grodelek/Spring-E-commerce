Spring E-Commerce

Spring E-Commerce is a web application built using the Spring Framework. It enables product management, a shopping cart feature, and order processing. The project is based on the MVC (Model-View-Controller) architecture, ensuring clean code separation between business logic, data handling, and presentation layers.
Table of Contents

    Features
    Technologies
    Requirements
    Installation and Setup
    Project Structure

Features

    Product Management:
        Add, edit, delete, and view products.
    Shopping Cart:
        Add products to the cart, remove them, or update quantities.
    Order Processing:
        Create and manage orders.
    User Authentication:
        Register and log in users with different roles (e.g., admin, customer).

Technologies

    Backend: Java 17, Spring Boot, Spring MVC, Spring Data JPA, Hibernate
    Frontend: HTML, CSS, Thymeleaf
    Database: H2 (embedded database) with support for other databases like MySQL and PostgreSQL
    Testing: JUnit, Mockito
    Other Tools: Maven, Lombok, IntelliJ IDEA

Requirements

    Java 17 or later
    Maven 3.8.0 or later
    Web browser (for testing the interface)

Installation and Setup

    Clone the repository:

git clone https://github.com/Grodelek/Spring-e-commerce.git
cd Spring-e-commerce

Build the project using Maven:

mvn clean install

Run the application:

mvn spring-boot:run

The application will be available at:

    http://localhost:8080

Project Structure

The project follows the MVC architecture:

    Model (src/main/java/com/example/ecommerce/model): Represents application data (e.g., Product, Order, User).
    Controller (src/main/java/com/example/ecommerce/controller): Handles HTTP requests and sends data to the views (e.g., ProductController, CartController).
    View (src/main/resources/templates): HTML templates generated using Thymeleaf.

Additional directories:

    Repository: Handles database operations using Spring Data JPA.
    Service: Contains business logic.
