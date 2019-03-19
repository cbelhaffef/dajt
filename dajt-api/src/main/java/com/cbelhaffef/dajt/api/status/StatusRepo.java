package com.cbelhaffef.dajt.api.status;

import com.cbelhaffef.dajt.api.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepo extends JpaRepository<Status, Long> {
    List<Status> findAll();
    Optional<Status> findByCode(String code);
}
