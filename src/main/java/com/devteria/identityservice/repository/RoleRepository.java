package com.devteria.identityservice.repository;


import com.devteria.identityservice.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RolesEntity, Long> {
    RolesEntity findByRoleName(String roleName);
}
