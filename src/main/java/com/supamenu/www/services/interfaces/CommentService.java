package com.supamenu.www.services.interfaces;

import com.supamenu.www.dtos.comment.CreateCommentDTO;
import com.supamenu.www.dtos.like.CreateLikeDTO;
import com.supamenu.www.models.Comment;
import com.supamenu.www.models.Like;
import com.supamenu.www.models.Post;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    Comment createComment(CreateCommentDTO createCommentDTO);
    Comment updateComment(UUID commentId , String content);
    Comment deleteComment(UUID commentId);
    List<Comment> getComments();
    List<Comment> getCommentsByPost(UUID postId);
 }
