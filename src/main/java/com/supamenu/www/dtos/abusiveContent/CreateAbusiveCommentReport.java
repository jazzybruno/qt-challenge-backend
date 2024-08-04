package com.supamenu.www.dtos.abusiveContent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateAbusiveCommentReport {
    private String title;
    private String description;
    private UUID commentId;
}
