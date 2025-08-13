package com.products.api.exception;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import com.products.api.constant.error.ErrorConstant;

@ControllerAdvice
public class GlobalExceptionHandlerMvc {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleGlobalException(
            Exception exception, WebRequest webRequest) {

        ErrorDetail errorDetails = ErrorDetail.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(webRequest.getDescription(false))
                .errorCode(ErrorConstant.INTERNAL_SERVER_ERROR)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorDetail> handleGenericException(
            GenericException exception, WebRequest webRequest) {

        ErrorDetail errorDetails = ErrorDetail.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(webRequest.getDescription(false))
                .errorCode(exception.getStatus().name())
                .build();

        return ResponseEntity.status(exception.getStatus()).body(errorDetails);
    }
}
