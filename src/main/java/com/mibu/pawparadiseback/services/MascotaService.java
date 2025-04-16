package com.mibu.pawparadiseback.services;

import com.mibu.pawparadiseback.services.dto.input.MascotaRequestDto;
import com.mibu.pawparadiseback.services.dto.input.UpdateMascotaRequestDto;
import com.mibu.pawparadiseback.services.dto.output.MascotaResponseDto;
import java.util.List;

public interface MascotaService {

  MascotaResponseDto registrarMascota(MascotaRequestDto mascotaRequestDto);

  List<MascotaResponseDto> obtenerMascotasPorCliente(String ci);

  MascotaResponseDto actualizarMascota(String ci, Long mascotaId, UpdateMascotaRequestDto updateMascotaRequestDto);
}
