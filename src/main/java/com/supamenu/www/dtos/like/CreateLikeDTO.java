package com.supamenu.www.dtos.like;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateLikeDTO {
    private UUID postId;
}
