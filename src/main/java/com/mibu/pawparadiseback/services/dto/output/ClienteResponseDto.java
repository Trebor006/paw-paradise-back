package com.mibu.pawparadiseback.services.dto.output;

import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClienteResponseDto {
  Integer id;
  LocalDateTime createdAt;
  Integer personId;
  String clientName;
  String clientEmail;
  String personCi;
}
