package com.example.bff.client;

import com.example.abc.domain.dto.AbcDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

@Headers("Authorization: Bearer {oidcToken}")
public interface AbcServiceClient {
    @RequestLine("POST /services/abc/api/abcs")
    @Headers("Content-Type: application/json")
    AbcDTO createAbc(@Param("oidcToken") String oidcToken, AbcDTO abcDTO);

    @RequestLine("PATCH /services/abc/api/abcs/{id}")
    @Headers("Content-Type: application/json")
    AbcDTO partialUpdateAbc(@Param("oidcToken") String oidcToken, @Param("id") String id, AbcDTO abcDTO);

    @RequestLine("DELETE /services/abc/api/abcs/{id}")
    @Headers("Content-Type: application/json")
    void deleteAbc(@Param("oidcToken") String oidcToken, @Param("id") String id);

    @RequestLine("GET /services/abc/api/abcs")
    @Headers("Content-Type: application/json")
    List<AbcDTO> getAllAbcs(@Param("oidcToken") String oidcToken);

    @RequestLine("GET /services/abc/api/abcs/{id}")
    @Headers("Content-Type: application/json")
    AbcDTO getAbc(@Param("oidcToken") String oidcToken, @Param("id") String id);
}
