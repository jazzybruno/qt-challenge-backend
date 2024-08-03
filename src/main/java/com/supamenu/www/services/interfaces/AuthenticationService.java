package com.supamenu.www.services.interfaces;

import com.supamenu.www.dtos.auth.LoginDTO;
import com.supamenu.www.dtos.auth.RegisterUserDTO;
import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.dtos.auth.AuthResponse;
import com.supamenu.www.dtos.user.UserResponseDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<ApiResponse<AuthResponse>> login(LoginDTO signInDTO);
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(RegisterUserDTO createUserDTO);
}
