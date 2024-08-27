package com.library.jafa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.library.jafa.entities.Roles;

public interface RoleRepository extends JpaRepository<Roles, String> {
    @Query
    Roles findByRoleName(String name);

}
