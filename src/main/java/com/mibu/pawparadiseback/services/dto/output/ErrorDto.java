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
public class ErrorDto {

  @Builder.Default boolean success = false;
  String message;
  String details;
  HttpStatus httpStatus;
}
