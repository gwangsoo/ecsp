package com.example.orders.service;

import com.example.abc.domain.dto.AbcDTO;

import java.util.List;

public interface AbcService {

    AbcDTO createAbc(AbcDTO abcDTO);
    AbcDTO partialUpdateAbc(String id, AbcDTO abcDTO);
    void deleteAbc(String id);
    List<AbcDTO> getAllAbcs();
    AbcDTO getAbc(String id);
}
