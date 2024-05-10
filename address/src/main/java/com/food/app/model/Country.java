package com.food.app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_country")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Country extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean enable;
}
