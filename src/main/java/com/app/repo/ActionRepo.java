package com.app.repo;

import com.app.model.action.Action;
import com.app.model.office.Office;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepo extends JpaRepository<Action, Long> {

    List<Action> findAll();
    Page<Action> findAll(Pageable p);
}

