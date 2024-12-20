package com.ecom.shoppy.repository;

import com.ecom.shoppy.enums.AppRole;
import com.ecom.shoppy.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}