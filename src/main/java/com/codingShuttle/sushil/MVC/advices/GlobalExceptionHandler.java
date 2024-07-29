package com.codingShuttle.sushil.MVC.advices;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(RuntimeException exception){
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class )
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception){
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage()).
                build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handlerInputValidation(MethodArgumentNotValidException exception){
       List<String> errors =  exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage()).collect(Collectors.toList());

       ApiError apiError = ApiError.builder()
               .status(HttpStatus.BAD_REQUEST)
               .message("Input validation failed")
               .subErrors(errors)
               .build();
       return buildErrorResponseEntity(apiError);

    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError error){
        return new ResponseEntity<>(new ApiResponse<>(error),error.getStatus());
    }
}
