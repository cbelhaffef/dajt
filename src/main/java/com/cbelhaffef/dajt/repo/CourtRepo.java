package com.cbelhaffef.dajt.repo;

import com.cbelhaffef.dajt.model.court.Court;
import com.cbelhaffef.dajt.model.guilty.Guilty;
import com.cbelhaffef.dajt.model.court.Court;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourtRepo extends JpaRepository<Court, Long> {
    List<Court> findAll();
    Page<Court> findAll(Pageable p);
}
