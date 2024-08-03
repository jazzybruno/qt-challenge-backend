package com.supamenu.www.controllers;

import com.supamenu.www.dtos.post.CreateUpdatePost;
import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.exceptions.CustomException;
import com.supamenu.www.models.Post;
import com.supamenu.www.services.interfaces.PostService;
import com.supamenu.www.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Post>> createPost( @RequestParam("image") MultipartFile multipartFile, @ModelAttribute CreateUpdatePost createPost) {
        try {
           return ApiResponse.success(
                   "Sucessfully created a post",
                   HttpStatus.CREATED,
                   postService.createPost(multipartFile, createPost)
           );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<ApiResponse<Post>> updatePost(@PathVariable UUID postId,  @RequestParam("image") MultipartFile multipartFile, @ModelAttribute CreateUpdatePost updatePost) {
        try {
            return ApiResponse.success(
                    "Post updated successfully",
                    HttpStatus.OK,
                    postService.updatePost(postId, multipartFile, updatePost)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse<Post>> deletePost(@PathVariable UUID postId) {
        try {
            return ApiResponse.success(
                    "Successfully Deleted the post",
                    HttpStatus.OK,
                    postService.deletePost(postId)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Post>>> getAllPosts() {
        try {
            return ApiResponse.success(
                    "successfully retrieved all posts",
                    HttpStatus.OK,
                    postService.getAllPosts()
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @GetMapping("/all/paginated")
    public ResponseEntity<ApiResponse<Page<Post>>> getAllPosts(         @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
                                                                        @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit
    ) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        try {
            return ApiResponse.success(
                    "Successfully retrieved paginated posts",
                    HttpStatus.OK,
                    postService.getAllPosts(pageable)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @GetMapping("/id/{postId}")
    public ResponseEntity<ApiResponse<Post>> getPostById(@PathVariable UUID postId) {
        try {
            return ApiResponse.success(
                    "Successfully Retrieved post",
                    HttpStatus.OK,
                    postService.getPostById(postId)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<Post>>> getPostByAuthor() {
        try {
            return ApiResponse.success(
                    "Successfully Retrieved posts by user",
                    HttpStatus.OK,
                    postService.getPostByAuthor()
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }
}
