package com.example.soldapple.global.controller;

import com.example.soldapple.global.dto.CreateTableReqDto;
import com.example.soldapple.global.service.CreateTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/create")
@RequiredArgsConstructor
public class CreateTableController {
    private final CreateTableService createTableService;

    @PostMapping("/{category}")
    public String createTable(@PathVariable String category, @RequestBody CreateTableReqDto createTableReqDto){
        return createTableService.createTable(category,createTableReqDto);
    }
}
