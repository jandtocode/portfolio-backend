package com.jandtocode.portfolio.config;

import com.jandtocode.portfolio.inquiry.model.Inquiry;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.PartialIndexFilter;
import org.springframework.data.mongodb.core.query.Criteria;

@Configuration
public class MongoIndexConfig {

    @Bean
    ApplicationRunner createInquiryIndexes(MongoTemplate mongoTemplate) {
        return args -> {
            IndexOperations indexOps = mongoTemplate.indexOps(Inquiry.class);

            indexOps.createIndex(
                    new Index().on("email", Sort.Direction.ASC).unique());

            indexOps.createIndex(
                    new Index()
                            .on("phoneCode", Sort.Direction.ASC)
                            .on("phone", Sort.Direction.ASC)
                            .unique()
                            .partial(PartialIndexFilter.of(Criteria.where("phone").exists(true))));
        };
    }
}