package com.buddy.chat.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.buddy.chat.model.DatabaseSequence;
import com.buddy.chat.service.SequenceGeneratorService;

// auto-generate id, just copy and paste
@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {
    private MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorServiceImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
    @Override
    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(
                Query.query(Criteria.where("_id").is(seqName)),
                new Update().inc("seq",1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 0;
    }
    
}
