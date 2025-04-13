package com.mibu.pawparadiseback.repository;

import com.mibu.pawparadiseback.domain.Client;
import java.util.Optional;

import com.mibu.pawparadiseback.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Client, Integer> {
  Optional<Client> findByPerson(Person person);
}
