package com.careydevelopment.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.careydevelopment.crm.model.ActivitySearchCriteria;

@Service
public class ActivityService {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityService.class);
    
    
    @Autowired
    private MongoTemplate mongoTemplate;

    
    public List<Activity> search(ActivitySearchCriteria searchCriteria) {
        List<AggregationOperation> ops = new ArrayList<>();
        
        if (!StringUtils.isBlank(searchCriteria.getContactId())) {
            AggregationOperation contactMatch = Aggregation.match(Criteria.where("contact._id").is(new ObjectId(searchCriteria.getContactId())));
            ops.add(contactMatch);
        }
        
        if (searchCriteria.getMinDate() != null) {
            AggregationOperation dateThreshold = Aggregation.match(Criteria.where("startDate").gte(searchCriteria.getMinDate()));
            ops.add(dateThreshold);
        }
        
        if (!StringUtils.isBlank(searchCriteria.getDealId())) {
            AggregationOperation deal = Aggregation.match(Criteria.where("deal._id").is(new ObjectId(searchCriteria.getDealId())));
            ops.add(deal);
        }
        
        if (!StringUtils.isBlank(searchCriteria.getSalesOwnerId())) {
            AggregationOperation owner = Aggregation.match(Criteria.where("contact.salesOwner._id").is(new ObjectId(searchCriteria.getSalesOwnerId())));
            ops.add(owner);
        }
        
        if (!StringUtils.isBlank(searchCriteria.getStatus())) {
            Criteria usesStatusNull = Criteria.where("type.usesStatus").is(null);
            Criteria usesStatusFalse = Criteria.where("type.usesStatus").is(false);
            Criteria statusComplete = Criteria.where("status").is(searchCriteria.getStatus());
            
            AggregationOperation status = Aggregation.match(new Criteria().orOperator(usesStatusNull, usesStatusFalse, statusComplete));
            ops.add(status);
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
