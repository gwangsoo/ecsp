package com.example.bff.dto;

import com.example.abc.domain.dto.AbcDTO;
import com.example.orders.domain.dto.OrdersDTO;
import com.example.xyz.domain.dto.XyzDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompositeDto {
    private List<OrdersDTO> ordersDTOList;
    private List<AbcDTO> abcDTOList;
    private List<XyzDTO> xyzDTOList;
}
