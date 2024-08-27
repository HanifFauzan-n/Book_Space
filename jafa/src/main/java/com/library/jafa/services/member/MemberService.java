package com.library.jafa.services.member;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;

import com.library.jafa.dto.IdentityResponseDto;
import com.library.jafa.dto.PageResponse;
import com.library.jafa.dto.RegistrationDto;
import com.library.jafa.entities.Member;

public interface MemberService {
    IdentityResponseDto register(RegistrationDto dto);

    String removeMember(String id);

    IdentityResponseDto updateMember(String id, RegistrationDto dto);

    PageResponse<Member> findAll(String memberName, String addres, Integer memberAge, int page, int size, String sortBy,
            String sortOrder);

    void uploadMemberPhoto(String id, MultipartFile photo) throws IOException, SQLException;

}
