package com.example.orders.client;

import com.example.xyz.domain.dto.XyzDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface XyzServiceClient {
    @RequestLine("POST /services/xyz/api/xyzs")
    @Headers("Content-Type: application/json")
    XyzDTO createXyz(XyzDTO xyzDTO);

    @RequestLine("PATCH /services/xyz/api/xyzs/{id}")
    @Headers("Content-Type: application/json")
    XyzDTO partialUpdateXyz(@Param("id") String id, XyzDTO xyzDTO);

    @RequestLine("DELETE /services/xyz/api/xyzs/{id}")
    @Headers("Content-Type: application/json")
    void deleteXyz(@Param("id") String id);

    @RequestLine("GET /services/xyz/api/xyzs")
    @Headers("Content-Type: application/json")
    List<XyzDTO> getAllXyzs();

    @RequestLine("GET /services/xyz/api/xyzs/{id}")
    @Headers("Content-Type: application/json")
    XyzDTO getXyz(@Param("id") String id);
}
