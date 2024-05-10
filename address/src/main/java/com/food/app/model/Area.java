package com.food.app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_area")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Area extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String pinCode;

    private Long cityId;

    private boolean enable;
}
