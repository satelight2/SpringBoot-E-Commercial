package com.devteria.identityservice.repository;


import com.devteria.identityservice.entity.PermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionsEntity, String> {
PermissionsEntity findByName(String name);
}
