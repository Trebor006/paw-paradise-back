package com.mibu.pawparadiseback.domain;

import com.mibu.pawparadiseback.domain.enums.StatusEnum;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pet")
public class Pet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String gender;

  @Column(nullable = false)
  private String breed;

  @Column(nullable = false)
  private LocalDate birthdate;

  @Column(nullable = true)
  private String image;

  @Column(nullable = false)
  private Long clientId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StatusEnum status;
}
