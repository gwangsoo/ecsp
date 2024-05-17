package com.example.bff.web.graphql;

import com.example.abc.domain.dto.AbcDTO;
import com.example.bff.dto.CompositeDto;
import com.example.bff.service.AsyncTestService;
import com.example.orders.domain.dto.OrdersDTO;
import com.example.xyz.domain.dto.XyzDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Controller
public class GraphQLController {

    private final com.example.bff.service.OrdersService ordersService;
    private final com.example.bff.service.AbcService abcService;
    private final com.example.bff.service.XyzService xyzService;
    private final AsyncTestService asyncTestService;

    @QueryMapping
    public CompositeDto getComposite() {
//        List<OrdersDTO> ordersDTOList = ordersService.getAllOrders(OrdersDTO.OrdersStatus.ACCEPTED);
//        List<AbcDTO> abcDTOList = abcService.getAllAbcs(AbcDTO.AbcStatus.OPEN);
//        List<XyzDTO> xyzDTOList = xyzService.getAllXyzs("1", XyzDTO.XyzStatus.STANDBY);
//        CompositeDto compositeDto = CompositeDto.builder()
//                .abcDTOList(abcDTOList)
//                .xyzDTOList(xyzDTOList)
//                .ordersDTOList(ordersDTOList)
//                .build();

        // 병렬 호출 테스트
        CompletableFuture<List<OrdersDTO>> ordersResult = asyncTestService.getOrdersDTOList(OrdersDTO.OrdersStatus.ACCEPTED);
        CompletableFuture<List<AbcDTO>> abcResult = asyncTestService.getAbcDTOList(AbcDTO.AbcStatus.OPEN);
        CompletableFuture<List<XyzDTO>> xyzResult = asyncTestService.getXyzDTOList("1", XyzDTO.XyzStatus.STANDBY);

        CompletableFuture<CompositeDto> compositeResult = ordersResult.thenCombine(abcResult, (orders, abc) -> {
            CompositeDto compositeDto = new CompositeDto();
            compositeDto.setOrdersDTOList(orders);
            compositeDto.setAbcDTOList(abc);
            return compositeDto;
        }).thenCombine(xyzResult, (compositeDto, xyz) -> {
            compositeDto.setXyzDTOList(xyz);
            return compositeDto;
        });

        CompositeDto result = compositeResult.join();
        log.info("composite result ={}", result);
        return result;
    }
}
