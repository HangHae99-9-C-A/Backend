package com.example.soldapple.create_price.service;

import com.example.soldapple.create_price.dto.*;
import com.example.soldapple.global.entity.IPhone;
import com.example.soldapple.global.entity.Macbook;
import com.example.soldapple.global.repository.IPhoneRepository;
import com.example.soldapple.global.repository.MacbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CreatePriceService {
    private final IPhoneRepository iPhoneRepository;
    private final MacbookRepository macbookRepository;

    public List<Integer> iphoneFirst() {
        List<Integer> yearList = iPhoneRepository.findAllByOrderByProductYear().stream().map(IPhone::getProductYear).distinct().collect(Collectors.toList());
        return yearList;
    }

    public List<String> iphoneSecond(Integer year) {
        List<String> modelList = iPhoneRepository.findAllByProductYearOrderByModel(year).stream().map(IPhone::getModel).distinct().collect(Collectors.toList());
        return modelList;
    }

    public List<String> iphoneThird(Integer year, String model) {
        List<String> storageList = iPhoneRepository.findAllByProductYearAndModelOrderByStorage(year,model).stream().map(IPhone::getStorage).distinct().collect(Collectors.toList());
        return storageList;
    }

    public List<Integer> macbookFirst() {
        List<Integer> yearList = macbookRepository.findAllByOrderByProductYear().stream().map(Macbook::getProductYear).distinct().collect(Collectors.toList());
        return yearList;
    }

    public List<String> macbookSecond(Integer year) {
        List<String> modelCpuList = macbookRepository.findAllByProductYearOrderByModel(year).stream().map(macbook -> new String(macbook.getModel() + "-" + macbook.getCpu())).distinct().collect(Collectors.toList());
        return modelCpuList;
    }

    public List<Integer> macbookThird(Integer year, String model, String cpu) {
        List<Integer> inchList = macbookRepository.findAllByProductYearAndModelAndCpuOrderByInch(year, model, cpu).stream().map(Macbook::getInch).distinct().collect(Collectors.toList());
        return inchList;
    }

    public MacbookResDto macbookFourth(Integer year, String model, String cpu, Integer inch) {
        List<String> keyboardList = macbookRepository.findAllByProductYearAndModelAndCpuAndInchOrderByRam(year,model,cpu,inch).stream().map(Macbook::getKeyboard).distinct().collect(Collectors.toList());
        List<String> ramList = macbookRepository.findAllByProductYearAndModelAndCpuAndInchOrderByRam(year,model,cpu,inch).stream().map(Macbook::getRam).distinct().collect(Collectors.toList());
        List<String> storageList = macbookRepository.findAllByProductYearAndModelAndCpuAndInchOrderByStorage(year,model,cpu,inch).stream().map(Macbook::getStorage).distinct().collect(Collectors.toList());

        return new MacbookResDto(keyboardList, ramList, storageList);
    }

    public GetIPhonePriceResDto getIPhonePrice(GetIPhonePriceReqDto getIPhonePriceReqDto) {
        int battery = getIPhonePriceReqDto.getBatteryState()/2;
        int state;
        String option;
        if(getIPhonePriceReqDto.getIphoneState().equals("S급")){
            state=50;
        }else if(getIPhonePriceReqDto.getIphoneState().equals("A급")) {
            state=40;
        }else if (getIPhonePriceReqDto.getIphoneState().equals("B급")) {
            state=30;
        }else {
            state=20;
        }

        if(battery+state>=90){
            option = "s";
        }else if(battery+state>=70){
            option = "a";
        }else if(battery+state>=40){
            option = "b";
        }else{
            option = "c";
        }
        int price = iPhoneRepository.findByProductYearAndModelAndStorageAndOpt(getIPhonePriceReqDto.getYear(), getIPhonePriceReqDto.getModel(), getIPhonePriceReqDto.getOption(), option).getPrice();
        return new GetIPhonePriceResDto(getIPhonePriceReqDto, price);
    }

    public GetMacbookPriceResDto getMacbookPrice(GetMacbookPriceReqDto getMacbookPriceReqDto) {
        String model = getMacbookPriceReqDto.getModel().split("-")[0];
        String cpu = getMacbookPriceReqDto.getModel().split("-")[1];
        int battery;
        if(getMacbookPriceReqDto.getBatteryState()>1000){
            battery = 0;
        }else { battery = 50 - getMacbookPriceReqDto.getBatteryState()/20;}
        int state;
        String option;
        if(getMacbookPriceReqDto.getMacbookState().equals("S급")){
            state=50;
        }else if(getMacbookPriceReqDto.getMacbookState().equals("A급")) {
            state=40;
        }else if (getMacbookPriceReqDto.getMacbookState().equals("B급")) {
            state=30;
        }else {
            state=20;
        }

        if(battery+state>=90){
            option = "s";
        }else if(battery+state>=70){
            option = "a";
        }else if(battery+state>=40){
            option = "b";
        }else{
            option = "c";
        }
        int price = macbookRepository.findByProductYearAndModelAndCpuAndInchAndKeyboardAndRamAndStorageAndOpt(getMacbookPriceReqDto.getYear(),model,cpu,getMacbookPriceReqDto.getOption(),getMacbookPriceReqDto.getKeyboard(),getMacbookPriceReqDto.getRam(),getMacbookPriceReqDto.getStorage(),option).getPrice();

        return new GetMacbookPriceResDto(getMacbookPriceReqDto, price);
    }
}