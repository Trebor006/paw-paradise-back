package com.mibu.pawparadiseback.repository;

import com.mibu.pawparadiseback.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByCi(String ci);
}
