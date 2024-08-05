package com.supamenu.www.services.implementations;

import com.supamenu.www.dtos.like.CreateLikeDTO;
import com.supamenu.www.exceptions.BadRequestException;
import com.supamenu.www.exceptions.CustomException;
import com.supamenu.www.exceptions.NotFoundException;
import com.supamenu.www.models.Like;
import com.supamenu.www.models.Post;
import com.supamenu.www.models.User;
import com.supamenu.www.repositories.ILikeRepository;
import com.supamenu.www.repositories.IPostRepository;
import com.supamenu.www.services.interfaces.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class LikeServiceImpl implements LikeService {
    private final IPostRepository postRepository;
    private final ILikeRepository likeRepository;
    private final UserServiceImpl userService;

    @Override
    public Like addLike(CreateLikeDTO like) {
        try{
            User user = userService.getLoggedInUser();
            if(user == null){
                throw new AuthenticationException("You are not logged in");
            }
            Post post = postRepository.findById(like.getPostId()).orElseThrow(() -> new NotFoundException("The post was not found"));
            if(!(likeRepository.findAllByAuthorAndPost(user , post).isEmpty())){
                throw new BadRequestException("You have already liked this post");
            }
            post.setNumberOfLikes(post.getNumberOfLikes() + 1);
            Like like1 = new Like();
            like1.setPost(post);
            like1.setAuthor(user);
            postRepository.save(post);
            return likeRepository.save(like1);
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public Like deleteLike(UUID likeId) {
        try{
            User user = userService.getLoggedInUser();
            if(user == null){
                throw new AuthenticationException("You are not logged in");
            }
            Like like = likeRepository.findById(likeId).orElseThrow(() -> new NotFoundException("The like was not found"));
            Post post = like.getPost();
            post.setNumberOfLikes(post.getNumberOfLikes() - 1);
            postRepository.save(post);
            likeRepository.deleteById(likeId);
            return like;
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public List<Like> getLikes() {
        try{
            User user = userService.getLoggedInUser();
            if(user == null){
                throw new AuthenticationException("You are not logged in");
            }
            return likeRepository.findAll();
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public List<Like> getLikesByPost(UUID postId) {
        try{
            User user = userService.getLoggedInUser();
            if(user == null){
                throw new AuthenticationException("You are not logged in");
            }
            return likeRepository.findAllByAuthor(user);
        }catch (Exception e){
            throw new CustomException(e);
        }
    }
}
