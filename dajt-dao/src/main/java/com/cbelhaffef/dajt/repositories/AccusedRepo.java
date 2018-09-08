package com.cbelhaffef.dajt.repositories;

import com.cbelhaffef.dajt.entities.Accused;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccusedRepo extends JpaRepository<Accused, Long> {
    List<Accused> findAll();
    Page<Accused> findAll(Pageable p);
}
