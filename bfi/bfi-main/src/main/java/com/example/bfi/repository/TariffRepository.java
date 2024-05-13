package com.example.bfi.repository;

import com.example.bfi.domain.entity.Tariff;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data MongoDB repository for the Tariff entity.
 *
 */
@SuppressWarnings("unused")
@Repository
public interface TariffRepository extends MongoRepository<Tariff, String>{
    /**
     *
     */
    @Aggregation({"{'$match': { '$and':  [ {'country_code' : ?#{[0]} }, {'partyId': ?#{[1]}}, {'last_updated': {'$gte': ?#{[2]} , '$lt': ?#{[3]}} }] }}"
                            ,"{'$skip': ?#{[4]}}", "{'$limit': ?#{[5]}}"})
    List<Tariff> findAllByLastUpdated(String countryCode, String partyId, ZonedDateTime from, ZonedDateTime to, Integer offset, Integer limit);
}
