package com.E_CommerceApplication.App.repositories;

import com.E_CommerceApplication.App.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
}
