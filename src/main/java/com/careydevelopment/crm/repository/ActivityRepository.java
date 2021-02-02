package com.careydevelopment.crm.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.careydevelopment.crm.model.Activity;


@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {

    @Query("{'contact.id': ?0}")
    public List<Activity> findByContactId(String contactId);
}
