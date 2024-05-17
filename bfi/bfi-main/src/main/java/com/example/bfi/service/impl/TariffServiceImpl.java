package com.example.bfi.service.impl;

import com.example.bfi.domain.dto.TariffDTO;
import com.example.bfi.domain.entity.Tariff;
import com.example.bfi.repository.TariffRepository;
import com.example.bfi.service.TariffService;
import com.example.bfi.service.mapper.TariffMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Tariff}.
 */
@RequiredArgsConstructor
@Service
public class TariffServiceImpl implements TariffService {

    private final Logger log = LoggerFactory.getLogger(TariffServiceImpl.class);

    private final TariffRepository tariffRepository;

    private final TariffMapper tariffMapper;

    @Override
    public TariffDTO save(TariffDTO tariffDTO) {
        log.debug("Request to save Tariff : {}", tariffDTO);
        Tariff tariff = tariffMapper.toEntity(tariffDTO);
        tariff = tariffRepository.save(tariff);
        return tariffMapper.toDto(tariff);
    }

    @Override
    public TariffDTO update(TariffDTO tariffDTO) {
        log.debug("Request to update Tariff : {}", tariffDTO);
        Tariff tariff = tariffMapper.toEntity(tariffDTO);
        tariff = tariffRepository.save(tariff);
        return tariffMapper.toDto(tariff);
    }

    @Override
    public Optional<TariffDTO> partialUpdate(TariffDTO tariffDTO) {
        log.debug("Request to partially update Tariff : {}", tariffDTO);

        return tariffRepository
            .findById(tariffDTO.getId())
            .map(existingTariff -> {
                tariffMapper.partialUpdate(existingTariff, tariffDTO);

                return existingTariff;
            })
            .map(tariffRepository::save)
            .map(tariffMapper::toDto);
    }

    @Override
    public List<TariffDTO> findAll(String countryCode, String partyId, ZonedDateTime dateFrom, ZonedDateTime dateTo, Integer offset, Integer limit) {
        log.debug("Request to get all Tariffs");
        return tariffRepository
                .findAllByLastUpdated(countryCode, partyId, dateFrom, dateTo, offset, limit)
                .stream()
                .map(tariffMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<TariffDTO> findOne(String id) {
        log.debug("Request to get Tariff : {}", id);
        return tariffRepository.findById(id).map(tariffMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Tariff : {}", id);
        tariffRepository.deleteById(id);
    }
}
