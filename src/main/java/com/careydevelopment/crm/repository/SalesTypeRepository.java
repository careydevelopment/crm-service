package com.careydevelopment.crm.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.careydevelopment.crm.model.SalesType;


@Repository
public interface SalesTypeRepository extends MongoRepository<SalesType, String> {

    public List<SalesType> findAllByOrderByNameAsc();
}
