package com.mibu.pawparadiseback.services;

import com.mibu.pawparadiseback.services.dto.input.MascotaRequestDto;
import com.mibu.pawparadiseback.services.dto.output.MascotaResponseDto;

public interface MascotaService {

  MascotaResponseDto registrarMascota(MascotaRequestDto mascotaRequestDto);
}
