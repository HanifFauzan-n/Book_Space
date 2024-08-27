package com.library.jafa.services.auth;

import com.library.jafa.dto.auth.LoginRequestDto;
import com.library.jafa.dto.auth.LoginResponseDto;

public interface LoginService {
        LoginResponseDto login(LoginRequestDto loginRequestDto);

}
