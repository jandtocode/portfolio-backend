package com.jandtocode.portfolio.inquiry.repository;

import com.jandtocode.portfolio.inquiry.model.Inquiry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InquiryRepository extends MongoRepository<Inquiry, String> {

    boolean existsByEmail(String email);
    boolean existsByPhoneCodeAndPhone(String phoneCode, String phone);
}
