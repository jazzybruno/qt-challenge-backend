package com.supamenu.www.services.interfaces;

import com.supamenu.www.dtos.post.CreateUpdatePost;
import com.supamenu.www.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

public interface PostService {
    Post createPost( CreateUpdatePost createPost);
    Post updatePost(UUID postId , CreateUpdatePost updatePost);
    Post deletePost(UUID postId);
    List<Post> getAllPosts();
    Page<Post> getAllPosts(Pageable pageable);
    Post getPostById(UUID postId);
    List<Post> getPostByAuthor();
}
