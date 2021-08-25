package com.example.kongservicesecurity.repository;

import com.example.kongservicesecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String user);
    List<User> findAll();
}
