package com.fx.deals.exception;

import com.fx.deals.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);



    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleExceptions(Exception ex){
        if(ex instanceof  ValidationException){
            ValidationException validationException = (ValidationException) ex;
            log.error("Validation exception has occurred", ex);
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Failed validation", validationException.getErrors()));
        }else {
            log.error("Exception has occurred", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed", ex.getMessage()));
        }

    }
}
