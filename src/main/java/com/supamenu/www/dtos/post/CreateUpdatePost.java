package com.supamenu.www.dtos.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateUpdatePost {
    private String title;
    private String content;
}
