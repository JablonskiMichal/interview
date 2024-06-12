package com.verygoodbank.tes.web.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(CsvParseException.class)
    public void handleCsvParseException(CsvParseException exception) {
        throw new ResponseStatusException(INTERNAL_SERVER_ERROR, exception.getMessage());
    }
}
