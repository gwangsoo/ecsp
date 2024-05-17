package com.example.abc.eventuate;

import com.example.abc.domain.dto.AbcDTO;
import com.example.abc.exception.BadRequestAlertException;
import com.example.abc.service.AbcService;
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

    private final AbcService abcService;

    public AbcDTO abcRegister(AbcDTO abcDTO) {
        return abcService.save(abcDTO);
    }

    public void confirmAbc(AbcDTO abcDTO) throws Exception {
        AbcDTO abcDto = abcService.findOne(abcDTO.getId()).orElseThrow();
        if(abcDto.getStatus() != AbcDTO.AbcStatus.OPEN) {
            throw new BadRequestAlertException("유효한 데이터가 아님", AbcDTO.class.getName(), "idnull");
        }
    }
}
