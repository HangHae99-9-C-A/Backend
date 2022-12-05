package com.example.soldapple.create_price.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Macbook {
    @Id
    @Column(name = "macbookId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long macbookId;

    private Integer productYear;
    private String model;
    private String cpu;
    private Integer inch;
    private String keyboard;
    private String ram;
    private String storage;
    private String opt;
    private Integer price;
}
