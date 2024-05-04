package com.devteria.identityservice.repository;


import com.devteria.identityservice.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {
    boolean existsByUsername(String username);
    Optional<UsersEntity> findByUsername(String username);

    // Add a new method to UserRepository that returns a list of users by full name
    List<UsersEntity> findByFullnameContains(String fullname);
}
