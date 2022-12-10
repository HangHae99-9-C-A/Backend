package com.example.soldapple.create_price;

import com.example.soldapple.create_price.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
public class CreatePriceController {
    private final CreatePriceService createPriceService;

    @PostMapping("/iphone") //아이폰 가격 책정
    public GetIPhonePriceResDto getIPhonePrice(@RequestBody GetIPhonePriceReqDto getIPhonePriceResDto) {
        return createPriceService.getIPhonePrice(getIPhonePriceResDto);
    }

    @PostMapping("/macbook")    //맥북 가격 책정
    public GetMacbookPriceResDto getMacbookPrice(@RequestBody GetMacbookPriceReqDto getMacbookPriceReqDto) {
        return createPriceService.getMacbookPrice(getMacbookPriceReqDto);
    }

    @GetMapping("/iphone")  //아이폰 첫번째 조건 생성(출시년도)
    public List<Integer> iphoneFirst() {
        return createPriceService.iphoneFirst();
    }

    @GetMapping("/iphone/{year}")   //아이폰 두번째 조건 생성(기종)
    public List<String> iphoneSecond(@PathVariable Integer year) {
        return createPriceService.iphoneSecond(year);
    }

    @GetMapping("/iphone/{year}/{model}")    //아이폰 세번째 조건 생성(저장공간)
    public List<String> iphoneThird(@PathVariable Integer year,
                                    @PathVariable String model) {
        return createPriceService.iphoneThird(year, model);
    }

    @GetMapping("/macbook")  //맥북 첫번째 조건 생성(출시년도)
    public List<Integer> macbookFirst() {
        return createPriceService.macbookFirst();
    }

    @GetMapping("/macbook/{year}")   //맥북 두번째 조건 생성(기종-CPU)
    public List<String> macbookSecond(@PathVariable Integer year) {
        return createPriceService.macbookSecond(year);
    }

    @GetMapping("/macbook/{year}/{model}-{cpu}")    //맥북 세번째 조건 생성(인치)
    public List<Integer> macbookThird(@PathVariable Integer year,
                                      @PathVariable String model,
                                      @PathVariable String cpu) {
        return createPriceService.macbookThird(year, model, cpu);
    }

    @GetMapping("/macbook/{year}/{model}-{cpu}/{inch}")    //맥북 네번째 조건 생성(키보드,램,저장공간)
    public MacbookResDto macbookFourth(@PathVariable Integer year,
                                       @PathVariable String model,
                                       @PathVariable String cpu,
                                       @PathVariable Integer inch) {
        return createPriceService.macbookFourth(year, model, cpu, inch);
    }

}
