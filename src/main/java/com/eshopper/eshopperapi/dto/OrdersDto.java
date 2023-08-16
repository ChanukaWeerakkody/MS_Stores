package com.eshopper.eshopperapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrdersDto implements SuperDto {
    private String id;

    private CustomerDto customerDto;

    @ToString.Exclude
    private List<OrderDetailsDto> orderDetailsDtoList = new ArrayList<>();
}
