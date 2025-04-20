package com.mibu.pawparadiseback.repository;

import com.mibu.pawparadiseback.domain.Person;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
  Optional<Person> findByCi(String ci);

  Optional<Person> findByEmail(String email);
}
