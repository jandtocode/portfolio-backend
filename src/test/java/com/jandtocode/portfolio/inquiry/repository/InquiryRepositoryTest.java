package com.jandtocode.portfolio.inquiry.repository;

import com.jandtocode.portfolio.inquiry.model.Inquiry;
import com.jandtocode.portfolio.inquiry.model.InquiryOption;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class InquiryRepositoryTest {

    @Autowired
    InquiryRepository repository;

    @Autowired
    MongoTemplate mongoTemplate;

    private final List<String> createdIds = new ArrayList<>();

    @AfterEach
    void cleanUp() {
        repository.deleteAllById(createdIds);
        createdIds.clear();
    }

    @Test
    void savesInquiryWithPhone() {
        Inquiry saved = save(newInquiry("test-with-phone@example.com", "999", "9999999999"));

        assertThat(saved.id()).isNotNull();
        assertThat(repository.existsByEmail("test-with-phone@example.com")).isTrue();
        assertThat(repository.existsByPhoneCodeAndPhone("999", "9999999999")).isTrue();
    }

    @Test
    void savesInquiryWithoutPhoneAndDoesNotStorePhoneFields() {
        Inquiry saved = save(newInquiry("test-no-phone@example.com", null, null));

        Document raw = mongoTemplate.findOne(
                Query.query(Criteria.where("_id").is(new ObjectId(saved.id()))),
                Document.class,
                "inquiries");

        assertThat(raw).isNotNull();
        assertThat(raw.containsKey("phoneCode")).isFalse();
        assertThat(raw.containsKey("phone")).isFalse();
    }

    @Test
    void rejectsDuplicateEmail() {
        save(newInquiry("test-duplicate@example.com", null, null));

        assertThatThrownBy(() -> save(newInquiry("test-duplicate@example.com", null, null)))
                .isInstanceOf(DuplicateKeyException.class);
    }

    private Inquiry save(Inquiry inquiry) {
        Inquiry saved = repository.save(inquiry);
        createdIds.add(saved.id());
        return saved;
    }

    private Inquiry newInquiry(String email, String phoneCode, String phone) {
        return new Inquiry(null, InquiryOption.DEV_WEB, "Test User", email,
                phoneCode, phone, "Test description for repository", Instant.now());
    }
}