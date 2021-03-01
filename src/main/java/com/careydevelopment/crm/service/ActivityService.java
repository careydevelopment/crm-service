package com.careydevelopment.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.careydevelopment.crm.model.Activity;
import com.careydevelopment.crm.model.ContactSearchCriteria;

@Service
public class ActivityService {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityService.class);
    
    
    @Autowired
    private MongoTemplate mongoTemplate;

    
    public List<Activity> search(ContactSearchCriteria searchCriteria) {
        List<AggregationOperation> ops = new ArrayList<>();
        
        if (searchCriteria.getContactId() != null) {
            AggregationOperation contactMatch = Aggregation.match(Criteria.where("contact._id").is(new ObjectId(searchCriteria.getContactId())));
            ops.add(contactMatch);
        }
        
        if (searchCriteria.getMinDate() != null) {
            AggregationOperation dateThreshold = Aggregation.match(Criteria.where("startDate").gte(searchCriteria.getMinDate()));
            ops.add(dateThreshold);
        }
        
        if (searchCriteria.getOrderBy() != null && searchCriteria.getOrderType() != null) {
            AggregationOperation sort = Aggregation.sort(searchCriteria.getOrderType(), searchCriteria.getOrderBy());
            ops.add(sort);
        }
        
        Aggregation aggregation = Aggregation.newAggregation(ops);
        
        List<Activity> activities = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(Activity.class), Activity.class).getMappedResults();
        
        return activities;
    }
}
