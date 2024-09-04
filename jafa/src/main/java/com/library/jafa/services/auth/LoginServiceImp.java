package com.library.jafa.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.dto.auth.LoginRequestDto;
import com.library.jafa.dto.auth.LoginResponseDto;
import com.library.jafa.entities.Users;
import com.library.jafa.repositories.UserRepository;
import com.library.jafa.util.JwtUtil;


@Service
public class LoginServiceImp implements LoginService  {
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository usersRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        Users user = usersRepository
                .findByUserName(loginRequestDto.getUsername())
                .orElse(null);
        if (user != null) {
            boolean isMatch = passwordEncoder.matches(loginRequestDto.getPassword(),
                    user.getPassword());
            if (isMatch) {
                LoginResponseDto loginResponseDto = new LoginResponseDto();
                loginResponseDto.setUsername(user.getUserName());
                loginResponseDto.setRole(user.getRole().getRoleName());
                loginResponseDto.setToken(jwtUtil.generateToken(user));
                return loginResponseDto;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid username or password");
    }
}
