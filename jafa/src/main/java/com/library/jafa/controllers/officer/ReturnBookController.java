package com.library.jafa.controllers.officer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.library.jafa.dto.GenericResponse;
import com.library.jafa.dto.officer.ReturnBookReqDto;
import com.library.jafa.dto.officer.ReturnBookResponseDto;
import com.library.jafa.services.officer.ReturnBookService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/officer")
@Slf4j
public class ReturnBookController {

    @Autowired
    ReturnBookService returnBookService;

    @PostMapping("/return-book")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> returnBook(@RequestBody ReturnBookReqDto dto) {
        try {
            ReturnBookResponseDto responseDto = returnBookService.returnBook(dto);
            return ResponseEntity.ok().body(GenericResponse.success(responseDto, "Berhasil mengembalikan buku"));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(GenericResponse.error(e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error("Terjadi kesalahan saat memproses permintaan"));
        }
    }
}
