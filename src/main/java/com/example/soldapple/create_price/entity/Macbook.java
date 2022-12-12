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


    private Long soldNum = 0L;

//    @OneToMany(mappedBy = "macbook")
//    private List<MacbookSold> macbookSold = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Post> post = new ArrayList<>();


    public void update(Integer price) {
        this.price = price;
    }
}
