package com.vdt.authservice.repository;

import com.vdt.authservice.entity.Role;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Set<Role> findByIdIn(Set<Long> ids);
}
