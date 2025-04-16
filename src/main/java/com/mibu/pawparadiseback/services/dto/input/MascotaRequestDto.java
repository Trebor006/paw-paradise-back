package com.mibu.pawparadiseback.services.dto.input;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MascotaRequestDto {

  String name;
  String type;
  String gender;
  String breed;
  LocalDate birthdate;
  String image;
  String ci;
}
