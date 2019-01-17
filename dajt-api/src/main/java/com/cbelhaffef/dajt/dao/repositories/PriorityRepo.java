package com.cbelhaffef.dajt.dao.repositories;

import com.cbelhaffef.dajt.dao.entities.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriorityRepo extends JpaRepository<Priority, Long> {
    List<Priority> findAll();
    Optional<Priority> findByCode(String code);
}
