package com.mibu.pawparadiseback.services.mapper;

import com.mibu.pawparadiseback.domain.Client;
import com.mibu.pawparadiseback.services.dto.input.ClienteRequestDto;
import com.mibu.pawparadiseback.services.dto.output.ClienteResponseDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

  Client toEntity(ClienteRequestDto dto);
  
  @Mapping(source = "person.id", target = "id")
  @Mapping(source = "person.ci", target = "ci")
  @Mapping(source = "person.name", target = "name")
  @Mapping(source = "person.lastname", target = "lastname")
  @Mapping(source = "person.email", target = "email")
  @Mapping(source = "person.phone", target = "phone")
  @Mapping(source = "person.image", target = "image")
  @Mapping(source = "person.gender", target = "gender")
  @Mapping(source = "person.birthdate", target = "birthdate")
  @Mapping(source = "person.type", target = "type")
  @Mapping(source = "person.address", target = "address")
  @Mapping(source = "person.country", target = "country")
  ClienteResponseDto mapToResponseDto(Client entity);

  List<ClienteResponseDto> toDtoListFromClients(List<Client> clients);
}
