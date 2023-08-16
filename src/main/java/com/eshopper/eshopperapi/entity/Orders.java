package com.eshopper.eshopperapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Orders implements SuperEntity {
    @Id
    private String id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @OneToMany(targetEntity = OrderDetails.class, mappedBy = "orders", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<OrderDetails> orderDetailsList = new ArrayList<>();
}
