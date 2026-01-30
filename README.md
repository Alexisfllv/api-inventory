# 🧾 API Inventory – Supplier Service

A **reactive REST API** built with **Spring Boot WebFlux** and **R2DBC**, focused on managing suppliers (*Suppliers*).  
This project demonstrates clean architecture, reactive programming, centralized exception handling, and production-ready best practices.

---

## 🚀 Tech Stack

- **Java 21**
- **Spring Boot 3.3x (WebFlux)**
- **R2DBC + PostgreSQL**
- **Project Reactor (Mono / Flux)**
- **MapStruct**
- **Lombok**
- **Jakarta Validation**
- **SpringDoc OpenAPI (Swagger UI)**
- **JUnit 5 + Reactor Test**
- **JaCoCo (minimum 75% coverage)**
- **Maven**

---

## 🧱 Architecture

Layered architecture with **Domain Services** for reusable business logic:

```
controller
service
 ├── impl
 └── domain
repository
entity
dto
exception
mapper
util
```

### Responsibilities

- **Controller**: Reactive HTTP endpoints.
- **Service**: Application use cases.
- **Domain Service**: Core reusable business rules.
- **Repository**: Reactive persistence layer (R2DBC).
- **DTOs**: Clear separation between API contracts and entities.
- **Mapper**: Entity ↔ DTO mapping via MapStruct.
- **Exception Handler**: Centralized reactive error handling.

---

## 📦 Supplier Entity

```java
@Table("suppliers")
public class Supplier extends BaseEntityReactive {
    @Id
    private Long id;
    private String name;
    private String email;
    private String phone;
}
```

Extends `BaseEntityReactive` to support auditing fields.

---

## 📄 Data Transfer Objects (DTOs)

### SupplierDTORequest
Used for **POST / PUT** operations with validations:
- `@NotBlank`
- `@Size`
- `@Email`

### SupplierDTOResponse
Used for API responses.

---

## 🔁 API Endpoints

### Get supplier by ID
```
GET /api/suppliers/{id}
```

### Paginated list
```
GET /api/suppliers/page?page=0&size=3
```

### Search by email
```
GET /api/suppliers/email/{email}
```

### Search by name (case-insensitive, LIKE)
```
GET /api/suppliers/search/name?name=ed
```

### Create supplier
```
POST /api/suppliers
```

### Update supplier
```
PUT /api/suppliers/{id}
```

### Delete supplier
```
DELETE /api/suppliers/{id}
```

---

## ⚠️ Global Exception Handling

Implemented with `@RestControllerAdvice` (reactive):

- **400 – Bad Request**: Validation errors (`WebExchangeBindException`)
- **404 – Not Found**: Resource not found

Standard error response:
```json
{
  "status": "BAD_REQUEST",
  "message": "field: error",
  "path": "/api/suppliers",
  "error": "Method Argument Not Valid"
}
```

---

## 📑 Standard API Response

All endpoints return a consistent wrapper:

```json
{
  "status": "SUCCESS",
  "data": {}
}
```

Implemented via `GenericResponse<T>` and `StatusApi`.

---

## 🧪 Testing & Code Quality

- Unit tests with **JUnit 5** and **Reactor Test**
- **JaCoCo** enforces minimum **75% line coverage**
- Controllers, config, and exception packages excluded from coverage

---

## 📘 API Documentation

Swagger UI available at:

```
/swagger-ui.html
```

Generated using **springdoc-openapi-starter-webflux-ui**.

---

## ▶️ Run the project

```bash
mvn clean spring-boot:run
```

