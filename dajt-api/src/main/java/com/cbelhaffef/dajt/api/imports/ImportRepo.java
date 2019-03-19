package com.cbelhaffef.dajt.api.imports;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImportRepo  extends JpaRepository<Import, Long> {

    Optional<Import> findByFileNameAndStatus(String fileName, StatusImport status);

}