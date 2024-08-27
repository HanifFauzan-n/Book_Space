package com.library.jafa.controllers.officer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.jafa.constans.MessageConstant;
import com.library.jafa.dto.GenericResponse;
import com.library.jafa.services.officer.LoanFileService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/officer")
@Tag(name = "Lending Report")
@Slf4j
public class LoanFileController {
    @Autowired
    LoanFileService laporanPeminjamanService;

    @GetMapping("/print_report")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> report(HttpServletResponse response){
        try{
            response.setHeader("Content-Disposition", "Attachment; filename=report.pdf");

            return ResponseEntity.ok(laporanPeminjamanService.generateReport());
        }catch(Exception e){
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error(MessageConstant.ERROR_500));
        }
    }
}
