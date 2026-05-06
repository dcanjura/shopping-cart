# Shopping Cart Microservices System

## Overview

This project is a microservices-based shopping cart system developed with Spring Boot. The application simulates a complete purchase flow, including product management, cart operations, order creation, payment processing, stock reduction, exception handling, API documentation, and role-based security.

The system was designed following clean architecture principles and separation of responsibilities between services.

---

# Architecture

The application is composed of four independent microservices:

| Service         | Responsibility                            |
| --------------- | ----------------------------------------- |
| Product Service | Product management and stock handling     |
| Cart Service    | Shopping cart operations                  |
| Order Service   | Order creation and order management       |
| Payment Service | Payment processing using Strategy Pattern |

---

# Technologies Used

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* H2 Database
* Lombok
* Swagger / OpenAPI
* Maven
* REST APIs

---

# Implemented Features

## Product Service

* Create products
* Update products
* Delete products
* Get all products
* Get product by id
* Reduce stock after successful payment
* Admin role protection for write operations

---

## Cart Service

* Create cart
* Get cart by user
* Add item to cart
* Remove item from cart
* Update item quantity
* Calculate total automatically
* Checkout process
* User authentication required

---

## Order Service

* Create orders from cart data
* Order status handling
* Order item mapping
* Payment integration
* Automatic stock reduction
* Exception handling

---

## Payment Service

* Payment processing
* Strategy Pattern implementation
* Multiple payment strategies support
* Payment status simulation

---

# Design Patterns Used

## Builder Pattern

Used to simplify entity and object creation through Lombok's `@Builder` annotation.

### Example:

```java
Product product = Product.builder()
        .name(request.name())
        .price(request.price())
        .stock(request.stock())
        .build();
```

---

## Strategy Pattern

Implemented in the payment service to support multiple payment methods.

### Structure:

* PaymentStrategy interface
* CardPaymentStrategy
* PaypalPaymentStrategy
* Strategy selection in PaymentService

---

# Security

Spring Security was implemented using role-based authentication.

## Roles

| Role  | Permissions                        |
| ----- | ---------------------------------- |
| ADMIN | Create, update and delete products |
| USER  | Cart operations                    |

## Authentication Type

* HTTP Basic Authentication
* In-memory users

### Default Users

| Username | Password | Role  |
| -------- | -------- | ----- |
| admin    | admin123 | ADMIN |
| user     | user123  | USER  |

---

# Exception Handling

Global exception handling was implemented using `@RestControllerAdvice`.

## Implemented Exceptions

* NotFoundException
* BadRequestException
* Generic Exception Handler

### Example Response

```json
{
  "error": "Product not found",
  "status": 404
}
```

---

# API Documentation

Swagger/OpenAPI was integrated into every microservice.

## Swagger URLs

| Service         | URL                                                                                        |
| --------------- | ------------------------------------------------------------------------------------------ |
| Product Service | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) |
| Cart Service    | [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) |
| Order Service   | [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html) |
| Payment Service | [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html) |

---

# Service Ports

| Service         | Port |
| --------------- | ---- |
| Product Service | 8080 |
| Cart Service    | 8081 |
| Order Service   | 8082 |
| Payment Service | 8083 |

---

# Inter-Service Communication

The microservices communicate using `RestTemplate`.

## Communication Flow

```text
Cart Service
   ↓
Order Service
   ↓
Payment Service
   ↓
Product Service
```

---

# Purchase Flow

1. Admin creates products
2. User creates a cart
3. User adds products to the cart
4. User performs checkout
5. Order Service creates the order
6. Payment Service processes the payment
7. Product Service reduces stock
8. Order status is updated

---

# Running the Project

## Requirements

* Java 17
* Maven
* IDE (IntelliJ IDEA recommended)

---

## Steps

### 1. Clone repository

```bash
git clone <repository-url>
```

---

### 2. Run each microservice

Run the following applications individually:

* ProductServiceApplication
* CartServiceApplication
* OrderServiceApplication
* PaymentServiceApplication

---

### 3. Access Swagger

Open the Swagger UI URLs listed above.

---

# Example Workflow

## 1. Create Product (ADMIN)

```http
POST /products
```

```json
{
  "name": "Laptop",
  "price": 800,
  "stock": 10
}
```

---

## 2. Create Cart

```http
POST /cart
```

```json
{
  "userId": "user123"
}
```

---

## 3. Add Item

```http
POST /cart/{cartId}/items
```

```json
{
  "productId": "1",
  "quantity": 2
}
```

---

## 4. Checkout

```http
POST /cart/checkout/{cartId}
```

```json
{
  "customerId": "user123",
  "paymentType": "CARD"
}
```

---

# Future Improvements

* JWT Authentication
* API Gateway
* Service Discovery
* Docker support
* PostgreSQL integration
* Unit and integration tests
* Kafka/Event-driven communication

---

# Author

Diego Enrique Arguera Canjura

* Email: [diegoac053@gmail.com](mailto:diegoac053@gmail.com)
* LinkedIn: [https://www.linkedin.com/in/diego-canjura/](https://www.linkedin.com/in/diego-canjura/)
