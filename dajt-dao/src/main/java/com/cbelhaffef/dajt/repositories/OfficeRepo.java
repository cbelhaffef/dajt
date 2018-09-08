package com.cbelhaffef.dajt.repositories;

import com.cbelhaffef.dajt.entities.Office;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficeRepo extends JpaRepository<Office, Long> {

    List<Office> findAll();
    Page<Office> findAll(Pageable p);
}

