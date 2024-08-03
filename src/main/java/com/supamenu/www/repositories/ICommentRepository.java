package com.supamenu.www.repositories;

import com.supamenu.www.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICommentRepository extends JpaRepository<Comment , UUID> {
}
