package com.mibu.pawparadiseback.services;

import com.mibu.pawparadiseback.services.dto.input.ClienteRequestDto;
import com.mibu.pawparadiseback.services.dto.output.ClienteResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface ClienteService {
  ClienteResponseDto createCliente(ClienteRequestDto clienteRequestDto);
}
