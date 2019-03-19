package com.cbelhaffef.dajt.api.accused;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "accused", path = "/accused")
public interface AccusedRepo extends JpaRepository<Accused, Long> {
    List<Accused> findAll();
    Page<Accused> findAll(Pageable p);
}
