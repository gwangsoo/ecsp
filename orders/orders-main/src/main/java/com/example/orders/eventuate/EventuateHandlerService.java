package com.example.orders.eventuate;

import com.example.orders.domain.dto.OrdersDTO;
import com.example.orders.domain.entity.Orders;
import com.example.orders.exception.BadRequestAlertException;
import com.example.orders.repository.OrdersRepository;
import com.example.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class EventuateHandlerService {

    private final OrdersService ordersService;

    private final OrdersRepository ordersRepository;

    public OrdersDTO orderRegister(OrdersDTO dto) {
        return ordersService.save(dto);
    }

    public void confirmOrders(OrdersDTO dto) throws Exception {
        Orders orders = ordersRepository.findById(dto.getId()).orElseThrow();
        if(orders.getStatus() == OrdersDTO.OrdersStatus.APPROVED) {
            throw new BadRequestAlertException("유효한 데이터가 아님", OrdersDTO.class.getName(), "idnull");
        }
    }
}
