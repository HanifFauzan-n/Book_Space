package com.library.jafa.dto.officer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReminderEmailDto {
    private String email;
    private String message;
}
