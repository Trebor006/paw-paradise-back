package com.mibu.pawparadiseback.services.impl;

import com.mibu.pawparadiseback.domain.Client;
import com.mibu.pawparadiseback.domain.Person;
import com.mibu.pawparadiseback.domain.Pet;
import com.mibu.pawparadiseback.domain.enums.StatusEnum;
import com.mibu.pawparadiseback.repository.ClienteRepository;
import com.mibu.pawparadiseback.repository.PersonRepository;
import com.mibu.pawparadiseback.repository.PetRepository;
import com.mibu.pawparadiseback.services.MascotaService;
import com.mibu.pawparadiseback.services.dto.input.MascotaRequestDto;
import com.mibu.pawparadiseback.services.dto.input.UpdateMascotaRequestDto;
import com.mibu.pawparadiseback.services.dto.output.MascotaResponseDto;
import com.mibu.pawparadiseback.services.exceptions.ClientNotFoundException;
import com.mibu.pawparadiseback.services.exceptions.PersonNotFoundException;
import com.mibu.pawparadiseback.services.exceptions.PetNotFoundException;
import com.mibu.pawparadiseback.services.mapper.MascotaMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MascotaServiceImpl implements MascotaService {

  private final PersonRepository personRepository;
  private final ClienteRepository clienteRepository;
  private final PetRepository petRepository;
  private final MascotaMapper mascotaMapper;

  @Override
  public MascotaResponseDto registrarMascota(MascotaRequestDto mascotaRequestDto) {
    Person person =
        personRepository
            .findByCi(mascotaRequestDto.getCi())
            .orElseThrow(
                () ->
                    new PersonNotFoundException(
                        "El cliente con CI " + mascotaRequestDto.getCi() + " no está registrado."));

    Client client =
        clienteRepository
            .findByPerson(person)
            .orElseThrow(
                () ->
                    new ClientNotFoundException(
                        "El cliente con CI " + mascotaRequestDto.getCi() + " no está registrado."));

    // Mapear la mascota y establecer el estado
    Pet pet = mascotaMapper.toEntity(mascotaRequestDto);
    pet.setClientId(client.getId());
    pet.setStatus(StatusEnum.ACTIVE);

    // Guardar la mascota
    pet = petRepository.save(pet);

    // Retornar la respuesta
    return mascotaMapper.toResponseDto(pet);
  }

  @Override
  public List<MascotaResponseDto> obtenerMascotasPorCliente(String ci) {
    Person person =
        personRepository
            .findByCi(ci)
            .orElseThrow(
                () ->
                    new PersonNotFoundException(
                        "El cliente con CI " + ci + " no está registrado."));

    Client client =
        clienteRepository
            .findByPerson(person)
            .orElseThrow(
                () ->
                    new ClientNotFoundException(
                        "El cliente con CI " + ci + " no está registrado."));

    return petRepository.findByClientId(client.getId()).stream()
        .map(mascotaMapper::toResponseDto)
        .collect(Collectors.toList());
  }

  @Override
  public MascotaResponseDto actualizarMascota(
      String ci, Long mascotaId, UpdateMascotaRequestDto updateMascotaRequestDto) {
    Person person =
        personRepository
            .findByCi(ci)
            .orElseThrow(
                () ->
                    new PersonNotFoundException(
                        "El cliente con CI " + ci + " no está registrado."));

    Client client =
        clienteRepository
            .findByPerson(person)
            .orElseThrow(
                () ->
                    new ClientNotFoundException(
                        "El cliente con CI " + ci + " no está registrado."));

    Pet pet =
        petRepository
            .findByIdAndClientId(mascotaId, client.getId())
            .orElseThrow(
                () ->
                    new PetNotFoundException(
                        "La mascota con ID "
                            + mascotaId
                            + " no pertenece al cliente con CI "
                            + ci
                            + "."));

    if (pet.getStatus() != StatusEnum.ACTIVE) {
      throw new IllegalStateException("Solo se pueden actualizar mascotas con estado ACTIVO.");
    }

    // Actualizar los campos de la mascota
    if (updateMascotaRequestDto.getName() != null) {
      pet.setName(updateMascotaRequestDto.getName());
    }
    if (updateMascotaRequestDto.getType() != null) {
      pet.setType(updateMascotaRequestDto.getType());
    }
    if (updateMascotaRequestDto.getGender() != null) {
      pet.setGender(updateMascotaRequestDto.getGender());
    }
    if (updateMascotaRequestDto.getBreed() != null) {
      pet.setBreed(updateMascotaRequestDto.getBreed());
    }
    if (updateMascotaRequestDto.getBirthdate() != null) {
      pet.setBirthdate(updateMascotaRequestDto.getBirthdate());
    }
    if (updateMascotaRequestDto.getImage() != null) {
      pet.setImage(updateMascotaRequestDto.getImage());
    }

    // Guardar los cambios
    pet = petRepository.save(pet);

    // Retornar la respuesta
    return mascotaMapper.toResponseDto(pet);
  }

  @Override
  public void eliminarMascota(String ci, Long mascotaId) {
    Person person =
        personRepository
            .findByCi(ci)
            .orElseThrow(
                () ->
                    new PersonNotFoundException(
                        "El cliente con CI " + ci + " no está registrado."));

    Client client =
        clienteRepository
            .findByPerson(person)
            .orElseThrow(
                () ->
                    new ClientNotFoundException(
                        "El cliente con CI " + ci + " no está registrado."));

    Pet pet =
        petRepository
            .findByIdAndClientId(mascotaId, client.getId())
            .orElseThrow(
                () ->
                    new PetNotFoundException(
                        "La mascota con ID "
                            + mascotaId
                            + " no pertenece al cliente con CI "
                            + ci
                            + "."));

    if (pet.getStatus() != StatusEnum.ACTIVE) {
      throw new IllegalStateException("Solo se pueden eliminar mascotas con estado ACTIVO.");
    }

    // Actualizar el estado de la mascota a INACTIVO en lugar de eliminarla
    pet.setStatus(StatusEnum.INACTIVE);
    petRepository.save(pet);
  }

  @Override
  public List<MascotaResponseDto> obtenerMascotasActivas() {
    return petRepository.findByStatus(StatusEnum.ACTIVE).stream()
        .map(mascotaMapper::toResponseDto)
        .collect(Collectors.toList());
  }
}
