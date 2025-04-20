package com.mibu.pawparadiseback.exceptions;

import com.mibu.pawparadiseback.services.dto.output.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler({
    Exception.class,
    RuntimeException.class,
    DataIntegrityViolationException.class,
    ConstraintViolationException.class
  })
  public ResponseEntity<ErrorDto> handleException(Object ex) {
    String message = "";
    if (ex instanceof ConstraintViolationException) {
      message = "Error con las restricciones de la base de datos";
      log.error(((ConstraintViolationException) ex).getMessage());
    } else if (ex instanceof DataIntegrityViolationException) {
      message = "Error con las restricciones de la base de datos";
      log.error(((DataIntegrityViolationException) ex).getMessage());
    } else if (ex instanceof RuntimeException) {
      message = ((RuntimeException) ex).getMessage();
    } else if (ex instanceof Exception) {
      message = ((Exception) ex).getMessage();
    }

    ErrorDto errorDto =
        ErrorDto.builder()
            .success(false)
            .message("An error occurred : " + message)
            .details("An error occurred : " + message)
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();
    log.error(errorDto.toString());

    return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
