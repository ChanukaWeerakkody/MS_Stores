package com.eshopper.eshopperapi.dto;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class ItemDto implements SuperDto {
    private String code;
    private String description;
    private Integer qty;
    private Double price;
    @ToString.Exclude
    private List<OrderDetailsDto> orderDetailsDtoList = new ArrayList<>();
}
