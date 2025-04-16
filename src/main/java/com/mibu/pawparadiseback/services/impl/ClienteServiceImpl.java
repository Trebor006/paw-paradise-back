package com.mibu.pawparadiseback.services.impl;

import com.mibu.pawparadiseback.domain.Client;
import com.mibu.pawparadiseback.domain.Person;
import com.mibu.pawparadiseback.domain.enums.StatusEnum;
import com.mibu.pawparadiseback.exceptions.CustomerNotFoundException;
import com.mibu.pawparadiseback.repository.ClienteRepository;
import com.mibu.pawparadiseback.repository.PersonRepository;
import com.mibu.pawparadiseback.services.ClienteService;
import com.mibu.pawparadiseback.services.dto.input.ClienteRequestDto;
import com.mibu.pawparadiseback.services.dto.input.UpdateClienteRequestDto;
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
    Client client;
    if (existingClient.isPresent()) {
      client = existingClient.get();
    } else {
      client = clienteMapper.toEntity(clienteRequestDto);
      client.setPerson(person);
      client.setStatus(StatusEnum.ACTIVE);
      client = clienteRepository.save(client);
    }

    return clienteMapper.mapToResponseDto(client);
  }

  private Person getPerson(ClienteRequestDto clienteRequestDto, Optional<Person> existingPerson) {
    Person person;
    if (existingPerson.isPresent()) {
      person = existingPerson.get();
    } else {
      person = personMapper.toEntity(clienteRequestDto);
      person.setRole("CLIENT");
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
  public void deleteCliente(String ci) {
    Person person =
        personRepository
            .findByCi(ci)
            .orElseThrow(
                () -> new CustomerNotFoundException("Person with CI " + ci + " not found"));
    Client client =
        clienteRepository
            .findByPerson(person)
            .orElseThrow(
                () ->
                    new CustomerNotFoundException(
                        "Client associated with CI " + ci + " not found"));
    client.setStatus(StatusEnum.INACTIVE);
    clienteRepository.save(client);
  }

  @Override
  public ClienteResponseDto getClienteByCi(String ci) {
    Person person =
        personRepository
            .findByCi(ci)
            .orElseThrow(
                () -> new CustomerNotFoundException("Person with CI " + ci + " not found"));
    Client client =
        clienteRepository
            .findByPerson(person)
            .orElseThrow(
                () ->
                    new CustomerNotFoundException(
                        "Client associated with CI " + ci + " not found"));
    return clienteMapper.mapToResponseDto(client);
  }

  @Override
  public ClienteResponseDto updateClienteByCi(String ci, UpdateClienteRequestDto updateClienteRequestDto) {
    Person person =
        personRepository
            .findByCi(ci)
            .orElseThrow(
                () -> new CustomerNotFoundException("Person with CI " + ci + " not found"));
    Client client =
        clienteRepository
            .findByPerson(person)
            .orElseThrow(
                () ->
                    new CustomerNotFoundException(
                        "Client associated with CI " + ci + " not found"));

    if (updateClienteRequestDto.getName() != null) {
      person.setName(updateClienteRequestDto.getName());
    }
    if (updateClienteRequestDto.getLastname() != null) {
      person.setLastname(updateClienteRequestDto.getLastname());
    }
    if (updateClienteRequestDto.getEmail() != null) {
      person.setEmail(updateClienteRequestDto.getEmail());
    }
    if (updateClienteRequestDto.getPhone() != null) {
      person.setPhone(updateClienteRequestDto.getPhone());
    }
    if (updateClienteRequestDto.getImage() != null) {
      person.setImage(updateClienteRequestDto.getImage());
    }
    if (updateClienteRequestDto.getGender() != null) {
      person.setGender(updateClienteRequestDto.getGender());
    }
    if (updateClienteRequestDto.getBirthdate() != null) {
      person.setBirthdate(updateClienteRequestDto.getBirthdate());
    }
    if (updateClienteRequestDto.getType() != null) {
      person.setType(updateClienteRequestDto.getType());
    }
    if (updateClienteRequestDto.getAddress() != null) {
      person.setAddress(updateClienteRequestDto.getAddress());
    }
    if (updateClienteRequestDto.getCountry() != null) {
      person.setCountry(updateClienteRequestDto.getCountry());
    }

    personRepository.save(person);
    return clienteMapper.mapToResponseDto(client);
  }
}
