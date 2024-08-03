package com.supamenu.www.services.implementations;

import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.dtos.user.*;
import com.supamenu.www.exceptions.*;
import com.supamenu.www.repositories.IRoleRepository;
import com.supamenu.www.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.supamenu.www.enumerations.user.EUserStatus;
import com.supamenu.www.enumerations.user.EUserRole;
import com.supamenu.www.models.Role;
import com.supamenu.www.models.User;
import com.supamenu.www.repositories.IUserRepository;
import com.supamenu.www.utils.HashUtil;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final IUserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final IRoleRepository roleRepository;

    @Value("${admin.create.code}")
    private String adminCreateCode;

    public String generateAccount(){
        String account = "";
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            account += random.nextInt(10);
        }
        if(userRepository.findUserByAccount(account).isPresent()){
            return generateAccount();
        }else {
            return account;
        }
    }

    public boolean isUserPresent(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }


    public User createUserEntity(CreateAdminDTO createAdminDTO) {
        try {
            if(this.isUserPresent(createAdminDTO.getEmail())){
                throw new BadRequestException("User with email already exists");
            }
            if(createAdminDTO.getAdminCreateCode().equals(adminCreateCode)){
                User user = new User();
                user.setFirstName(createAdminDTO.getFirstName());
                user.setLastName(createAdminDTO.getLastName());
                user.setEmail(createAdminDTO.getEmail());
                user.setDateOfBirth(createAdminDTO.getDateOfBirth());
                user.setTelephone(createAdminDTO.getPhoneNumber());
                user.setPassword(HashUtil.hashPassword(createAdminDTO.getPassword()));
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());
                user.setRoles(new HashSet<>(Collections.singletonList(roleService.getRoleByName(EUserRole.ADMIN))));
                userRepository.save(user);
                return user;
            }else{
                throw new BadRequestException("Invalid Admin Creation Code");
            }
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<UserResponseDTO>> createCustomer(CreateCustomerDTO createUserDTO) {
        try {
            if(isUserPresent(createUserDTO.getEmail())){
                return ApiResponse.error("User with email already exists", HttpStatus.BAD_REQUEST, null);
            }
            User user = new User();
            user.setFirstName(createUserDTO.getFirstName());
            user.setLastName(createUserDTO.getLastName());
            user.setEmail(createUserDTO.getEmail());
            user.setDateOfBirth(createUserDTO.getDateOfBirth());
            user.setTelephone(createUserDTO.getPhoneNumber());
            user.setPassword(HashUtil.hashPassword(createUserDTO.getPassword()));
            user.setAccount(generateAccount());
            if(createUserDTO.getAccountBalance() != 0.0F){
                user.setBalance(createUserDTO.getAccountBalance());
            }else{
                user.setBalance(0.0F);
            }
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setRoles(new HashSet<>(Collections.singletonList(roleService.getRoleByName(EUserRole.CUSTOMER))));
            userRepository.save(user);
            return ApiResponse.success("Successfully created user", HttpStatus.CREATED, new UserResponseDTO(user));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<UserResponseDTO>> createAdmin(CreateAdminDTO createAdminDTO) {
        try {
            if(createAdminDTO.getAdminCreateCode().equals(adminCreateCode)){
                User user = new User();
                user.setFirstName(createAdminDTO.getFirstName());
                user.setLastName(createAdminDTO.getLastName());
                user.setEmail(createAdminDTO.getEmail());
                user.setDateOfBirth(createAdminDTO.getDateOfBirth());
                user.setTelephone(createAdminDTO.getPhoneNumber());
                user.setPassword(HashUtil.hashPassword(createAdminDTO.getPassword()));
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());
                user.setRoles(new HashSet<>(Collections.singletonList(roleService.getRoleByName(EUserRole.ADMIN))));
                userRepository.save(user);
                return ApiResponse.success("Successfully created admin", HttpStatus.CREATED, new UserResponseDTO(user));
            }else{
                return ApiResponse.error("Invalid Admin Creation Code", HttpStatus.BAD_REQUEST, null);
            }
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<UsersResponseDTO>> getUsers(Pageable pageable) {
        try {
            Page<User> users = userRepository.findAll(pageable);
            for (User user : users) {
                user.setFullName(user.getFirstName() + " " + user.getLastName());
            }
            return ApiResponse.success("Successfully fetched all users", HttpStatus.OK, new UsersResponseDTO(users));
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(UUID uuid) {
        try {
            User user = findUserById(uuid);
            return ApiResponse.success("Successfully fetched user", HttpStatus.OK, new UserResponseDTO(user));
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    public User findUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("The Resource was not found"));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(UUID userId, UpdateUserDTO updateUserDTO) {
        try {
            User user = findUserById(userId);
            if (user.getEmail() != null) user.setEmail(updateUserDTO.getEmail());
            if (user.getFirstName() != null) user.setFirstName(updateUserDTO.getFirstName());
            if (user.getLastName() != null) user.setLastName(updateUserDTO.getLastName());
            if (user.getTelephone() != null) user.setTelephone(updateUserDTO.getPhoneNumber());
            return ApiResponse.success("Successfully updated the user", HttpStatus.OK, new UserResponseDTO(user));
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public User getLoggedInUser() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userRepository.findUserByEmail(username).orElseThrow(() -> new NotFoundException("User Not Found"));
        user.setFullName(user.getFirstName() + " " + user.getLastName());
        return user;
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<UserResponseDTO>> addRoles(UUID userId, UserRoleModificationDTO userRoleModificationDTO) {
        try {
            User user = findUserById(userId);
            Set<Role> roles = user.getRoles();
            for (UUID roleId : userRoleModificationDTO.getRoles()) {
                Role role = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role not found"));
                roles.add(role);
            }
            user.setRoles(roles);
            userRepository.save(user);
            return ApiResponse.success("Successfully added roles to the user", HttpStatus.OK, new UserResponseDTO(user));
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<UserResponseDTO>> removeRoles(UUID userId, UserRoleModificationDTO userRoleModificationDTO) {
        try {
            User user = findUserById(userId);
            Set<Role> roles = user.getRoles();
            for (UUID roleId : userRoleModificationDTO.getRoles()) {
                Role role = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role not found"));
                roles.remove(role);
            }
            user.setRoles(roles);
            userRepository.save(user);
            return ApiResponse.success("Successfully removed roles from the user", HttpStatus.OK, new UserResponseDTO(user));
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<Object>> deleteUser(UUID userId) {
        try {
            User user = findUserById(userId);
            userRepository.deleteById(userId);
            return ApiResponse.success("Successfully deleted the user", HttpStatus.OK, null);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
