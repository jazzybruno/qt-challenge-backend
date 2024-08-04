package com.supamenu.www.services.implementations;

import com.supamenu.www.dtos.comment.CreateCommentDTO;
import com.supamenu.www.enumerations.user.EUserRole;
import com.supamenu.www.exceptions.CustomException;
import com.supamenu.www.exceptions.NotFoundException;
import com.supamenu.www.models.*;
import com.supamenu.www.repositories.ICommentRepository;
import com.supamenu.www.repositories.IPostRepository;
import com.supamenu.www.services.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final IPostRepository postRepository;
    private final ICommentRepository commentRepository;
    private final UserServiceImpl userService;


    @Override
    public Comment createComment(CreateCommentDTO createCommentDTO) {
        try{
            User user = userService.getLoggedInUser();
            if(user == null){
                throw new AuthenticationException("You are not logged in");
            }
            Post post = postRepository.findById(createCommentDTO.getPostId()).orElseThrow(() -> new NotFoundException("The post was not found"));
            Comment comment = new Comment();
            comment.setPost(post);
            comment.setAuthor(user);
            post.setNumberOfComments(post.getNumberOfComments() + 1);
            comment.setComment(createCommentDTO.getComment());
            postRepository.save(
                    post
            );
            return commentRepository.save(comment);
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public Comment updateComment(UUID commentId, String content) {
        try{
            User user = userService.getLoggedInUser();
            if(user == null){
                throw new AuthenticationException("You are not logged in");
            }
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("The comment was not found"));
            if(comment.getAuthor().getId() != user.getId()){
                throw new AuthenticationException("You are not allowed to update this comment");
            }
            comment.setComment(content);
            return commentRepository.save(comment);
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public Comment deleteComment(UUID commentId) {
        try{
            User user = userService.getLoggedInUser();
            if(user == null){
                throw new AuthenticationException("You are not logged in");
            }
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("The comment was not found"));
            commentRepository.deleteById(commentId);
            return comment;
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public List<Comment> getComments() {
        try{
            User user = userService.getLoggedInUser();
            if(user == null){
                throw new AuthenticationException("You are not logged in");
            }
            return commentRepository.findAll();
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public List<Comment> getCommentsByPost(UUID postId) {
        try{
            Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("The post was not found"));
            return commentRepository.findAllByPost(post);
        }catch (Exception e){
            throw new CustomException(e);
        }
    }
}
