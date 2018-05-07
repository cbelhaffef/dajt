package com.cbelhaffef.dajt.repo;

import com.cbelhaffef.dajt.model.guilty.Guilty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuiltyRepo extends JpaRepository<Guilty, Long> {
    List<Guilty> findAll();
    Page<Guilty> findAll(Pageable p);
}
