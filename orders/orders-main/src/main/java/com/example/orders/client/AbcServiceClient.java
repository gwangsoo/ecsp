package com.example.orders.client;

import com.example.abc.domain.dto.AbcDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface AbcServiceClient {
    @RequestLine("POST /services/abc/api/abcs")
    @Headers("Content-Type: application/json")
    AbcDTO createAbc(AbcDTO abcDTO);

    @RequestLine("PATCH /services/abc/api/abcs/{id}")
    @Headers("Content-Type: application/json")
    AbcDTO partialUpdateAbc(@Param("id") String id, AbcDTO abcDTO);

    @RequestLine("DELETE /services/abc/api/abcs/{id}")
    @Headers("Content-Type: application/json")
    void deleteAbc(@Param("id") String id);

    @RequestLine("GET /services/abc/api/abcs")
    @Headers("Content-Type: application/json")
    List<AbcDTO> getAllAbcs();

    @RequestLine("GET /services/abc/api/abcs/{id}")
    @Headers("Content-Type: application/json")
    AbcDTO getAbc(@Param("id") String id);
}
