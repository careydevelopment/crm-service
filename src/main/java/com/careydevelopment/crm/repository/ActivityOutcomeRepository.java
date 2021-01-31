package com.careydevelopment.crm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.careydevelopment.crm.model.ActivityOutcome;


@Repository
public interface ActivityOutcomeRepository extends MongoRepository<ActivityOutcome, String> {

}
