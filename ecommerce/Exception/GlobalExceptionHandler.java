package com.dmart.ecommerce.Exception;

import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    private ResponseEntity<ApiResponseWithMessage> buildResponse(HttpStatus status, String message)
    {
        ApiResponseWithMessage response = new ApiResponseWithMessage(false, status, message);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponseWithMessage> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex)
    {
        return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, "HTTP Method not allowed. Please check the request method.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseWithMessage> handleHttpMessageNotReadable(HttpMessageNotReadableException ex)
    {
        String message = "Request body is missing or malformed.";
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException ife) {
            String fieldName = ife.getPath().isEmpty() ? "unknown" : ife.getPath().get(0).getFieldName();
            String expectedType = ife.getTargetType() != null ? ife.getTargetType().getSimpleName() : "unknown";
            message = String.format("Field '%s' must be of type %s.", fieldName, expectedType);
        } else if (cause instanceof JsonMappingException) {
            message = "Invalid input structure.";
        }

        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseWithMessage> handleValidationErrors(MethodArgumentNotValidException ex)
    {
        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildResponse(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseWithMessage> handleNotFound(ResourceNotFoundException ex)
    {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ResourceIncorrectException.class)
    public ResponseEntity<ApiResponseWithMessage> handleResourceIncorrect(ResourceIncorrectException ex)
    {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponseWithMessage> handleResourceAlreadyExists(ResourceAlreadyExistsException ex)
    {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseWithMessage> handleIllegalArgumentException(IllegalArgumentException ex)
    {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseWithMessage> handleGenericException(Exception ex)
    {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + ex.getMessage());
    }
}

