package com.supamenu.www.dtos.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateCommentDTO {
    private String comment;
    private UUID postId;
}
