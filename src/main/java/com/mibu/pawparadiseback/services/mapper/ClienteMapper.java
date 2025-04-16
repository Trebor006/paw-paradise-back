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

  @Mapping(source = "person.id", target = "personId")
  @Mapping(source = "person.ci", target = "personCi")
  @Mapping(source = "person.name", target = "clientName")
  @Mapping(source = "person.email", target = "clientEmail")
  ClienteResponseDto mapToResponseDto(Client entity);

  List<ClienteResponseDto> toDtoListFromClients(List<Client> clients);
}
