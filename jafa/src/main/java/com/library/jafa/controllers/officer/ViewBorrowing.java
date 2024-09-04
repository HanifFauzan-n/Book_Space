package com.library.jafa.controllers.officer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.dto.GenericResponse;
import com.library.jafa.services.officer.RecordLoanService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/member")
@Tag(name = "officer-member")
@Slf4j
public class ViewBorrowing {
    @Autowired
    RecordLoanService recordService;

    @GetMapping("view-all-borrowing")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> findAll(
            @RequestParam(required = false) String member,
            @RequestParam(required = false) String book,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder

    ) {
        try {
            return ResponseEntity.ok()
                    .body(GenericResponse.success(
                            recordService.findAll(member, book,  page, size, sortBy, sortOrder),
                            "Succesfully fetch data"));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error("Internal Server error"));
        }
    }

  
}
