package com.example.soldapple.global.controller;

import com.example.soldapple.global.dto.CreateIPhoneReqDto;
import com.example.soldapple.global.dto.CreateMacbookReqDto;
import com.example.soldapple.global.service.CreateTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/create")
@RequiredArgsConstructor
public class CreateTableController {
    private final CreateTableService createTableService;

    @PostMapping("/iphone")
    public String createTable(@RequestBody CreateIPhoneReqDto createIPhoneReqDto){
        return createTableService.createIPhone(createIPhoneReqDto);
    }

    @PostMapping("/macbook")
    public String createTable(@RequestBody CreateMacbookReqDto createTableReqDto){
        return createTableService.createMacbook(createTableReqDto);
    }
}
