package com.project.matchimban.api.user.repository;

import com.project.matchimban.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByEmail(String email);

    Optional<User> findByEmail(String username);
}
