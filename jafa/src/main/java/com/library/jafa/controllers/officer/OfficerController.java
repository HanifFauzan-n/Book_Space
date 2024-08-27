package com.library.jafa.controllers.officer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.constans.MessageConstant;
import com.library.jafa.dto.GenericResponse;
import com.library.jafa.dto.IdentityResponseDto;
import com.library.jafa.dto.RegistrationDto;
import com.library.jafa.services.officer.OfficerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/officer")
@Tag(name = "OFFICER")
@Slf4j
public class OfficerController {

    @Autowired
    OfficerService officerService;

    @PostMapping("/register-officer")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> register(@RequestBody RegistrationDto dto) {
        try {
            IdentityResponseDto regist = officerService.register(dto);
            return ResponseEntity.ok().body(GenericResponse.success(regist, "Successfully register new officer"));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/remove-officer")
    @SecurityRequirement(name = "Bearer Authentication")
    public String remove(@RequestParam String id) {
        return officerService.removeOfficer(id);
    }

    @PutMapping("/update-officer")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> update(@RequestParam String id ,@RequestBody RegistrationDto dto){
        try {
            IdentityResponseDto update = officerService.updateOfficer(id,dto);
            return ResponseEntity.ok().body(GenericResponse.success(update, "Successfully update officer"));
        } 
        catch (ResponseStatusException e){
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        }
        catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error(e.getMessage()));
        }
    }

    @PutMapping(value = "/upload-officer-photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> uploadStudentPhoto(@RequestParam String officerId,@RequestParam("photo") MultipartFile file){
        try {
            officerService.uploadOfficerPhoto(officerId,file);
            return ResponseEntity.ok().body(GenericResponse.success(null, "successfully upload officer photo"));
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