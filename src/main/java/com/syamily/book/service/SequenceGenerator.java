package com.syamily.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.syamily.book.entity.Sequence;

@Component
public class SequenceGenerator {

    @Autowired
    private MongoOperations mongoOperations;

    public Long getNextSequence(String sequenceName) {
        Query query = new Query(Criteria.where("_id").is(sequenceName));
        Update update = new Update().inc("seq", 1);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
        Sequence sequence = mongoOperations.findAndModify(query, update, options, Sequence.class);
        return sequence != null ? sequence.getSeq() : 1L;
    }
}

