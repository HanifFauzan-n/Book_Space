package com.library.jafa.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.dto.GenericResponse;
import com.library.jafa.dto.admin.BookshelfRequestDto;
import com.library.jafa.entities.Bookshelf;
import com.library.jafa.services.admin.BookshelfService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/book")
@Tag(name = "Admin-Bookshelf")
@Slf4j
public class BookshelfController {

    @Autowired
    BookshelfService bookshelfService;

    @PostMapping("/add-bookshelf")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> register(@RequestBody BookshelfRequestDto dto) {
        try {
            Bookshelf bookshelf = bookshelfService.addBookshelf(dto);
            return ResponseEntity.ok().body(GenericResponse.success(bookshelf, "Successfully Add new Bookshelf"));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/remove-bookshelf")
    @SecurityRequirement(name = "Bearer Authentication")
    public String remove(@RequestParam String id) {
        return bookshelfService.removeBookshelf(id);
    }

    @PutMapping("/update-bookshelf")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> updateBook(@RequestParam String id, @RequestBody BookshelfRequestDto dto) {
        try {
            Bookshelf bookshelf = bookshelfService.updateBookshelf(id, dto);
            return ResponseEntity.ok().body(GenericResponse.success(bookshelf, "Successfully update Book"));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/find-bookshelf")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> findAll(@RequestParam(required = false) String category,@RequestParam(required = false) Integer capacity,
            @RequestParam int page, @RequestParam int size,@RequestParam(required = false) String sortBy,@RequestParam(required = false) String sortOrder) {
        try {
            return ResponseEntity.ok().body(GenericResponse
                    .success(bookshelfService.findAll(category, capacity, page, size,sortBy,sortOrder), "Successfully Fetch Data"));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error("Internal Server error"));
        }
    }

}