package com.cbelhaffef.dajt.api.office;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OfficeRepo extends JpaRepository<Office, Long> {

    Optional<Office> findOneByCode(String code);
    List<Office> findAll();
    Page<Office> findAll(Pageable p);
}

