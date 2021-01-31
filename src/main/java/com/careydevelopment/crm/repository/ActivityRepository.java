package com.careydevelopment.crm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.careydevelopment.crm.model.Activity;


@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {

}
