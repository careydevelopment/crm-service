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

import com.careydevelopment.crm.model.Deal;
import com.careydevelopment.crm.model.DealSearchCriteria;

@Service
public class DealService {

    private static final Logger LOG = LoggerFactory.getLogger(DealService.class);
    
    
    @Autowired
    private MongoTemplate mongoTemplate;

    
    public List<Deal> search(DealSearchCriteria searchCriteria) {
        List<AggregationOperation> ops = new ArrayList<>();
        
        if (!StringUtils.isBlank(searchCriteria.getContactId())) {
            AggregationOperation contactMatch = Aggregation.match(Criteria.where("contact._id").is(new ObjectId(searchCriteria.getContactId())));
            ops.add(contactMatch);
        }
        
        if (!StringUtils.isBlank(searchCriteria.getSalesOwnerId())) {
            AggregationOperation salesOwnerMatch = Aggregation.match(Criteria.where("contact.salesOwner._id").is(new ObjectId(searchCriteria.getSalesOwnerId())));
            ops.add(salesOwnerMatch);            
        }

        if (searchCriteria.getMinDate() != null) {
            AggregationOperation minDate = Aggregation.match(Criteria.where("expectedClosureDate").gte(searchCriteria.getMinDate()));
            ops.add(minDate);
        }
        
        if (searchCriteria.getMaxDate() != null) {
            AggregationOperation maxDate = Aggregation.match(Criteria.where("expectedClosureDate").lte(searchCriteria.getMaxDate()));
            ops.add(maxDate);
        }
        
        if (searchCriteria.getMaxResults() != null) {
            AggregationOperation limit = Aggregation.limit(searchCriteria.getMaxResults());
            ops.add(limit);
        }
        
        if (!StringUtils.isBlank(searchCriteria.getOrderBy()) && searchCriteria.getOrderType() != null) {
            AggregationOperation sort = Aggregation.sort(searchCriteria.getOrderType(), searchCriteria.getOrderBy());
            ops.add(sort);
        }
                
        Aggregation aggregation = Aggregation.newAggregation(ops);
        
        List<Deal> deals = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(Deal.class), Deal.class).getMappedResults();
        
        return deals;
    }
}
