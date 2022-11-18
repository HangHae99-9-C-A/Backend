package com.example.soldapple.post.repository;

import com.example.soldapple.post.entity.Opt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Opt, Long> {
}
