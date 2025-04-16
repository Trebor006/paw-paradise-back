package com.mibu.pawparadiseback.repository;

import com.mibu.pawparadiseback.domain.Client;
import com.mibu.pawparadiseback.domain.Person;
import com.mibu.pawparadiseback.domain.enums.StatusEnum;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Client, Integer> {
  Optional<Client> findByPerson(Person person);

  List<Client> findAllByStatus(StatusEnum status);
}
