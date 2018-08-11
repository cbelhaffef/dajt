package com.cbelhaffef.dajt.repo;

import com.cbelhaffef.dajt.model.importfile.Import;
import com.cbelhaffef.dajt.model.importfile.StatusImport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImportRepo  extends JpaRepository<Import, Long> {

    Optional<Import> findByFileNameAndStatus(String fileName, StatusImport status);

}
