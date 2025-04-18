package com.mibu.pawparadiseback.services.dto.output;

import com.mibu.pawparadiseback.domain.enums.StatusEnum;
import java.time.LocalDate;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MascotaResponseDto {

  Long id;
  String name;
  String type;
  String gender;
  String breed;
  LocalDate birthdate;
  String image;
  Long clientId;
  StatusEnum status;
}
