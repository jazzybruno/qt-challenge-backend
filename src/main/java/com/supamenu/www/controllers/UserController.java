package com.supamenu.www.controllers;

import com.supamenu.www.dtos.user.*;
import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.services.interfaces.UserService;
import com.supamenu.www.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.supamenu.www.models.User;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserController {
    private final UserService userService;

    @GetMapping("/get-users")
    public ResponseEntity<ApiResponse<List<User>>> getUsers(
    ) {
        return userService.getUsers();
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    @PatchMapping("/update-user/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@PathVariable UUID userId, @RequestBody UpdateUserDTO updateUserDTO) {
        return userService.updateUser(userId, updateUserDTO);
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable UUID userId) {
        return userService.deleteUser(userId);
    }

    @PatchMapping("/add-roles/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> addRoles(@Valid @PathVariable("userId") UUID userId, @Valid @RequestBody UserRoleModificationDTO userRoleModificationDTO) {
        return userService.addRoles(userId, userRoleModificationDTO);
    }

    @PatchMapping("/remove-roles/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> removeRoles(@Valid @PathVariable("userId") UUID userId, @Valid @RequestBody UserRoleModificationDTO userRoleModificationDTO) {
        return userService.removeRoles(userId, userRoleModificationDTO);
    }
}
