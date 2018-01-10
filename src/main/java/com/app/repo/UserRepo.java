package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.model.user.User;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findOneByUsername(String username);
    Optional<User> findOneByUsernameAndPassword(String userId, String password);
}

