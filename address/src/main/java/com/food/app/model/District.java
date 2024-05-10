package com.food.app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_district")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class District extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long stateId;

    private boolean enable;
}
