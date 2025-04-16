package com.mibu.pawparadiseback.repository;

import com.mibu.pawparadiseback.domain.Pet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
  List<Pet> findByClientId(Long clientId);

    Optional<Pet> findByIdAndClientId(Long mascotaId, Long id);
}
