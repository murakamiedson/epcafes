package com.epcafes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epcafes.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
