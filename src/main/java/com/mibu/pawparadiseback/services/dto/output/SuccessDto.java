package com.mibu.pawparadiseback.services.dto.output;

import lombok.*;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuccessDto<T> {

  boolean success;
  T data;
  String message;
  HttpStatus httpStatus;
}
