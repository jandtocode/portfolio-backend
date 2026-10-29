package com.jandtocode.portfolio.inquiry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record InquiryRequest(

        @NotBlank(message = "Required")
        @Pattern(regexp = "DEV_WEB|AUTOMATION_QA|TWICE", message = "Invalid value")
        String option,

        @NotBlank(message = "Required")
        @Size(min = 2, max = 50, message = "Invalid length: must be between {min} and {max} characters")
        @Pattern(regexp = "^(?=(?:[^\\p{L}]*\\p{L}){2})[\\p{L}\\p{M} '\u2019-]+$", message = "Invalid value")
        String name,

        @NotBlank(message = "Required")
        @Size(min = 5, max = 70, message = "Invalid length: must be between {min} and {max} characters")
        @Pattern(regexp = "^(?!.*\\.\\.)[A-Za-z0-9._-]+@(?![.-])(?=[A-Za-z0-9-]*\\.)[A-Za-z0-9.-]*[A-Za-z0-9]$", message = "Invalid value")
        String email,

        String phoneCode,
        String phone,
        String description
) {
    public InquiryRequest {
        option = trim(option);
        name = trim(name);
        email = trim(email);
        phoneCode = trim(phoneCode);
        phone = trim(phone);
        description = trim(description);
    }

    private static String trim(String value) {
        return value == null ? null : value.strip();
    }
}