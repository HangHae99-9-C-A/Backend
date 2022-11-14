package com.example.soldapple.global.service;

import com.example.soldapple.global.dto.CreateTableReqDto;
import com.example.soldapple.global.entity.IPhone;
import com.example.soldapple.global.repository.IPhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateTableService {
    private final IPhoneRepository iPhoneRepository;

    public String createTable(String category, CreateTableReqDto createTableReqDto) {
        if(category.equals("IPhone")) {
            System.out.println(category+createTableReqDto.getModel()+createTableReqDto.getYear());
            IPhone iPhone = new IPhone(createTableReqDto);
            iPhoneRepository.save(iPhone);
        } else{
            return "맥북 받는거랑 예외처리해야함";
        }

        return "success";
    }
}
