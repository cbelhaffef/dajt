package com.app.repo;

import com.app.model.office.Office;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficeRepo extends JpaRepository<Office, Long> {

    List<Office> findAll();
    Page<Office> findAll(Pageable p);
}

