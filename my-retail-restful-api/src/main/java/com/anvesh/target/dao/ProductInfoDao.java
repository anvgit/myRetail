package com.anvesh.target.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.anvesh.target.entity.ProductInfoEntity;

@Repository
public interface ProductInfoDao extends MongoRepository<ProductInfoEntity, Long> {

}
