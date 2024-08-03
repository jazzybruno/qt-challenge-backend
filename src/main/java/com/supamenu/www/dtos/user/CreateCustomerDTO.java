package com.supamenu.www.dtos.user;

import com.supamenu.www.dtos.auth.RegisterUserDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CreateCustomerDTO extends RegisterUserDTO {

    private String accountNumber;
    private float accountBalance;

    public CreateCustomerDTO(RegisterUserDTO registerUserDTO , String accountNumber, float accountBalance){
        this.setFirstName(registerUserDTO.getFirstName());
        this.setLastName(registerUserDTO.getLastName());
        this.setEmail(registerUserDTO.getEmail());
        this.setPhoneNumber(registerUserDTO.getPhoneNumber());
        this.setPassword(registerUserDTO.getPassword());
        this.setDateOfBirth(registerUserDTO.getDateOfBirth());
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

}
