package com.supamenu.www.repositories;

import com.supamenu.www.models.Comment;
import com.supamenu.www.models.Post;
import com.supamenu.www.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ICommentRepository extends JpaRepository<Comment , UUID> {
    List<Comment> findAllByAuthor(User author);
    List<Comment> findAllByPost(Post post);
}
