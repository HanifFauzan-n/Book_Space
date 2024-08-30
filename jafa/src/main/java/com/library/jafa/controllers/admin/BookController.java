package com.library.jafa.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.constans.MessageConstant;
import com.library.jafa.dto.GenericResponse;
import com.library.jafa.dto.admin.BookRequestDto;
import com.library.jafa.entities.Book;
import com.library.jafa.services.admin.BookService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/book")
@Tag(name = "Admin-Book")
@Slf4j
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/add-book")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> addBook(@RequestBody BookRequestDto dto) {
        try {
            Book book = bookService.addBook(dto);
            return ResponseEntity.ok().body(GenericResponse.success(book, "Successfully Add new Book"));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/remove-book")
    @SecurityRequirement(name = "Bearer Authentication")
    public String remove(@RequestParam String id) {
        return  bookService.removeBook(id);
    }

    @PutMapping("/update-book")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> updateBook(@RequestParam String id, @RequestBody BookRequestDto dto) {
        try {
            Book book = bookService.updateBook(id,dto);
            return ResponseEntity.ok().body(GenericResponse.success(book, "Successfully update Book"));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/find-all-book")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> findAll(@RequestParam(required = false) String author,
            @RequestParam(required = false) String statusBook, @RequestParam(required = false) String category,
            @RequestParam int page, @RequestParam int size, @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        try {
            return ResponseEntity.ok()
                    .body(GenericResponse.success(
                            bookService.findAll(author, statusBook, category, page, size, sortBy, sortOrder),
                            "Successfully Fetch Data"));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error("Internal Server error"));
        }
    }

    @PutMapping(value = "/upload-book-photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> uploadStudentPhoto(@RequestParam String bookId,@RequestParam("photo") MultipartFile file){
        try {
            bookService.uploadBookPhoto(bookId,file);
            return ResponseEntity.ok().body(GenericResponse.success(null, "successfully upload book photo"));
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