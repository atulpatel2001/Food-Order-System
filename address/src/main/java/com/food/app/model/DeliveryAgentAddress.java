package com.food.app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_deliveryAgent_address")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAgentAddress extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deliveryAgentId;
    private Long addressId;
    private boolean enable;

}
