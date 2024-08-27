package com.library.jafa.services.officer;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;

import com.library.jafa.dto.IdentityResponseDto;
import com.library.jafa.dto.RegistrationDto;

public interface OfficerService {
    IdentityResponseDto register(RegistrationDto dto);

    String removeOfficer(String id);

    IdentityResponseDto updateOfficer(String id, RegistrationDto dto);

    void uploadOfficerPhoto(String id, MultipartFile photo) throws IOException, SQLException;
}
