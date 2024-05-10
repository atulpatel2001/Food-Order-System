package com.food.app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_user_address")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private Long addressId;
    private boolean enable;

}
