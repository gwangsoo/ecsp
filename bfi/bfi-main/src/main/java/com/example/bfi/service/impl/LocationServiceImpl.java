package com.example.bfi.service.impl;

import com.example.bfi.domain.dto.LocationDTO;
import com.example.bfi.domain.entity.Location;
import com.example.bfi.repository.LocationRepository;
import com.example.bfi.service.LocationService;
import com.example.bfi.service.mapper.LocationMapper;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.example.bfi.domain.entity.Location}.
 */
@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);

    private final LocationRepository locationRepository;

    private final LocationMapper locationMapper;

    @Override
    public LocationDTO save(LocationDTO locationDTO) {
        log.debug("Request to save Location : {}", locationDTO);
        Location location = locationMapper.toEntity(locationDTO);
        location = locationRepository.save(location);
        return locationMapper.toDto(location);
    }

    @Override
    public LocationDTO update(LocationDTO locationDTO) {
        log.debug("Request to update Location : {}", locationDTO);
        Location location = locationMapper.toEntity(locationDTO);
        location = locationRepository.save(location);
        return locationMapper.toDto(location);
    }

    @Override
    public Optional<LocationDTO> partialUpdate(LocationDTO locationDTO) {
        log.debug("Request to partially update Location : {}", locationDTO);

        return locationRepository
            .findById(locationDTO.getId())
            .map(existingLocation -> {
                locationMapper.partialUpdate(existingLocation, locationDTO);

                return existingLocation;
            })
            .map(locationRepository::save)
            .map(locationMapper::toDto);
    }

    @Override
    public List<LocationDTO> findAll(String countryCode, String partyId, ZonedDateTime dateFrom, ZonedDateTime dateTo, Integer offset, Integer limit) {
        log.debug("Request to get all Locations");

//        Criteria where = null;
//
//        if(dateFrom != null) {
//            where = Criteria.where("lastUpdated").gte(dateFrom);
//        }
//
//        if(dateTo != null) {
//            if(where == null) where = Criteria.where("lastUpdated").lte(dateTo);
//            else where = where.and("lastUpdated").lte(dateTo);
//        }
//
//        Query query = new Query(where);
//
//        if(offset != null) {
//            query = query.setFirstResult(offset);
//        }
//        if(limit != null) {
//            query = query.setMaxResults(limit);
//        }

        // TODO 쿼리 작업중
//        Example<Location> example = Example.of(Location.builder().lastUpdated(ZonedDateTime.now()).build());
//
//        Pageable pageable = Pageable.unpaged();
//        return locationRepository.findAll().stream().map(locationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
        return locationRepository.findAllByLastUpdated(countryCode, partyId, dateFrom, dateTo, offset, limit).stream().map(locationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<LocationDTO> findOne(String id) {
        log.debug("Request to get Location : {}", id);
        return locationRepository.findById(id).map(locationMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.deleteById(id);
    }
}
