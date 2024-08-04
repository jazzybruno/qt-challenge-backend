package com.supamenu.www.dtos.user;

import com.supamenu.www.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class UsersResponseDTO {
    List<User> users;
}
