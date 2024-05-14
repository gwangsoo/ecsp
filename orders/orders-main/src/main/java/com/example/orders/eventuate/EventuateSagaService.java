package com.example.orders.eventuate;

import com.example.abc.domain.dto.AbcDTO;
import com.example.orders.domain.dto.OrdersDTO;
import com.example.orders.domain.entity.Orders;
import com.example.orders.exception.BadRequestAlertException;
import com.example.orders.repository.OrdersRepository;
import com.example.orders.service.AbcService;
import com.example.orders.service.XyzService;
import com.example.orders.service.mapper.OrdersMapper;
import com.example.xyz.domain.dto.XyzDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class EventuateSagaService {

    private final OrdersMapper ordersMapper;

    private final AbcService abcService;
    private final XyzService xyzService;

    private final OrdersRepository ordersRepository;

    public OrdersDTO create(OrdersDTO dto) {
        Orders orders = ordersMapper.toEntity(dto);
        orders = ordersRepository.save(orders);
        return ordersMapper.toDto(orders);
    }

    public void reject(OrdersDTO data) {
        ordersRepository.findById(data.getId()).get().setStatus(OrdersDTO.OrdersStatus.REJECTED);
    }

    public AbcDTO handleAbcReply(AbcDTO dto) {
        return abcService.getAbc(dto.getId());
    }

    public XyzDTO handleXyzReply(XyzDTO dto) {
        return xyzService.getXyz(dto.getId());
    }

    public void handleAccountException(OrdersDTO data, Exception reply) {
        log.error("handleAccountException", reply);
    }

    public void approve(OrdersDTO data) {
        ordersRepository.findById(data.getId()).get().setStatus(OrdersDTO.OrdersStatus.APPROVED);
    }
}
