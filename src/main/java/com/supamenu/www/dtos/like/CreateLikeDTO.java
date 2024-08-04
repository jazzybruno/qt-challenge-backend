package com.supamenu.www.dtos.like;

import lombok.*;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateLikeDTO {
    private UUID postId;
}
