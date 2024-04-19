package com.example.bfi.repository;

import com.example.bfi.domain.entity.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Location entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRepository extends MongoRepository<Location, String> {}
