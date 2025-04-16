package com.mibu.pawparadiseback.controller;

import com.mibu.pawparadiseback.services.MascotaService;
import com.mibu.pawparadiseback.services.dto.input.MascotaRequestDto;
import com.mibu.pawparadiseback.services.dto.output.MascotaResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
public class MascotaController {

  private final MascotaService mascotaService;

  @PostMapping
  public ResponseEntity<MascotaResponseDto> registrarMascota(@RequestBody MascotaRequestDto mascotaRequestDto) {
    log.info("Registering a new pet: {}", mascotaRequestDto);
    MascotaResponseDto response = mascotaService.registrarMascota(mascotaRequestDto);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
