package com.supamenu.www.dtos.comment;

import lombok.*;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateCommentDTO {
    private String comment;
    private UUID postId;
}
