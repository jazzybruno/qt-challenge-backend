package com.supamenu.www.repositories;

import com.supamenu.www.models.Comment;
import com.supamenu.www.models.Like;
import com.supamenu.www.models.Post;
import com.supamenu.www.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ILikeRepository extends JpaRepository<Like, UUID> {
    List<Like> findAllByAuthor(User author);
    List<Like> findAllByAuthorAndPost(User author, Post post);
}
