package com.example.soldapple.create_price.controller;

import com.example.soldapple.create_price.service.CreatePriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
public class CreatePriceController {
    private final CreatePriceService createPriceService;

    @GetMapping("/iphone")  //iPhone,Macbook
    public List<Long> chooseTag1() {
        return createPriceService.first();
    }

    @GetMapping("/iphone/{year}")   //출시년도
    public List<String> chooseTag2(@PathVariable Long year) {
        return createPriceService.second(year);
    }

    @GetMapping("/iphone/{year}/{model}")    //기종
    public List<?> chooseTag3(@PathVariable Long year,
                              @PathVariable String model) {
        return createPriceService.third(year, model);

    }
}
