package com.mibu.pawparadiseback.services.mapper;

import com.mibu.pawparadiseback.domain.Pet;
import com.mibu.pawparadiseback.services.dto.input.MascotaRequestDto;
import com.mibu.pawparadiseback.services.dto.output.MascotaResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MascotaMapper {

  @Mapping(target = "id", ignore = true)
  Pet toEntity(MascotaRequestDto dto);

  MascotaResponseDto toResponseDto(Pet pet);
}
