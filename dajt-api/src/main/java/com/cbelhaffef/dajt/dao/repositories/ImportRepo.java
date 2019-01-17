package com.cbelhaffef.dajt.dao.repositories;

import com.cbelhaffef.dajt.dao.entities.Import;
import com.cbelhaffef.dajt.dao.enums.StatusImport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImportRepo  extends JpaRepository<Import, Long> {

    Optional<Import> findByFileNameAndStatus(String fileName, StatusImport status);

}
