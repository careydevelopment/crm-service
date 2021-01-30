package com.careydevelopment.crm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.careydevelopment.crm.model.Deal;


@Repository
public interface DealRepository extends MongoRepository<Deal, String> {

}
