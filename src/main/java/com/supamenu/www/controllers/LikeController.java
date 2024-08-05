package com.supamenu.www.controllers;

import com.supamenu.www.dtos.like.CreateLikeDTO;
import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.exceptions.CustomException;
import com.supamenu.www.models.Like;
import com.supamenu.www.services.interfaces.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {
    private final LikeService likeService;

    @DeleteMapping("/delete/{likeId}")
    public ResponseEntity<ApiResponse<Like>> deleteLike(@PathVariable UUID likeId) {
        try {
            return ApiResponse.success(
                    "Successfully UnLiked a post",
                    HttpStatus.OK,
                    likeService.deleteLike(likeId)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Like>> createLike(@RequestBody CreateLikeDTO like) {
        try {
            return ApiResponse.success(
                    "Successfully Liked a post",
                    HttpStatus.CREATED,
                    likeService.addLike(like)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Like>>> getLikes() {
        try {
            return ApiResponse.success(
                    "Successfully Retrieved a post",
                    HttpStatus.OK,
                    likeService.getLikes()
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<List<Like>>> getLikesByPost(@PathVariable  UUID postId) {
        try {
            return ApiResponse.success(
                    "Successfully Retrived Likes by post",
                    HttpStatus.OK,
                    likeService.getLikesByPost(postId)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }
}
