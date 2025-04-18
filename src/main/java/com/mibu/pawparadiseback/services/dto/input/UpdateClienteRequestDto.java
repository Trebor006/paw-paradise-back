package com.mibu.pawparadiseback.services.dto.input;

import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
