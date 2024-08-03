package com.supamenu.www.dtos.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class CreateUpdatePost {
    @Schema(example = "this is my title")
    @NotBlank(message = "The title is required")
    private String title;
    @Schema(example = "this is my content of the post and it is there")
    @NotBlank(message = "The content is required")
    private String content;
}
