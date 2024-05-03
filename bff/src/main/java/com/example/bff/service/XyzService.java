package com.example.bff.service;

import com.example.xyz.domain.dto.XyzDTO;

import java.util.List;

public interface XyzService {

    XyzDTO createXyz(XyzDTO xyzDTO);
    XyzDTO partialUpdateXyz(String id, XyzDTO xyzDTO);
    void deleteXyz(String id);
    List<XyzDTO> getAllXyzs();
    XyzDTO getXyz(String id);
}
