package com.cbelhaffef.dajt.api.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findOneByUsername(String username);
    Optional<User> findOneByUsernameAndPassword(String username, String password);
    Optional<User> findOneByEmail(String email);
    Page<User> findAll(Pageable p);

}

