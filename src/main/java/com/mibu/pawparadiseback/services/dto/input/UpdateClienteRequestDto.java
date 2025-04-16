package com.mibu.pawparadiseback.services.dto.input;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateClienteRequestDto {
  String ci;
  String name;
  String lastname;
  String email;
  String phone;
  String image;
  String gender;
  LocalDateTime birthdate;
  String type;
  String address;
  String country;
}
