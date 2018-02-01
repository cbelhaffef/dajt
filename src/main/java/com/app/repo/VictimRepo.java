package com.app.repo;

import com.app.model.victim.Victim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VictimRepo extends JpaRepository<Victim, Long> {
    List<Victim> findAll();
    Page<Victim> findAll(Pageable p);
}
