package com.example.soldapple.create_price.repository;

import com.example.soldapple.create_price.entity.Macbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MacbookRepository extends JpaRepository<Macbook,Long> {
    List<Macbook> findAllByOrderByProductYear();
    List<Macbook> findAllByProductYearOrderByModel(Integer productYear);
    List<Macbook> findAllByProductYearAndModelAndCpuOrderByInch(Integer productYear, String model, String cpu);
    List<Macbook> findAllByProductYearAndModelAndCpuAndInchOrderByRam(Integer productYear, String model, String cpu, Integer inch);
    List<Macbook> findAllByProductYearAndModelAndCpuAndInchOrderByStorage(Integer productYear, String model, String cpu, Integer inch);
    Macbook findByProductYearAndModelAndCpuAndInchAndKeyboardAndRamAndStorageAndOpt(Integer productYear, String model, String cpu, Integer inch, String keyboard, String ram, String storage, String option);
}
