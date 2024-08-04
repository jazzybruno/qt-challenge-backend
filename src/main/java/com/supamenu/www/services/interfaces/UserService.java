package com.supamenu.www.services.interfaces;

import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.dtos.user.*;
import com.supamenu.www.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

    public User findUserById(UUID userId);

    public User getLoggedInUser();

    public ResponseEntity<ApiResponse<UserResponseDTO>> createAdmin(CreateAdminDTO createUserDTO);

    public ResponseEntity<ApiResponse<List<User>>> getUsers();

    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(UUID uuid);

    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(UUID userId, UpdateUserDTO updateUserDTO);

    public ResponseEntity<ApiResponse<UserResponseDTO>> addRoles(UUID userId, UserRoleModificationDTO userRoleModificationDTO);

    public ResponseEntity<ApiResponse<UserResponseDTO>> removeRoles(UUID userId, UserRoleModificationDTO userRoleModificationDTO);

    public ResponseEntity<ApiResponse<Object>> deleteUser(UUID userId);
}
