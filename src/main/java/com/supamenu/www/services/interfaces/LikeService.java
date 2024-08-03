package com.supamenu.www.services.interfaces;

import com.supamenu.www.dtos.like.CreateLikeDTO;
import com.supamenu.www.models.Like;

import java.util.List;
import java.util.UUID;

public interface LikeService {
    Like addLike(CreateLikeDTO like);
    Like deleteLike(UUID likeId);
    List<Like> getLikes();
    List<Like> getLikesByPost(UUID postId);
}
