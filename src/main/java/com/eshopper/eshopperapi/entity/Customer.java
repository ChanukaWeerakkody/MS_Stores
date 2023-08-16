package com.eshopper.eshopperapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Customer implements SuperEntity {
    @Id
    private String id;
    private String name;
    private String address;
    private Double salary;

    @OneToMany(targetEntity = Orders.class, mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Orders> ordersList = new ArrayList<>();
}
