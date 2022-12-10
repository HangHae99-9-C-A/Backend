package com.example.soldapple.create_price.entity;

import com.example.soldapple.post.entity.Post;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class MacbookSold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "macbook_sold_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "macbook_id")
    private Macbook macbook;
    private Long soldPrice;

    @OneToOne(fetch = FetchType.LAZY)
    private Post post;

}
