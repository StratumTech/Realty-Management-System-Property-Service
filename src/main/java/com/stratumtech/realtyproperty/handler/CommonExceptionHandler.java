package com.stratumtech.realtyproperty.handler;

import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.stratumtech.realtyproperty.exception.*;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> badRequestException(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "error", "Bad request",
                "message", e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> failedToCreateException(FailedToCreateNewPropertyException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "error", "Cannot create new property",
                "message", e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> failedToDeleteException(FailedToDeletePropertyException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "error", "Cannot delete property",
                "message", e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> failedToUpdateException(FailedToUpdatePropertyException e){
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "error", "Cannot update property",
                "message", e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> notFoundException(PropertyNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "error", "Property not found",
                "message", e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> internalServerErrorException(RuntimeException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "error", "Internal server error",
                "message", e.getMessage()));
    }
}
