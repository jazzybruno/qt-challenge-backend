package com.supamenu.www.repositories;

import com.supamenu.www.models.Post;
import com.supamenu.www.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IPostRepository extends JpaRepository<Post, UUID> {
     List<Post> findAllByAuthor(User user);
}
