package com.mibu.pawparadiseback.exceptions;

import com.mibu.pawparadiseback.services.dto.output.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class PetControllerAdvice {

  @ExceptionHandler({
    PetNotFoundException.class,
  })
  public ResponseEntity<ErrorDto> handleException(PetNotFoundException ex) {
    ErrorDto errorDto =
        ErrorDto.builder()
            .success(false)
            .message(ex.getMessage())
            .details(ex.getMessage())
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();
    log.error(errorDto.toString());

    return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
