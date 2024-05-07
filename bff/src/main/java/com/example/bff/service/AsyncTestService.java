package com.example.bff.service;

import com.example.abc.domain.dto.AbcDTO;
import com.example.orders.domain.dto.OrdersDTO;
import com.example.xyz.domain.dto.XyzDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncTestService {

    private final com.example.bff.service.OrdersService ordersService;
    private final com.example.bff.service.AbcService abcService;
    private final com.example.bff.service.XyzService xyzService;

    @Async
    public CompletableFuture<List<OrdersDTO>> getOrdersDTOList(OrdersDTO.OrdersStatus status) {
        log.debug("REST request to get all Orders");
        List<OrdersDTO> ordersDTOList = ordersService.getAllOrders(status);
        log.debug("getAllOrders={}", ordersDTOList);
        return CompletableFuture.completedFuture(ordersDTOList);
    }

    @Async
    public CompletableFuture<List<AbcDTO>> getAbcDTOList(AbcDTO.AbcStatus status) {
        log.debug("REST request to get all Abcs");
        List<AbcDTO> abcDTOList = abcService.getAllAbcs(status);
        log.debug("getAllAbcs={}", abcDTOList);
        return CompletableFuture.completedFuture(abcDTOList);
    }

    @Async
    public CompletableFuture<List<XyzDTO>> getXyzDTOList(String attrValue, XyzDTO.XyzStatus status) {
        log.debug("REST request to get all Abcs");
        List<XyzDTO> xyzDTOList = xyzService.getAllXyzs(attrValue, status);
        log.debug("getAllXyzs={}", xyzDTOList);
        return CompletableFuture.completedFuture(xyzDTOList);
    }
}
