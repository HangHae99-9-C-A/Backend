package com.example.soldapple.global.service;

import com.example.soldapple.global.dto.CreateIPhoneReqDto;
import com.example.soldapple.global.dto.CreateMacbookReqDto;
import com.example.soldapple.global.entity.IPhone;
import com.example.soldapple.global.entity.Macbook;
import com.example.soldapple.global.repository.IPhoneRepository;
import com.example.soldapple.global.repository.MacbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateTableService {
    private final IPhoneRepository iPhoneRepository;
    private final MacbookRepository macbookRepository;

    public String createIPhone(CreateIPhoneReqDto createIPhoneReqDto) {
        IPhone iPhone = new IPhone(createIPhoneReqDto);
        iPhoneRepository.save(iPhone);
        return "success";
    }

    public String createMacbook(CreateMacbookReqDto createMacbookReqDto) {
        Macbook macbook = new Macbook(createMacbookReqDto);
        macbookRepository.save(macbook);
        return "success";
    }
}
