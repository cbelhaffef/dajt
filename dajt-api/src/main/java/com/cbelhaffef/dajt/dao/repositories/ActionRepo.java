package com.cbelhaffef.dajt.dao.repositories;

import com.cbelhaffef.dajt.dao.entities.Action;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActionRepo extends JpaRepository<Action, Long> {

    List<Action> findAll();
    Page<Action> findAll(Pageable p);
    Optional<Action> findByCode(String actionCode);
}

