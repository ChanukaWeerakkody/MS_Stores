package com.eshopper.eshopperapi.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
public class Item implements SuperEntity {
    @Id
    private String code;
    private String description;
    private Integer qty;
    private Double price;

    @OneToMany(targetEntity = OrderDetails.class, mappedBy = "item", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<OrderDetails> orderDetailsList = new ArrayList<>();
}
