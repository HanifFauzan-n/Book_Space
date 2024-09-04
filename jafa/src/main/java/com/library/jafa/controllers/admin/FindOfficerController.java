package com.library.jafa.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.dto.GenericResponse;
import com.library.jafa.services.officer.OfficerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/officer")
@Tag(name = "admin-officer")
@Slf4j
public class FindOfficerController {
    @Autowired
    OfficerService officerService;

    @GetMapping("find-all-officer")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> findAll(
            @RequestParam(required = false) String officerName,
            @RequestParam(required = false) String officerAddress,
            @RequestParam(required = false) Integer officerAge,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder

    ) {
        try {
            return ResponseEntity.ok()
                    .body(GenericResponse.success(
                            officerService.findAll(officerName, officerAddress, officerAge, page, size, sortBy, sortOrder),
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
