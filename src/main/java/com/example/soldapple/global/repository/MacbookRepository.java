package com.example.soldapple.global.repository;

import com.example.soldapple.global.entity.Macbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MacbookRepository extends JpaRepository<Macbook,Long> {
    List<Macbook> findAllByOrderByAsd();
}
