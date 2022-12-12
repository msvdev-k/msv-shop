package org.msvdev.ee.shop.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<AppError> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);

        return new ResponseEntity<>(
                new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }


    @ExceptionHandler
    public ResponseEntity<AppError> badRequestExceptionHandler(BadRequestException e) {
        log.error(e.getMessage(), e);

        return new ResponseEntity<>(
                new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}