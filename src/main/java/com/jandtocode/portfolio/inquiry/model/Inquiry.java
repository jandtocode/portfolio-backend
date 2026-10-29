package com.jandtocode.portfolio.inquiry.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "inquiries")
public record Inquiry(
        @Id
        String id,
        InquiryOption option,
        String name,
        String email,
        String phoneCode,
        String phone,
        String description,
        Instant createdAt
) {

    public Inquiry withId(String id) {
        return new Inquiry(id, option, name, email, phoneCode, phone, description, createdAt);
    }
}
