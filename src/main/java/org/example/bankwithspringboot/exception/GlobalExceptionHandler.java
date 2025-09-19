package org.example.bankwithspringboot.exception;

import org.example.bankwithspringboot.dto.apiResponse.ApiResponse;
import org.example.bankwithspringboot.dto.apiResponse.ResponseUtility;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseUtility.error("Invalid argument: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceAlreadyExists(ResourceAlreadyExistsException exception) {
        return ResponseUtility.error("Resource already exists: " + exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException exception) {
        return ResponseUtility.error("Resource not found: " + exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception exception) {
        return ResponseUtility.error("Internal Server Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrity(DataIntegrityViolationException exception) {
        return ResponseUtility.error("Data Constrain violated: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataAccess(DataAccessException exception) {
        return ResponseUtility.error("Database error, please try again later. "+exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}