package com.supamenu.www.repositories;

import com.supamenu.www.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPostRepository extends JpaRepository<Post, UUID> {
}
