package com.app.repo;

import com.app.model.court.Court;
import com.app.model.guilty.Guilty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourtRepo extends JpaRepository<Court, Long> {
    List<Court> findAll();
    Page<Court> findAll(Pageable p);
}
