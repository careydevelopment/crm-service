package com.careydevelopment.crm.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.careydevelopment.crm.model.Activity;


@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {

    public List<Activity> findByContactIdOrderByStartDateDesc(String contactId);
    
    public List<Activity> findAllByOrderByStartDateDesc();
    
    public List<Activity> findByContactSalesOwnerIdOrderByStartDateDesc(String userId);

}
