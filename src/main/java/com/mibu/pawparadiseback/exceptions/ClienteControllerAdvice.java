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

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleException(Exception ex) {
    ErrorDto errorDto =
        ErrorDto.builder()
            .success(false)
            .message("An error occurred" + ex.getMessage())
            .details("An error occurred" + ex.getMessage())
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();
    log.error(errorDto.toString());

    return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
