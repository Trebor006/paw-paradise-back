package com.mibu.pawparadiseback.controller;

import com.mibu.pawparadiseback.services.ClienteService;
import com.mibu.pawparadiseback.services.dto.input.ClienteRequestDto;
import com.mibu.pawparadiseback.services.dto.output.ClienteResponseDto;
import com.mibu.pawparadiseback.services.dto.output.SuccessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clientes")
public class ClienteController {

  private final ClienteService clienteService;

  @PostMapping
  public ResponseEntity<SuccessDto> createCliente(
      @RequestBody ClienteRequestDto clienteRequestDto) {
    ClienteResponseDto response = clienteService.createCliente(clienteRequestDto);
    SuccessDto successDto =
        SuccessDto.builder().message("Cliente created successfully").data(response).build();
    return ResponseEntity.ok(successDto);
  }
}
