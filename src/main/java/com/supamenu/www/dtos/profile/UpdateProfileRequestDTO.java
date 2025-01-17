package com.supamenu.www.dtos.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateProfileRequestDTO {
    @Schema(example = "John")
    private String firstName;

    @Schema(example = "John")
    private String userName;

    @Schema(example = "Doe")
    private String lastName;

    @Schema(example = "0784042344")
    private String phone;

    @Schema(example = "example@gmail.com")
    @Email(message = "Invalid email format")
    private String email;
}
