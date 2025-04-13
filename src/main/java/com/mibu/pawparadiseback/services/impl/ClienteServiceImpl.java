package com.mibu.pawparadiseback.services.impl;

import com.mibu.pawparadiseback.domain.Client;
import com.mibu.pawparadiseback.domain.Person;
import com.mibu.pawparadiseback.domain.enums.StatusEnum;
import com.mibu.pawparadiseback.exceptions.CustomerNotFoundException;
import com.mibu.pawparadiseback.exceptions.CustomerRegisteredException;
import com.mibu.pawparadiseback.repository.ClienteRepository;
import com.mibu.pawparadiseback.repository.PersonRepository;
import com.mibu.pawparadiseback.services.ClienteService;
import com.mibu.pawparadiseback.services.dto.input.ClienteRequestDto;
import com.mibu.pawparadiseback.services.dto.output.ClienteResponseDto;
import com.mibu.pawparadiseback.services.mapper.ClienteMapper;
import com.mibu.pawparadiseback.services.mapper.PersonMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

  public static final String EL_CLIENTE_YA_ESTA_REGISTRADO = "El cliente ya está registrado.";

  private final PersonRepository personRepository;
  private final ClienteRepository clienteRepository;
  private final PersonMapper personMapper;
  private final ClienteMapper clienteMapper;

  @Override
  public ClienteResponseDto createCliente(ClienteRequestDto clienteRequestDto) {
    Optional<Person> existingPerson = personRepository.findByCi(clienteRequestDto.getCi());
    Person person = getPerson(clienteRequestDto, existingPerson);

    Optional<Client> existingClient = clienteRepository.findByPerson(person);
    if (existingClient.isPresent()) {
      throw new CustomerRegisteredException(EL_CLIENTE_YA_ESTA_REGISTRADO);
    }

    Client client = clienteMapper.toEntity(clienteRequestDto);
    client.setPerson(person);
    client = clienteRepository.save(client);

    return clienteMapper.toDto(client);
  }

  private Person getPerson(ClienteRequestDto clienteRequestDto, Optional<Person> existingPerson) {
    Person person;
    if (existingPerson.isPresent()) {
      person = existingPerson.get();
    } else {
      person = personMapper.toEntity(clienteRequestDto);
      person = personRepository.save(person);
    }
    return person;
  }

  @Override
  public List<ClienteResponseDto> getAllClientes() {
    List<Client> clients = clienteRepository.findAllByStatus(StatusEnum.ACTIVE);
    return clienteMapper.toDtoListFromClients(clients);
  }

  @Override
  public void deleteCliente(Integer id) {
    Client client =
        clienteRepository
            .findById(id)
            .orElseThrow(() -> new CustomerNotFoundException("Client not found"));
    client.setStatus(StatusEnum.INACTIVE);
    clienteRepository.save(client);
  }
}
