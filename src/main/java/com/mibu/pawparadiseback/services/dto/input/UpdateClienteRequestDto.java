package com.mibu.pawparadiseback.services.dto.input;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateClienteRequestDto {
  String name;
  String lastname;
  String email;
  String phone;
  String address;
  String country;
}
