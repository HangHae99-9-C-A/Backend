package com.example.soldapple.create_price.repository;

import com.example.soldapple.create_price.entity.IPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPhoneRepository extends JpaRepository<IPhone,Long> {
    List<IPhone> findAllByOrderByProductYear();
    List<IPhone> findAllByProductYearOrderByModel(Integer productYear);
    List<IPhone> findAllByProductYearAndModel(Integer productYear, String model);
    IPhone findByProductYearAndModelAndStorageAndOpt(Integer productYear, String model, String storage, String option);
}
