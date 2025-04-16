package com.mibu.pawparadiseback.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mibu.pawparadiseback.domain.Client;
import com.mibu.pawparadiseback.domain.Person;
import com.mibu.pawparadiseback.domain.Pet;
import com.mibu.pawparadiseback.repository.ClienteRepository;
import com.mibu.pawparadiseback.repository.PersonRepository;
import com.mibu.pawparadiseback.repository.PetRepository;
import com.mibu.pawparadiseback.services.dto.input.MascotaRequestDto;
import com.mibu.pawparadiseback.services.dto.output.MascotaResponseDto;
import com.mibu.pawparadiseback.services.exceptions.ClientNotFoundException;
import com.mibu.pawparadiseback.services.exceptions.PersonNotFoundException;
import com.mibu.pawparadiseback.services.mapper.MascotaMapper;
import com.mibu.pawparadiseback.util.MockUtil;
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
    when(mascotaMapper.toDto(pet)).thenReturn(mascotaResponseDto);

    // When
    MascotaResponseDto result = mascotaServiceImpl.registrarMascota(mascotaRequestDto);

    // Then
    assertEquals(mascotaResponseDto, result);
    verify(personRepository).findByCi(mascotaRequestDto.getCi());
    verify(clienteRepository).findByPerson(person);
    verify(petRepository).save(pet);
    verify(mascotaMapper).toDto(pet);
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
}
