package com.project.bm_internsip_grad_project.repositories;


import com.project.bm_internsip_grad_project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String userName);
    User findByEmail(String email);

    Boolean existsByEmail(String email);
}
