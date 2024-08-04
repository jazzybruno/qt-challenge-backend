package com.supamenu.www.dtos.abusiveContent;

import lombok.*;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateAbusivePostReport {
    private String title;
    private String description;
    private UUID postId;
}
