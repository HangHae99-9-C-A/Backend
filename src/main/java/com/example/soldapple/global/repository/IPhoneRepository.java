package com.example.soldapple.global.repository;

import com.example.soldapple.global.entity.IPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPhoneRepository extends JpaRepository<IPhone,Long> {
    List<IPhone> findAllByOrderByAsd();
    List<IPhone> findAllByAsdOrderByModel(Long asd);

    List<IPhone> findAllByAsdAndModelOrderByStorage(Long asd, String model);
}
