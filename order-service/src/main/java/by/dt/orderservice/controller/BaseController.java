package by.dt.orderservice.controller;

import by.dt.orderservice.exception.ClientIdNotFoundException;
import by.dt.orderservice.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface BaseController {

    @ExceptionHandler(value = ClientIdNotFoundException.class)
    default ResponseEntity<Object> exception(ClientIdNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OrderNotFoundException.class)
    default ResponseEntity<Object> exception(OrderNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
