package org.example.bankwithspringboot.dto.apiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseUtility {

    private ResponseUtility(){
    }
    public static <T> ResponseEntity<ApiResponse<T>> success(String message, HttpStatus status, T data){
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
        return new ResponseEntity<>(response, status);
    }
    public static ResponseEntity<ApiResponse<Object>> error(String message, HttpStatus status) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message(message)
                .build();
        return new ResponseEntity<>(response, status);
    }
}
