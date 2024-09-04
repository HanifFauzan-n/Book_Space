package com.library.jafa.controllers.officer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.dto.GenericResponse;
import com.library.jafa.dto.officer.RecordLoanReqDto;
import com.library.jafa.dto.officer.RecordLoanResponseDto;
import com.library.jafa.services.officer.RecordLoanService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/officer")
@Tag(name = "RECORD-BOOK")
@Slf4j
public class RecordLoanController {

    @Autowired
    RecordLoanService recordLoanService;

    @PostMapping("record-book")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> register(@RequestBody RecordLoanReqDto dto){
        try {
            RecordLoanResponseDto records = recordLoanService.recordLoans(dto);
            return ResponseEntity.ok().body(GenericResponse.success(records, "Successfully recorded the loan book"));
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
}