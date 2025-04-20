package com.mibu.pawparadiseback.exceptions;

import com.mibu.pawparadiseback.services.dto.output.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ClienteControllerAdvice {

  @ExceptionHandler({
    CustomerNotFoundException.class,
    PersonNotFoundException.class,
    ClientNotFoundException.class,
    CustomerRegisteredException.class
  })
  public ResponseEntity<ErrorDto> handleException(Object ex) {
    String message = "";
    if (ex instanceof CustomerNotFoundException) {
      message = ((CustomerNotFoundException) ex).getMessage();
    } else if (ex instanceof PersonNotFoundException) {
      message = ((PersonNotFoundException) ex).getMessage();
    } else if (ex instanceof ClientNotFoundException) {
      message = ((ClientNotFoundException) ex).getMessage();
    } else if (ex instanceof CustomerRegisteredException) {
      message = ((CustomerRegisteredException) ex).getMessage();
    }

    ErrorDto errorDto =
        ErrorDto.builder()
            .success(false)
            .message(message)
            .details(message)
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();
    log.error(errorDto.toString());

    return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
