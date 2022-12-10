package com.example.soldapple.create_price.entity;

import com.example.soldapple.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class IPhone {
    @Id
    @Column(name = "iPhoneId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iPhoneId;

    private Integer productYear;
    private String model;
    private String storage;
    private String opt;
    private Integer price;


    private Long soldNum = 0L;
//    @OneToMany(mappedBy = "iphone")
//    private List<MacbookSold> macbookSold = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Post> post = new ArrayList<>();


}
