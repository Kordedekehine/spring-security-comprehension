package com.practice.poll.practice.repository;

import com.practice.poll.practice.model.Role;
import com.practice.poll.practice.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(RoleName roleName);
}
