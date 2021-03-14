package com.careydevelopment.crm.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.careydevelopment.crm.model.DealStage;


@Repository
public interface DealStageRepository extends MongoRepository<DealStage, String> {

    public List<DealStage> findAllBySalesTypeOrderByIndexAsc(String salesType);
}
