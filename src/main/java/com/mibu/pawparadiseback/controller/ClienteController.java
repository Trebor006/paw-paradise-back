package com.mibu.pawparadiseback.controller;

import com.mibu.pawparadiseback.services.ClienteService;
import com.mibu.pawparadiseback.services.dto.input.ClienteRequestDto;
import com.mibu.pawparadiseback.services.dto.output.ClienteResponseDto;
import com.mibu.pawparadiseback.services.dto.output.SuccessDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

  @GetMapping
  public ResponseEntity<SuccessDto> getAllClientes() {
    List<ClienteResponseDto> response = clienteService.getAllClientes();
    SuccessDto successDto =
        SuccessDto.builder().message("Clients retrieved successfully").data(response).build();
    return ResponseEntity.ok(successDto);
  }

  @GetMapping("/getBy/{ci}")
  public ResponseEntity<SuccessDto> getClienteByCi(@PathVariable("ci") String ci) {
    ClienteResponseDto response = clienteService.getClienteByCi(ci);
    SuccessDto successDto =
        SuccessDto.builder().message("Client retrieved successfully").data(response).build();
    return ResponseEntity.ok(successDto);
  }

  @DeleteMapping("/{ci}")
  public ResponseEntity<SuccessDto> deleteCliente(@PathVariable("ci") String ci) {
    clienteService.deleteCliente(ci);
    SuccessDto successDto = SuccessDto.builder().message("Cliente deleted successfully").build();
    return ResponseEntity.ok(successDto);
  }
}
