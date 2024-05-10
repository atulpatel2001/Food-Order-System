package com.food.app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_state")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class State extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long countryId;

    private boolean enable;
}
