package com.blog.blogbackend.repositories;

import com.blog.blogbackend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
