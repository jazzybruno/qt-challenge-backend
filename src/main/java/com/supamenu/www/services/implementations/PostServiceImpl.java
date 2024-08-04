package com.supamenu.www.services.implementations;

import com.supamenu.www.dtos.post.CreateUpdatePost;
import com.supamenu.www.exceptions.CustomException;
import com.supamenu.www.exceptions.NotFoundException;
import com.supamenu.www.models.Post;
import com.supamenu.www.models.User;
import com.supamenu.www.repositories.IPostRepository;
import com.supamenu.www.repositories.IUserRepository;
import com.supamenu.www.services.interfaces.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class PostServiceImpl implements PostService {
    private final IPostRepository postRepository;
    private final IUserRepository userRepository;
    private final UserServiceImpl userService;
    private final FileServiceImpl fileService;


    @Override
    public Post createPost( CreateUpdatePost createPost) {
        try {
            User user = userService.getLoggedInUser();
            if(user == null) {
                throw new AuthenticationException(
                        "You are not logged in"
                );
            }
            Post post = new Post();
            post.setTitle(createPost.getTitle());
            post.setContent(createPost.getContent());
            post.setAuthor(user);
            return postRepository.save(post);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(e);
        }
    }

    @Override
    public Post updatePost(UUID postId, CreateUpdatePost updatePost) {
        try {
            // get post by id
            Post post = postRepository.findById(postId).orElseThrow(()-> new NotFoundException("The post does not exist"));
            post.setTitle(updatePost.getTitle());
            post.setContent(updatePost.getContent());
            post.setUpdatedAt(LocalDateTime.now());
            return postRepository.save(post);
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public Post deletePost(@PathVariable UUID postId) {
        try {
            Post post = postRepository.findById(postId).orElseThrow(()-> new NotFoundException("The post does not exist"));
                postRepository.deleteById(postId);
            return post;
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public List<Post> getAllPosts() {
        try {
            return postRepository.findAll();
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        try {
            return postRepository.findAll(pageable);
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public Post getPostById(UUID postId) {
        try {
            return postRepository.findById(postId).orElseThrow(()-> new NotFoundException("The post does not exist"));
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public List<Post> getPostByAuthor() {
        try {
          User user = userService.getLoggedInUser();
          if(user == null) {
              throw new AuthenticationException("You are not logged in");
          }
          return postRepository.findAllByAuthor(user);
        }catch (Exception e){
            throw new CustomException(e);
        }
    }
}
