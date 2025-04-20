package com.mibu.pawparadiseback.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mibu.pawparadiseback.domain.Client;
import com.mibu.pawparadiseback.domain.Person;
import com.mibu.pawparadiseback.domain.Pet;
import com.mibu.pawparadiseback.domain.enums.StatusEnum;
import com.mibu.pawparadiseback.repository.ClienteRepository;
import com.mibu.pawparadiseback.repository.PersonRepository;
import com.mibu.pawparadiseback.repository.PetRepository;
import com.mibu.pawparadiseback.services.dto.input.MascotaRequestDto;
import com.mibu.pawparadiseback.services.dto.input.UpdateMascotaRequestDto;
import com.mibu.pawparadiseback.services.dto.output.MascotaResponseDto;
import com.mibu.pawparadiseback.exceptions.ClientNotFoundException;
import com.mibu.pawparadiseback.exceptions.PersonNotFoundException;
import com.mibu.pawparadiseback.exceptions.PetNotFoundException;
import com.mibu.pawparadiseback.services.mapper.MascotaMapper;
import com.mibu.pawparadiseback.util.MockUtil;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MascotaServiceImplTest {

  @Mock private PersonRepository personRepository;
  @Mock private ClienteRepository clienteRepository;
  @Mock private PetRepository petRepository;
  @Mock private MascotaMapper mascotaMapper;

  @InjectMocks private MascotaServiceImpl mascotaServiceImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should register a pet when person and client exist")
  void shouldRegisterPetWhenPersonAndClientExist() {
    // Given
    MascotaRequestDto mascotaRequestDto = MockUtil.createMascotaRequestDto();
    Person person = MockUtil.createPerson();
    Client client = MockUtil.createClient();
    Pet pet = MockUtil.createPet();
    MascotaResponseDto mascotaResponseDto = MockUtil.createMascotaResponseDto();

    when(personRepository.findByCi(mascotaRequestDto.getCi())).thenReturn(Optional.of(person));
    when(clienteRepository.findByPerson(person)).thenReturn(Optional.of(client));
    when(mascotaMapper.toEntity(mascotaRequestDto)).thenReturn(pet);
    when(petRepository.save(pet)).thenReturn(pet);
    when(mascotaMapper.toResponseDto(pet)).thenReturn(mascotaResponseDto);

    // When
    MascotaResponseDto result = mascotaServiceImpl.registrarMascota(mascotaRequestDto);

    // Then
    assertEquals(mascotaResponseDto, result);
    verify(personRepository).findByCi(mascotaRequestDto.getCi());
    verify(clienteRepository).findByPerson(person);
    verify(petRepository).save(pet);
    verify(mascotaMapper).toResponseDto(pet);
  }

  @Test
  @DisplayName("Should throw exception when person does not exist")
  void shouldThrowExceptionWhenPersonDoesNotExist() {
    // Given
    MascotaRequestDto mascotaRequestDto = MockUtil.createMascotaRequestDto();

    when(personRepository.findByCi(mascotaRequestDto.getCi())).thenReturn(Optional.empty());

    // When / Then
    PersonNotFoundException exception =
        assertThrows(
            PersonNotFoundException.class,
            () -> mascotaServiceImpl.registrarMascota(mascotaRequestDto));

    assertEquals(
        "El cliente con CI " + mascotaRequestDto.getCi() + " no está registrado.",
        exception.getMessage());
    verify(personRepository).findByCi(mascotaRequestDto.getCi());
    verifyNoInteractions(clienteRepository, petRepository, mascotaMapper);
  }

  @Test
  @DisplayName("Should throw exception when client does not exist")
  void shouldThrowExceptionWhenClientDoesNotExist() {
    // Given
    MascotaRequestDto mascotaRequestDto = MockUtil.createMascotaRequestDto();
    Person person = MockUtil.createPerson();

    when(personRepository.findByCi(mascotaRequestDto.getCi())).thenReturn(Optional.of(person));
    when(clienteRepository.findByPerson(person)).thenReturn(Optional.empty());

    // When / Then
    ClientNotFoundException exception =
        assertThrows(
            ClientNotFoundException.class,
            () -> mascotaServiceImpl.registrarMascota(mascotaRequestDto));

    assertEquals(
        "El cliente con CI " + mascotaRequestDto.getCi() + " no está registrado.",
        exception.getMessage());
    verify(personRepository).findByCi(mascotaRequestDto.getCi());
    verify(clienteRepository).findByPerson(person);
    verifyNoInteractions(petRepository, mascotaMapper);
  }

  @Test
  @DisplayName("Should return a list of pets for a given CI")
  void givenCiObtenerMascotasPorClienteShouldReturnListOfPets() {
    // given
    String ci = "12345678";
    Person person = new Person();
    person.setCi(ci);
    Client client = new Client();
    client.setId(1L);
    client.setPerson(person);

    List<Pet> pets = MockUtil.getPets(client);

    when(personRepository.findByCi(ci)).thenReturn(Optional.of(person));
    when(clienteRepository.findByPerson(person)).thenReturn(Optional.of(client));
    when(petRepository.findByClientId(anyLong())).thenReturn(pets);

    // when
    List<MascotaResponseDto> result = mascotaServiceImpl.obtenerMascotasPorCliente(ci);

    // then
    assertNotNull(result);
    assertEquals(pets.size(), result.size());
    verify(personRepository, times(1)).findByCi(ci);
    verify(clienteRepository, times(1)).findByPerson(person);
    verify(petRepository, times(1)).findByClientId(client.getId());
  }

  @Test
  @DisplayName("Should update a pet when client and pet exist and pet is active")
  void shouldUpdatePetWhenClientAndPetExistAndPetIsActive() {
    // Given
    String ci = "123456";
    Long mascotaId = 1L;
    UpdateMascotaRequestDto updateMascotaRequestDto = MockUtil.createUpdateMascotaRequestDto();
    Person person = MockUtil.createPerson();
    Client client = MockUtil.createClient();
    Pet pet = MockUtil.createPet();
    pet.setStatus(StatusEnum.ACTIVE);
    MascotaResponseDto mascotaResponseDto = MockUtil.createMascotaResponseDto();

    when(personRepository.findByCi(ci)).thenReturn(Optional.of(person));
    when(clienteRepository.findByPerson(person)).thenReturn(Optional.of(client));
    when(petRepository.findByIdAndClientId(mascotaId, client.getId())).thenReturn(Optional.of(pet));
    when(petRepository.save(pet)).thenReturn(pet);
    when(mascotaMapper.toResponseDto(pet)).thenReturn(mascotaResponseDto);

    // When
    MascotaResponseDto result = mascotaServiceImpl.actualizarMascota(ci, mascotaId, updateMascotaRequestDto);

    // Then
    assertEquals(mascotaResponseDto, result);
    verify(petRepository).save(pet);
  }

  @Test
  @DisplayName("Should inactivate a pet when client CI and pet ID are valid and pet is active")
  void givenValidCiAndMascotaIdWhenEliminarMascotaThenPetIsInactive() {
    // Given
    String ci = "123456";
    Long mascotaId = 1L;

    Person person = new Person();
    person.setCi(ci);

    Client client = new Client();
    client.setId(1L);
    client.setPerson(person);

    Pet pet = new Pet();
    pet.setId(mascotaId);
    pet.setClientId(client.getId());
    pet.setStatus(StatusEnum.ACTIVE);

    when(personRepository.findByCi(ci)).thenReturn(Optional.of(person));
    when(clienteRepository.findByPerson(person)).thenReturn(Optional.of(client));
    when(petRepository.findByIdAndClientId(mascotaId, client.getId())).thenReturn(Optional.of(pet));

    // When
    mascotaServiceImpl.eliminarMascota(ci, mascotaId);

    // Then
    assertEquals(StatusEnum.INACTIVE, pet.getStatus());
  }

  @Test
  @DisplayName("Should throw exception when client CI is not found")
  void givenInvalidCiWhenEliminarMascotaThenThrowPersonNotFoundException() {
    // Given
    String ci = "123456";
    Long mascotaId = 1L;

    when(personRepository.findByCi(ci)).thenReturn(Optional.empty());

    // When & Then
    assertThrows(
        PersonNotFoundException.class, () -> mascotaServiceImpl.eliminarMascota(ci, mascotaId));
  }

  @Test
  @DisplayName("Should throw exception when pet is not active")
  void givenInactivePetWhenEliminarMascotaThenThrowIllegalStateException() {
    // Given
    String ci = "123456";
    Long mascotaId = 1L;

    Person person = new Person();
    person.setCi(ci);

    Client client = new Client();
    client.setId(1L);
    client.setPerson(person);

    Pet pet = new Pet();
    pet.setId(mascotaId);
    pet.setClientId(client.getId());
    pet.setStatus(StatusEnum.INACTIVE);

    when(personRepository.findByCi(ci)).thenReturn(Optional.of(person));
    when(clienteRepository.findByPerson(person)).thenReturn(Optional.of(client));
    when(petRepository.findByIdAndClientId(mascotaId, client.getId())).thenReturn(Optional.of(pet));

    // When & Then
    assertThrows(
        IllegalStateException.class, () -> mascotaServiceImpl.eliminarMascota(ci, mascotaId));
  }

  @Test
  @DisplayName("Should throw exception when pet is not found")
  void givenInvalidMascotaIdWhenEliminarMascotaThenThrowPetNotFoundException() {
    // Given
    String ci = "123456";
    Long mascotaId = 1L;

    Person person = new Person();
    person.setCi(ci);

    Client client = new Client();
    client.setId(1L);
    client.setPerson(person);

    when(personRepository.findByCi(ci)).thenReturn(Optional.of(person));
    when(clienteRepository.findByPerson(person)).thenReturn(Optional.of(client));
    when(petRepository.findByIdAndClientId(mascotaId, client.getId())).thenReturn(Optional.empty());

    // When & Then
    assertThrows(
        PetNotFoundException.class, () -> mascotaServiceImpl.eliminarMascota(ci, mascotaId));
  }

  @Test
  @DisplayName("Should return a list of active pets")
  void givenActivePetsWhenObtenerMascotasActivasThenReturnListOfActivePets() {
    // Given
    Pet activePet1 = MockUtil.createPet();
    activePet1.setStatus(StatusEnum.ACTIVE);
    Pet activePet2 = MockUtil.createPet();
    activePet2.setStatus(StatusEnum.ACTIVE);

    List<Pet> pets = List.of(activePet1, activePet2);
    when(petRepository.findByStatus(any())).thenReturn(pets);

    // When
    List<MascotaResponseDto> result = mascotaServiceImpl.obtenerMascotasActivas();

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    verify(petRepository, times(1)).findByStatus(any());
  }

  @Test
  @DisplayName("Should fetch a pet by ID when the pet is active")
  void shouldFetchPetByIdWhenActive() {
    // Given
    Long mascotaId = 1L;
    Pet pet = MockUtil.createPet();
    pet.setStatus(StatusEnum.ACTIVE);

    when(petRepository.findById(mascotaId)).thenReturn(Optional.of(pet));
    when(mascotaMapper.toResponseDto(pet)).thenReturn(MockUtil.createMascotaResponseDto());

    // When
    MascotaResponseDto result = mascotaServiceImpl.obtenerMascotaPorId(mascotaId);

    // Then
    assertNotNull(result);
    assertEquals(pet.getId(), result.getId());
    verify(petRepository).findById(mascotaId);
    verify(mascotaMapper).toResponseDto(pet);
  }
}
