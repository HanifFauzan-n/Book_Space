package com.library.jafa.controllers.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.jafa.dto.GenericResponse;
import com.library.jafa.services.auth.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin("*")
@RestController
@RequestMapping("/forgot-password")
@Tag(name = "Forgot-Password")
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @PostMapping("/get-token")
    public ResponseEntity<Object> forgotPassword(@RequestParam String email) {
        try {
            userService.forgotPassword(email);
            return ResponseEntity.ok().body(GenericResponse.success(true, "Password reset instructions sent successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send reset instructions: " + e.getMessage());
        }
    }


    @PostMapping("/reset")
    public ResponseEntity<Object> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            userService.updatePassword(token, newPassword);
            return ResponseEntity.ok().body(GenericResponse.success(true, "Password reset successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to reset password: " + e.getMessage());
        }
    }
}
