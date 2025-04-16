package com.mibu.pawparadiseback.controller;

import com.mibu.pawparadiseback.services.MascotaService;
import com.mibu.pawparadiseback.services.dto.input.MascotaRequestDto;
import com.mibu.pawparadiseback.services.dto.input.UpdateMascotaRequestDto;
import com.mibu.pawparadiseback.services.dto.output.MascotaResponseDto;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping("/{ci}")
  public ResponseEntity<List<MascotaResponseDto>> listarMascotasPorCliente(@PathVariable("ci") String ci) {
    log.info("Listing pets for client with CI: {}", ci);
    List<MascotaResponseDto> mascotas = mascotaService.obtenerMascotasPorCliente(ci);
    return new ResponseEntity<>(mascotas, HttpStatus.OK);
  }

  @PutMapping("/cliente/{ci}/{mascotaId}")
  public ResponseEntity<MascotaResponseDto> actualizarMascota(
      @PathVariable("ci") String ci,
      @PathVariable("mascotaId") Long mascotaId,
      @RequestBody UpdateMascotaRequestDto updateMascotaRequestDto) {
    log.info("Updating pet with ID {} for client with CI {}", mascotaId, ci);
    MascotaResponseDto response = mascotaService.actualizarMascota(ci, mascotaId, updateMascotaRequestDto);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
