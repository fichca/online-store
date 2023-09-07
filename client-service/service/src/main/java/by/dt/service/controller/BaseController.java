package by.dt.service.controller;

import by.dt.service.exception.ClientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface BaseController {
    @ExceptionHandler(value = ClientNotFoundException.class)
    default ResponseEntity<Object> exception(ClientNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
