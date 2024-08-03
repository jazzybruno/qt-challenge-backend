package com.supamenu.www.controllers;

import com.supamenu.www.dtos.auth.RegisterUserDTO;
import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.dtos.user.CreateAdminDTO;
import com.supamenu.www.dtos.user.UserResponseDTO;
import com.supamenu.www.services.interfaces.AuthenticationService;
import com.supamenu.www.dtos.auth.AuthResponse;
import com.supamenu.www.services.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.supamenu.www.dtos.auth.LoginDTO;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<ApiResponse<UserResponseDTO>> createCustomer(@RequestBody RegisterUserDTO createUserDTO) {
        return this.authenticationService.createUser(createUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginDTO signInDTO) {
        return authenticationService.login(signInDTO);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@Valid @RequestBody CreateAdminDTO createAdminDTO) {
        return userService.createAdmin(createAdminDTO);
    }
}
