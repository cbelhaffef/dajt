package com.cbelhaffef.dajt.dao.repositories;

import com.cbelhaffef.dajt.dao.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepo extends JpaRepository<Status, Long> {
    List<Status> findAll();
    Optional<Status> findByCode(String code);
}
