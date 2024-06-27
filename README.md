# KyryloCodeFirstApplication

## Description

KyryloCodeFirstApplication is a Spring Boot application that includes the functionality of a web service. The application provides a REST API for working with `Price` entities, exception handling using `GlobalExceptionHandler`, and the ability to perform testing using JUnit and Mockito.

## Prerequisites

To work with the application, you need:
- Java 11 or higher
- Apache Maven 3.6.3 or higher

## Installation and Running

### Clone the Repository

```bash
git clone https://github.com/KyryloTestWork/kyrylo-code-first.git
cd KyryloCodeFirstApplication
```
## To build and run the application, use the following Maven commands:
### Build the Application
```bash
mvn clean install
```
### Run the Application
````bash
mvn spring-boot:run
````
The application will start and will be available at http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/.

## Running Tests
The application includes unit and integration tests that can be run using Maven.
### Unit Tests and Integration Tests
To run the unit tests, use the following command:
```bash
mvn test
```

## Exception Handling
The application uses GlobalExceptionHandler to handle exceptions globally. Here are the main exception handlers included:
- `handleGlobalException` for generic exceptions
- `handleEntityNotFoundException` for EntityNotFoundException
- `handleEntityConstraintViolationException` for ConstraintViolationException
- `handleEntityValidationException` for ValidationException

## Additional Information
- The application uses `Spring Boot 2.5.4`.
- Integration tests use `@SpringBootTest`, `MockMvc` and `Cucumber`.
- Unit tests are written using `JUnit 5` and `Mockito`.
