package com.careydevelopment.crm.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.careydevelopment.crm.model.ActivityType;


@Repository
public interface ActivityTypeRepository extends MongoRepository<ActivityType, String> {

    public List<ActivityType> findAllByOrderByNameAsc();

    public ActivityType findByName(String name);
}
