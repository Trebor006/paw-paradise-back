package com.mibu.pawparadiseback.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mibu.pawparadiseback.domain.Client;
import com.mibu.pawparadiseback.domain.Person;
import com.mibu.pawparadiseback.domain.enums.StatusEnum;
import com.mibu.pawparadiseback.exceptions.CustomerNotFoundException;
import com.mibu.pawparadiseback.repository.ClienteRepository;
import com.mibu.pawparadiseback.repository.PersonRepository;
import com.mibu.pawparadiseback.services.dto.input.ClienteRequestDto;
import com.mibu.pawparadiseback.services.dto.input.UpdateClienteRequestDto;
import com.mibu.pawparadiseback.services.dto.output.ClienteResponseDto;
import com.mibu.pawparadiseback.services.mapper.ClienteMapper;
import com.mibu.pawparadiseback.services.mapper.PersonMapper;
import com.mibu.pawparadiseback.util.MockUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ClienteServiceImplTest {

  @Mock private PersonRepository personRepository;
  @Mock private ClienteRepository clienteRepository;
  @Mock private PersonMapper personMapper;
  @Mock private ClienteMapper clienteMapper;

  @InjectMocks private ClienteServiceImpl clienteServiceImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should create a new client when person does not exist")
  void shouldCreateClientWhenPersonDoesNotExist() {
    // Given
    ClienteRequestDto clienteRequestDto = mock(ClienteRequestDto.class);
    Person person = mock(Person.class);
    Client client = mock(Client.class);
    ClienteResponseDto clienteResponseDto = mock(ClienteResponseDto.class);

    when(clienteRequestDto.getCi()).thenReturn("123456");
    when(personRepository.findByCi("123456")).thenReturn(Optional.empty());
    when(personMapper.toEntity(clienteRequestDto)).thenReturn(person);
    when(personRepository.save(person)).thenReturn(person);
    when(clienteMapper.toEntity(clienteRequestDto)).thenReturn(client);
    when(clienteRepository.save(client)).thenReturn(client);
    when(clienteMapper.mapToResponseDto(client)).thenReturn(clienteResponseDto);

    // When
    ClienteResponseDto result = clienteServiceImpl.createCliente(clienteRequestDto);

    // Then
    assertEquals(clienteResponseDto, result);
    verify(personRepository).findByCi("123456");
    verify(personRepository).save(person);
    verify(clienteRepository).save(client);
    verify(clienteMapper).mapToResponseDto(client);
  }

  @Test
  @DisplayName("Should create a new client when person already exists")
  void shouldCreateClientWhenPersonExists() {
    // Given
    ClienteRequestDto clienteRequestDto = mock(ClienteRequestDto.class);
    Person person = mock(Person.class);
    Client client = mock(Client.class);
    ClienteResponseDto clienteResponseDto = mock(ClienteResponseDto.class);

    when(clienteRequestDto.getCi()).thenReturn("123456");
    when(personRepository.findByCi("123456")).thenReturn(Optional.of(person));
    when(clienteMapper.toEntity(clienteRequestDto)).thenReturn(client);
    when(clienteRepository.save(client)).thenReturn(client);
    when(clienteMapper.mapToResponseDto(client)).thenReturn(clienteResponseDto);

    // When
    ClienteResponseDto result = clienteServiceImpl.createCliente(clienteRequestDto);

    // Then
    assertEquals(clienteResponseDto, result);
    verify(personRepository).findByCi("123456");
    verify(clienteRepository).save(client);
    verify(clienteMapper).mapToResponseDto(client);
  }

  //  @Test
  //  @DisplayName("Should throw exception when client already exists")
  //  void shouldThrowExceptionWhenClientAlreadyExists() {
  //    // Given
  //    ClienteRequestDto clienteRequestDto = mock(ClienteRequestDto.class);
  //    Person person = mock(Person.class);
  //    Client client = mock(Client.class);
  //
  //    when(clienteRequestDto.getCi()).thenReturn("123456");
  //    when(personRepository.findByCi("123456")).thenReturn(Optional.of(person));
  //    when(clienteRepository.findByPerson(person)).thenReturn(Optional.of(client));
  //
  //    // When / Then
  //    CustomerRegisteredException exception =
  //        assertThrows(
  //            CustomerRegisteredException.class,
  //            () -> clienteServiceImpl.createCliente(clienteRequestDto));
  //
  //    assertEquals("El cliente ya está registrado.", exception.getMessage());
  //    verify(personRepository).findByCi("123456");
  //    verify(clienteRepository).findByPerson(person);
  //  }

  @Test
  @DisplayName("Should return all clients")
  void shouldReturnAllClients() {
    // Given
    Client client1 = MockUtil.createClient();
    Client client2 = MockUtil.createClient();
    ClienteResponseDto dto1 = MockUtil.createClienteResponseDto();
    ClienteResponseDto dto2 = MockUtil.createClienteResponseDto();

    List<ClienteResponseDto> expectedResponse = Arrays.asList(dto1, dto2);

    when(clienteRepository.findAllByStatus(any())).thenReturn(Arrays.asList(client1, client2));
    when(clienteMapper.toDtoListFromClients(anyList())).thenReturn(expectedResponse);

    // When
    List<ClienteResponseDto> result = clienteServiceImpl.getAllClientes();

    // Then
    assertEquals(expectedResponse.size(), result.size());
    verify(clienteRepository).findAllByStatus(any());
    verify(clienteMapper).toDtoListFromClients(anyList());
  }

  @Test
  @DisplayName("Should set client status to INACTIVE when deleting an existing client")
  void givenExistingClientIdShouldSetStatusToInactive() {
    // Given
    String ci = "12345678";
    Person person = MockUtil.createMockPerson(ci);
    Client client = MockUtil.createMockClient(person);

    when(personRepository.findByCi(any())).thenReturn(Optional.of(person));
    when(clienteRepository.findByPerson(any())).thenReturn(Optional.of(client));
    when(clienteMapper.mapToResponseDto(any())).thenReturn(MockUtil.createMockClienteResponseDto(client));

    // When
    clienteServiceImpl.deleteCliente(ci);

    // Then
    assertEquals(StatusEnum.INACTIVE, client.getStatus());
  }

  @Test
  @DisplayName("Should throw exception when deleting a non-existent client")
  void givenNonExistentClientIdThenThrowException() {
    // Given
    String ci = "12345678";
    Person person = MockUtil.createMockPerson(ci);
    when(personRepository.findByCi(any())).thenReturn(Optional.of(person));
    when(clienteRepository.findByPerson(any())).thenReturn(Optional.empty());

    // When / Then
    Exception exception =
        assertThrows(CustomerNotFoundException.class, () -> clienteServiceImpl.deleteCliente(ci));

    assertEquals("Client associated with CI " + ci + " not found", exception.getMessage());
  }

  @Test
  @DisplayName("Should retrieve a client by CI when the client exists")
  void givenExistingClientWhenGetClienteByCiShouldReturnClienteResponseDto() {
    // given
    String ci = "12345678";
    Person person = MockUtil.createMockPerson(ci);
    Client client = MockUtil.createMockClient(person);

    when(personRepository.findByCi(any())).thenReturn(Optional.of(person));
    when(clienteRepository.findByPerson(any())).thenReturn(Optional.of(client));
    when(clienteMapper.mapToResponseDto(any())).thenReturn(MockUtil.createMockClienteResponseDto(client));

    // when
    ClienteResponseDto result = clienteServiceImpl.getClienteByCi(ci);

    // then
    assertNotNull(result);
  }

  @Test
  @DisplayName("Should update a client by CI when valid data is provided")
  void givenValidCiAndUpdateRequest_whenUpdateClienteByCi_thenShouldUpdateClient() {
    // Given
    String ci = "123456";
    UpdateClienteRequestDto updateRequest = new UpdateClienteRequestDto();
    updateRequest.setName("Updated Name");
    updateRequest.setLastname("Updated Lastname");
    updateRequest.setEmail("updated.email@example.com");
    updateRequest.setPhone("123-456-7890");
    updateRequest.setAddress("Updated Address");
    updateRequest.setCountry("Updated Country");

    Person person = MockUtil.createMockPerson(ci);
    Client client = MockUtil.createMockClient(person);

    when(personRepository.findByCi(ci)).thenReturn(Optional.of(person));
    when(clienteRepository.findByPerson(person)).thenReturn(Optional.of(client));
    when(clienteMapper.mapToResponseDto(client)).thenReturn(new ClienteResponseDto());

    // When
    ClienteResponseDto response = clienteServiceImpl.updateClienteByCi(ci, updateRequest);

    // Then
    assertNotNull(response);
    verify(personRepository).save(person);
    verify(clienteRepository, never()).save(client);
  }
}
