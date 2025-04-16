package com.mibu.pawparadiseback.repository;

import com.mibu.pawparadiseback.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {}
