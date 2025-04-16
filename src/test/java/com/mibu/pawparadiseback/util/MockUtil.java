package com.mibu.pawparadiseback.util;

import com.mibu.pawparadiseback.domain.Client;
import com.mibu.pawparadiseback.domain.Person;
import com.mibu.pawparadiseback.domain.Pet;
import com.mibu.pawparadiseback.services.dto.input.ClienteRequestDto;
import com.mibu.pawparadiseback.services.dto.input.MascotaRequestDto;
import com.mibu.pawparadiseback.services.dto.output.ClienteResponseDto;
import com.mibu.pawparadiseback.services.dto.output.MascotaResponseDto;

public class MockUtil {

  public static ClienteRequestDto createClienteRequestDto() {
    ClienteRequestDto dto = new ClienteRequestDto();
    dto.setCi("123456");
    dto.setName("John");
    dto.setLastname("Doe");
    return dto;
  }

  public static ClienteResponseDto createClienteResponseDto() {
    ClienteResponseDto dto = new ClienteResponseDto();
    dto.setId(1);
    dto.setClientName("John");
    //    dto.setLastName("Doe");
    return dto;
  }

  public static Person createPerson() {
    Person person = new Person();
    person.setId(1L);
    person.setCi("123456");
    person.setName("John");
    person.setLastname("Doe");
    return person;
  }

  public static Client createClient() {
    Client client = new Client();
    client.setId(1L);
    client.setPerson(createPerson());
    return client;
  }

  public static MascotaRequestDto createMascotaRequestDto() {
    MascotaRequestDto dto = new MascotaRequestDto();
    return dto;
  }

  public static Pet createPet() {
    return Pet.builder().build();
  }

  public static MascotaResponseDto createMascotaResponseDto() {
    return MascotaResponseDto.builder().build();
  }
}
