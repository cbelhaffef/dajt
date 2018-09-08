package com.cbelhaffef.dajt.repositories;

import com.cbelhaffef.dajt.entities.Import;
import com.cbelhaffef.dajt.enums.StatusImport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImportRepo  extends JpaRepository<Import, Long> {

    Optional<Import> findByFileNameAndStatus(String fileName, StatusImport status);

}
