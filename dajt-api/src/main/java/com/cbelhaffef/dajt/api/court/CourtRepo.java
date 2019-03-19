package com.cbelhaffef.dajt.api.court;

import com.cbelhaffef.dajt.api.court.Court;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourtRepo extends JpaRepository<Court, Long> {

    List<Court> findAll();
    Page<Court> findAll(Pageable p);
    Optional<Court> findByName(String name);
}
