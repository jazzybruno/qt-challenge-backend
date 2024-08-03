package com.supamenu.www.controllers;


import com.google.protobuf.Api;
import com.supamenu.www.dtos.comment.CreateCommentDTO;
import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.exceptions.CustomException;
import com.supamenu.www.models.Comment;
import com.supamenu.www.services.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Comment>> createComment(CreateCommentDTO createCommentDTO) {
        try {
            return ApiResponse.success(
                    "Successfully Created the comment",
                    HttpStatus.CREATED,
                    commentService.createComment(createCommentDTO)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @PatchMapping("/patch/")
    public ResponseEntity<ApiResponse<Comment>> updateComment(@RequestParam("commentId") UUID commentId, @RequestParam("comment") String content) {
        try {
            return ApiResponse.success(
                    "Successfully updated the comment",
                    HttpStatus.OK,
                    commentService.updateComment(commentId , content)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse<Comment>> deleteComment(@PathVariable UUID commentId) {
        try {
            return ApiResponse.success(
                    "Successfully deleted the comment",
                    HttpStatus.OK,
                    commentService.deleteComment(commentId)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Comment>>> getComments() {
        try {
            return ApiResponse.success(
                    "Successfully Retrived all comments",
                    HttpStatus.OK,
                    commentService.getComments()
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<List<Comment>>> getCommentsByPost(@PathVariable UUID postId) {
        try {
            return ApiResponse.success(
                    "Successfully Retrieved the comments of a post",
                    HttpStatus.CREATED,
                    commentService.getCommentsByPost(postId)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }
}
