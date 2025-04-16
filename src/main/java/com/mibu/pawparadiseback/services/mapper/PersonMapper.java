package com.mibu.pawparadiseback.services.mapper;

import com.mibu.pawparadiseback.domain.Person;
import com.mibu.pawparadiseback.services.dto.input.ClienteRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

  Person toEntity(ClienteRequestDto clienteRequestDto);
}

