package com.library.jafa.dto;

import com.library.jafa.entities.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdentityResponseDto {
    private String name;
    private Integer age;
    private String address;
    private String gender;
    private String email;
    private String roleName;
    private String roleDesc;

    public IdentityResponseDto response(RegistrationDto dto, Users users) {
        IdentityResponseDto regist = new IdentityResponseDto();
        regist.setName(dto.getName());
        regist.setAge(dto.getAge());
        regist.setGender(dto.getGender());
        regist.setAddress(dto.getAddress());
        regist.setEmail(dto.getEmail());
        regist.setRoleName(users.getRole().getRoleName());
        regist.setRoleDesc(users.getRole().getRoleDesc());
        return regist;
    }

}
