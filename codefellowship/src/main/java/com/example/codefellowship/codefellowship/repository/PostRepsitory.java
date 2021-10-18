package com.example.codefellowship.codefellowship.repository;

import com.example.codefellowship.codefellowship.models.ApplicationUser;
import com.example.codefellowship.codefellowship.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepsitory extends JpaRepository<Post, Long> {
    Post findAllByApplicationUser(ApplicationUser user);

}
