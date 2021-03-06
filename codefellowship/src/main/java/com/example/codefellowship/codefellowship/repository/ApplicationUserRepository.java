package com.example.codefellowship.codefellowship.repository;

import com.example.codefellowship.codefellowship.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {
    ApplicationUser findApplicationUserByUsername(String username);
    ApplicationUser findApplicationUserById(Long id);
}
