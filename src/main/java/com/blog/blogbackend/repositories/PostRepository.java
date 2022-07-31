package com.blog.blogbackend.repositories;

import com.blog.blogbackend.entities.Category;
import com.blog.blogbackend.entities.Post;
import com.blog.blogbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);
}
