package com.kyrylocodefirst.configuration.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.kyrylocodefirst.configuration.exception.dto.ErrorDetails;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private static final String URL = "http://localhost:8080/some-url";

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(8080);
        request.setRequestURI("/some-url");

        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);
    }

    @Test
    void handleGlobalException_shouldReturn_InternalServerError() {
        request.setQueryString("some-query");

        Exception exception = new Exception("Test exception");

        ResponseEntity<?> responseEntity = globalExceptionHandler.handleGlobalException(exception, mock(WebRequest.class));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        ErrorDetails errorDetails = (ErrorDetails) responseEntity.getBody();
        assert errorDetails != null;
        assertEquals(500, errorDetails.getStatusCode());
        assertEquals("Test exception", errorDetails.getMessage());
        assertEquals("http://localhost:8080/some-url?some-query", errorDetails.getDetails());
    }

    @Test
    void handleEntityNotFoundException_shouldReturn_NotFound() {
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found");

        ResponseEntity<?> responseEntity = globalExceptionHandler.handleEntityNotFoundException(exception, mock(WebRequest.class));

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ErrorDetails errorDetails = (ErrorDetails) responseEntity.getBody();
        assert errorDetails != null;
        assertEquals(404, errorDetails.getStatusCode());
        assertEquals("Entity not found", errorDetails.getMessage());
        assertEquals(URL, errorDetails.getDetails());
    }

    @Test
    void handleConstraintViolationException_shouldReturn_BadRequest() {
        ConstraintViolationException exception = new ConstraintViolationException("Constraint violation", null);

        ResponseEntity<?> responseEntity = globalExceptionHandler.handleEntityConstraintViolationException(exception, mock(WebRequest.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorDetails errorDetails = (ErrorDetails) responseEntity.getBody();
        assert errorDetails != null;
        assertEquals(400, errorDetails.getStatusCode());
        assertEquals("Constraint violation", errorDetails.getMessage());
        assertEquals("http://localhost:8080/some-url", errorDetails.getDetails());
    }

    @Test
    void handleValidationException_shouldReturn_BadRequest() {
        ValidationException exception = new ValidationException("Validation error");

        ResponseEntity<?> responseEntity = globalExceptionHandler.handleEntityValidationException(exception, mock(WebRequest.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorDetails errorDetails = (ErrorDetails) responseEntity.getBody();
        assert errorDetails != null;
        assertEquals(400, errorDetails.getStatusCode());
        assertEquals("Validation error", errorDetails.getMessage());
        assertEquals("http://localhost:8080/some-url", errorDetails.getDetails());
    }
}
