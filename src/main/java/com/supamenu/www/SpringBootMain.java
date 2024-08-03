package com.supamenu.www;

import com.supamenu.www.dtos.user.CreateAdminDTO;
import com.supamenu.www.enumerations.user.EUserRole;
import com.supamenu.www.models.Role;
import com.supamenu.www.models.User;
import com.supamenu.www.repositories.IUserRepository;
import com.supamenu.www.services.interfaces.RoleService;
import com.supamenu.www.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@EnableCaching
@RequiredArgsConstructor
@EnableTransactionManagement
public class SpringBootMain {
    private final RoleService roleService;
    private final UserService userService;
    private final IUserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
    }

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.username}")
    private String adminPhone;

    @Value("${admin.firstName}")
    private String adminFirstName;

    @Value("${admin.lastName}")
    private String adminLastName;

    @Bean
    public void seedData() {
        Set<EUserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(EUserRole.ADMIN);
            userRoleSet.add(EUserRole.CUSTOMER);
        for (EUserRole role : userRoleSet) {
            if (!this.roleService.isRolePresent(role)) {
                this.roleService.createRole(role);
            }
        }

        Optional<User> isUserPresent = userRepository.findUserByEmail(adminEmail);
        if (isUserPresent.isEmpty()) {
            CreateAdminDTO createUserDTO = new CreateAdminDTO();
            createUserDTO.setAdminCreateCode("admin");
            createUserDTO.setFirstName(adminFirstName);
            createUserDTO.setLastName(adminLastName);
            createUserDTO.setEmail(adminEmail);
            createUserDTO.setPhoneNumber(adminPhone);
            createUserDTO.setDateOfBirth(new Date(1999, 12, 31));
            createUserDTO.setPassword(adminPassword);
            this.userService.createAdmin(createUserDTO);
        }
    }
}
