package com.example.orders.service.impl;

import com.example.ecsp.common.util.JsonUtil;
import com.example.orders.domain.dto.OrdersDTO;
import com.example.orders.domain.entity.Orders;
import com.example.orders.eventuate.CreateOrdersSaga;
import com.example.orders.eventuate.event.OrdersInsertEvent;
import com.example.orders.eventuate.event.OrdersUpdateEvent;
import com.example.orders.repository.OrdersRepository;
import com.example.orders.service.mapper.OrdersMapper;
import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.tram.commands.producer.CommandProducer;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * Service Implementation for managing {@link com.example.orders.domain.entity.Orders}.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrdersServiceImpl implements com.example.orders.service.OrdersService {

    private final OrdersRepository ordersRepository;

    private final OrdersMapper ordersMapper;

    private final DomainEventPublisher domainEventPublisher;
    private final CommandProducer commandProducer;

    private final SagaInstanceFactory sagaInstanceFactory;
    private final CreateOrdersSaga createOrdersSaga;

    @Override
    public OrdersDTO save(OrdersDTO OrdersDTO) {
        log.debug("Request to save Orders : {}", OrdersDTO);
        Orders orders = ordersMapper.toEntity(OrdersDTO);
        orders = ordersRepository.save(orders);
        OrdersDTO result = ordersMapper.toDto(orders);

        log.debug("orders = {}", orders);
        log.debug("orders.toJson1 = {}", JsonUtil.toJson(orders));
        log.debug("orders.toJson2 = {}", JSonMapper.toJson(orders));

        sagaInstanceFactory.create(createOrdersSaga, orders);

        // 도메인 이벤트 저장
        DomainEvent domainEvent = new OrdersInsertEvent(orders);
        domainEventPublisher.publish(Orders.class.getName(), orders.getId(), Collections.singletonList(domainEvent));

        return result;
    }

    @Override
    public OrdersDTO update(OrdersDTO OrdersDTO) {
        log.debug("Request to update Orders : {}", OrdersDTO);
        Orders orders = ordersMapper.toEntity(OrdersDTO);
        orders = ordersRepository.save(orders);
        OrdersDTO result = ordersMapper.toDto(orders);

        // 도메인 이벤트 저장
        DomainEvent domainEvent = new OrdersUpdateEvent(orders);
        domainEventPublisher.publish(Orders.class.getName(), orders.getId(), Collections.singletonList(domainEvent));

        return result;
    }

    @Override
    public Optional<OrdersDTO> partialUpdate(OrdersDTO OrdersDTO) {
        log.debug("Request to partially update Orders : {}", OrdersDTO);

        return ordersRepository
            .findById(OrdersDTO.getId())
            .map(existingOrders -> {
                ordersMapper.partialUpdate(existingOrders, OrdersDTO);

                // 도메인 이벤트 저장
                DomainEvent domainEvent = new OrdersUpdateEvent(existingOrders);
                domainEventPublisher.publish(Orders.class.getName(), existingOrders.getId(), Collections.singletonList(domainEvent));

                return existingOrders;
            })
            .map(ordersRepository::save)
            .map(savedOrders -> {
                return savedOrders;
            })
            .map(ordersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrdersDTO> findAll(Orders.OrdersStatus ordersStatus, Pageable pageable) {
        log.debug("Request to get all Orderss");

//        Long length1 = ordersRepository.getLength();
//        Page<Long> length2 = ordersRepository.getLength(pageable);
//        Long length3 = ordersRepository.getLength(OrdersRepository.Spec.equalChatRoom(chatRoomId));

        return ordersRepository
                .findAll(OrdersRepository.Spec.instance().equal("status", ordersStatus), pageable)
                .map(ordersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdersDTO> findOne(String id) {
        log.debug("Request to get Orders : {}", id);

//        Specification<Orders> spec = OrdersRepository.Spec.instance().equal("status", ordersStatus)
//                .and(OrdersRepository.Spec.instance().equal("id", id));
//        return ordersRepository.findOne(spec).map(ordersMapper::toDto);

        return ordersRepository.findById(id)
                .map(ordersMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Orders : {}", id);

        Orders orders = ordersRepository.findById(id).orElse(null);

        if(orders == null) return;

//        Specification<Orders> spec = OrdersRepository.Spec.instance().equal("chatRoom", "id", chatRoomId)
//                .and(OrdersRepository.Spec.instance().equal("id", id));
//        ordersRepository.delete(spec);

        ordersRepository.deleteById(id);

        // 도메인 이벤트 저장
        DomainEvent domainEvent = new OrdersUpdateEvent(orders);
        domainEventPublisher.publish(Orders.class.getName(), orders.getId(), Collections.singletonList(domainEvent));

    }
}
