package com.library.jafa.controllers.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.constans.MessageConstant;
import com.library.jafa.dto.GenericResponse;
import com.library.jafa.dto.IdentityResponseDto;
import com.library.jafa.dto.RegistrationDto;
import com.library.jafa.services.member.MemberService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/member")
@Tag(name = "MEMBER")
@Slf4j
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("register-member")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> register(@RequestBody RegistrationDto dto) {
        try {
            IdentityResponseDto regist = memberService.register(dto);
            return ResponseEntity.ok().body(GenericResponse.success(regist, "Successfully register new member"));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("remove-member")
    @SecurityRequirement(name = "Bearer Authentication")
    public String remove(@RequestParam String id) {
        return memberService.removeMember(id);
    }

    @PutMapping("update-member")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> update(@RequestParam String id, @RequestBody RegistrationDto dto) {
        try {
            IdentityResponseDto update = memberService.updateMember(id, dto);
            return ResponseEntity.ok().body(GenericResponse.success(update, "Successfully update officer"));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error(e.getMessage()));
        }
    }
        @PutMapping(value = "/upload-member-photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> uploadStudentPhoto(@RequestParam String memberId,@RequestParam("photo") MultipartFile file){
        try {
            memberService.uploadMemberPhoto(memberId,file);
            return ResponseEntity.ok().body(GenericResponse.success(null, "successfully upload member photo"));
        }
        catch (ResponseStatusException e){
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        }
         catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error(MessageConstant.ERROR_500));
        }
    }

}