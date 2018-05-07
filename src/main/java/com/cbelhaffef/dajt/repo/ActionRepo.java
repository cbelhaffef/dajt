package com.cbelhaffef.dajt.repo;

import com.cbelhaffef.dajt.model.action.Action;
import com.cbelhaffef.dajt.model.office.Office;
import com.cbelhaffef.dajt.model.action.Action;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepo extends JpaRepository<Action, Long> {

    List<Action> findAll();
    Page<Action> findAll(Pageable p);
}

