package com.supamenu.www.services.implementations;

import com.supamenu.www.dtos.auth.LoginDTO;
import com.supamenu.www.dtos.auth.RegisterUserDTO;
import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.dtos.user.UserResponseDTO;
import com.supamenu.www.enumerations.user.EUserRole;
import com.supamenu.www.exceptions.CustomException;
import com.supamenu.www.models.User;
import com.supamenu.www.repositories.IUserRepository;
import com.supamenu.www.security.JwtTokenProvider;
import com.supamenu.www.security.UserPrincipal;
import com.supamenu.www.services.interfaces.AuthenticationService;
import com.supamenu.www.services.interfaces.RoleService;
import com.supamenu.www.services.interfaces.UserService;
import com.supamenu.www.dtos.auth.AuthResponse;
import com.supamenu.www.utils.HashUtil;
import com.supamenu.www.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;
    private final IUserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationProvider authenticationProvider;


    public boolean isUserPresent(String email , String username) {
        return userRepository.findUserByUsername(username).isPresent() || userRepository.findUserByEmail(email).isPresent();
    }


    @Override
    @Transactional
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(RegisterUserDTO createUserDTO) {
        try {
            if(isUserPresent(createUserDTO.getEmail() , createUserDTO.getUserName())){
                return ApiResponse.error("User with email already exists", HttpStatus.BAD_REQUEST, null);
            }
            User user = new User();
            user.setFirstName(createUserDTO.getFirstName());
            user.setLastName(createUserDTO.getLastName());
            user.setEmail(createUserDTO.getEmail());
            user.setFullName(createUserDTO.getFirstName() + " " + createUserDTO.getLastName());
            user.setUsername(createUserDTO.getUserName());
            user.setDateOfBirth(createUserDTO.getDateOfBirth());
            user.setTelephone(createUserDTO.getPhoneNumber());
            user.setPassword(HashUtil.hashPassword(createUserDTO.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setRoles(new HashSet<>(Collections.singletonList(roleService.getRoleByName(EUserRole.USER))));
            userRepository.save(user);
            return ApiResponse.success("Successfully created user", HttpStatus.CREATED, new UserResponseDTO(user));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<AuthResponse>> login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticateUser(loginDTO);
            AuthResponse response = generateJwtAuthenticationResponse(authentication);
            return ApiResponse.success("Successfully logged in", HttpStatus.OK, response);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    private Authentication authenticateUser(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationProvider.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private AuthResponse generateJwtAuthenticationResponse(Authentication authentication) {
        String jwt = jwtTokenProvider.generateAccessToken(authentication);
        UserPrincipal userPrincipal = UserUtils.getLoggedInUser();
        assert userPrincipal != null;
        User user = userService.findUserById(userPrincipal.getId());
        user.setFullName(user.getFirstName() + " " + user.getLastName());
        return new AuthResponse(jwt, user);
    }
}
