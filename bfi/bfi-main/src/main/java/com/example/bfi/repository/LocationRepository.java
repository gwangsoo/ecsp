package com.example.bfi.repository;

import com.example.bfi.domain.entity.Location;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data MongoDB repository for the Location entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRepository extends MongoRepository<Location, String> {
    @Aggregation("{'last_updated':{$gte:ISODate(?0),$lt:ISODate(?1)}}, {'limit': ?2, 'skip': ?3}")
    List<Location> findAllByLastUpdated(ZonedDateTime from, ZonedDateTime to, Integer offset, Integer limit);
}
