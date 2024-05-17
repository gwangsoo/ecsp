package com.example.xyz.eventuate;

import com.example.xyz.domain.dto.XyzDTO;
import com.example.xyz.service.XyzService;
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

    private final XyzService xyzService;

    public XyzDTO xyzRegister(XyzDTO dto) {
        return xyzService.save(dto);
    }
}
