package com.mibu.pawparadiseback.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 15)
  private String ci;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, length = 100)
  private String lastname;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(length = 15)
  private String phone;

  @Column(columnDefinition = "TEXT")
  private String image;

  @Column(length = 10)
  private String gender;

  private LocalDateTime birthdate;

  @Column(length = 10)
  private String type;

  @Column(length = 100)
  private String address;

  @Column(length = 100)
  private String country;

  @Column(length = 50)
  private String role;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

}
