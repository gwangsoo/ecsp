package com.example.bfi.repository;

import com.example.bfi.domain.entity.Location;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data MongoDB repository for the Location entity.
 * "{ 'locale' :  ?0 }")
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRepository extends MongoRepository<Location, String> {
    /**
     * @param countryCode
     * @param partyId
     * @param from
     * @param to
     * @param offset
     * @param limit
     * @return
     * - ref MongoDB url : https://www.mongodb.com/docs/manual/reference/operator/aggregation/
     * - ref MongoRepository url : https://docs.spring.io/spring-data/mongodb/reference/mongodb/repositories/query-methods.html
     */
    @Aggregation(pipeline = {"{'$match': { '$and':  [ {'country_code' : ?#{[0]} }, {'partyId': ?#{[1]}}, {'last_updated': {'$gte': ?#{[2]} , '$lt': ?#{[3]}} }] }}"
                            ,"{'$skip': ?#{[4]}}", "{'$limit': ?#{[5]}}"})
    List<Location> findAllByLastUpdated(String countryCode, String partyId, ZonedDateTime from, ZonedDateTime to, Integer offset, Integer limit);
}
