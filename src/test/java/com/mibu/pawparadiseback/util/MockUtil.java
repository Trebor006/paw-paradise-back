package com.mibu.pawparadiseback.util;

import com.mibu.pawparadiseback.domain.Client;
import com.mibu.pawparadiseback.domain.Person;
import com.mibu.pawparadiseback.domain.Pet;
import com.mibu.pawparadiseback.domain.enums.StatusEnum;
import com.mibu.pawparadiseback.services.dto.input.ClienteRequestDto;
import com.mibu.pawparadiseback.services.dto.input.MascotaRequestDto;
import com.mibu.pawparadiseback.services.dto.output.ClienteResponseDto;
import com.mibu.pawparadiseback.services.dto.output.MascotaResponseDto;
import java.time.LocalDate;
import java.util.List;

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

  public static List<Pet> getPets(Client client) {
    return List.of(
        new Pet(
            1L,
            "Buddy",
            "Dog",
            "Male",
            "Golden Retriever",
            LocalDate.of(2020, 1, 1),
            "image1.jpg",
            client.getId(),
            StatusEnum.ACTIVE),
        new Pet(
            2L,
            "Mittens",
            "Cat",
            "Female",
            "Siamese",
            LocalDate.of(2019, 5, 15),
            "image2.jpg",
            client.getId(),
            StatusEnum.ACTIVE));
  }

  public static Person createMockPerson(String ci) {
    return Person.builder().ci(ci).build();
  }

  public static Client createMockClient(Person person) {
    return Client.builder().person(person).build();
  }

  public static ClienteResponseDto createMockClienteResponseDto(Client client) {
    return ClienteResponseDto.builder().build();
  }
}
