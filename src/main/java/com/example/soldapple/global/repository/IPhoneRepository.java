package com.example.soldapple.global.repository;

import com.example.soldapple.global.entity.IPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPhoneRepository extends JpaRepository<IPhone,Long> {
    List<IPhone> findAllByOrderByProductYear();
    List<IPhone> findAllByProductYearOrderByModel(Integer productYear);
    List<IPhone> findAllByProductYearAndModelOrderByStorage(Integer productYear, String model);
    IPhone findByProductYearAndModelAndStorageAndOpt(Integer productYear, String model, String storage, String option);
    IPhone findByProductYearAndModelAndStorage(Integer productYear, String model, String storage);
}
