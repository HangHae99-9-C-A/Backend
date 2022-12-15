package com.example.soldapple.create_price;

import com.example.soldapple.create_price.dto.*;
import com.example.soldapple.create_price.entity.IPhone;
import com.example.soldapple.create_price.entity.Macbook;
import com.example.soldapple.create_price.repository.IPhoneRepository;
import com.example.soldapple.create_price.repository.MacbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CreatePriceService {
    private final IPhoneRepository iPhoneRepository;
    private final MacbookRepository macbookRepository;

    public List<Integer> iphoneFirst() {    //아이폰 출시년도 가져오기
        return iPhoneRepository.findAllByOrderByProductYear().stream().map(IPhone::getProductYear).distinct().collect(Collectors.toList());
    }

    public List<String> iphoneSecond(Integer year) {    //아이폰 모델명 가져오기
        return iPhoneRepository.findAllByProductYearOrderByModel(year).stream().map(IPhone::getModel).distinct().collect(Collectors.toList());
    }

    public List<String> iphoneThird(Integer year, String model) {   //아이폰 저장공간 가져오기
        return iPhoneRepository.findAllByProductYearAndModel(year, model).stream().map(IPhone::getStorage).distinct().collect(Collectors.toList());
    }

    public List<Integer> macbookFirst() {   //맥북 출시년도 가져오기
        return macbookRepository.findAllByOrderByProductYear().stream().map(Macbook::getProductYear).distinct().collect(Collectors.toList());
    }

    public List<String> macbookSecond(Integer year) {   //맥북 모델명 - CPU 가져오기
        return macbookRepository.findAllByProductYearOrderByModel(year).stream().map(macbook -> macbook.getModel() + "-" + macbook.getCpu()).distinct().collect(Collectors.toList());
    }

    public List<Integer> macbookThird(Integer year, String model, String cpu) { //맥북 인치 가져오기
        return macbookRepository.findAllByProductYearAndModelAndCpuOrderByInch(year, model, cpu).stream().map(Macbook::getInch).distinct().collect(Collectors.toList());
    }

    public MacbookResDto macbookFourth(Integer year, String model, String cpu, Integer inch) {  //맥북 키보드, 램, 저장공간 가져오기
        List<String> keyboardList = macbookRepository.findAllByProductYearAndModelAndCpuAndInchOrderByRam(year, model, cpu, inch).stream().map(Macbook::getKeyboard).distinct().collect(Collectors.toList());  //중복 제거
        List<String> ramList = macbookRepository.findAllByProductYearAndModelAndCpuAndInchOrderByRam(year, model, cpu, inch).stream().map(Macbook::getRam).distinct().collect(Collectors.toList());    //중복 제거
        List<String> storageList = macbookRepository.findAllByProductYearAndModelAndCpuAndInch(year, model, cpu, inch).stream().map(Macbook::getStorage).distinct().collect(Collectors.toList());    //중복 제거

        return new MacbookResDto(keyboardList, ramList, storageList);
    }

    public GetIPhonePriceResDto getIPhonePrice(GetIPhonePriceReqDto getIPhonePriceReqDto) { //아이폰 가격 책정
        //상태의 총량을 100으로 보고 배터리(50) + 상태(50) 으로 급을 나눔
        int battery;
        int state;
        String option;
        if (getIPhonePriceReqDto.getBatteryState()>91) { //상태에 따라 급을 나눠 점수를 매김
            battery = 50;
        } else if (getIPhonePriceReqDto.getBatteryState()>85) {
            battery = 35;
        } else if (getIPhonePriceReqDto.getBatteryState()>80) {
            battery = 20;
        }else {
            battery = 0;
        }

        if (getIPhonePriceReqDto.getIphoneState().equals("Class A")) { //상태에 따라 급을 나눠 점수를 매김
            state = 50;
        } else if (getIPhonePriceReqDto.getIphoneState().equals("Class B")) {
            state = 35;
        } else {
            state = 10;
        }

        if (battery + state >= 80) {  //배터리 + 상태의 값으로 급을 나눔
            option = "a";
        } else if (battery + state >= 50) {
            option = "b";
        } else {
            option = "c";
        }
        int price = iPhoneRepository.findByProductYearAndModelAndStorageAndOpt(getIPhonePriceReqDto.getYears(), getIPhonePriceReqDto.getModel(), getIPhonePriceReqDto.getOptions(), option).getPrice(); //급에 맞는 가격을 가져옴
        String iphoneState = "Class " + option.toUpperCase();
        return new GetIPhonePriceResDto(getIPhonePriceReqDto, iphoneState, price);
    }

    public GetMacbookPriceResDto getMacbookPrice(GetMacbookPriceReqDto getMacbookPriceReqDto) { //맥북 가격 책정
        //model-cpu 형태로 값을 가져오기에 각각의 값을 가져오기위해 '-'를 구분자로 하여 나눔
        String model = getMacbookPriceReqDto.getModel().split("-")[0];
        String cpu = getMacbookPriceReqDto.getModel().split("-")[1];
        //상태의 총량을 100으로 보고 배터리(50) + 상태(50) 으로 급을 나눔
//        int battery;
//        if (getMacbookPriceReqDto.getBatteryState() > 1000) {   //배터리 사용량에 따라 점수를 매김
//            battery = 0;
//        } else {
//            battery = 50 - getMacbookPriceReqDto.getBatteryState() / 20;
//        }
//        int state;
//        String option;
//        if (getMacbookPriceReqDto.getMacbookState().equals("Class A")) {   //상태에 따라 급을 나눠 점수를 매김
//            state = 50;
//        } else if (getMacbookPriceReqDto.getMacbookState().equals("Class B")) {
//            state = 35;
//        } else {
//            state = 25;
//        }

//        if (battery + state >= 80) {  //배터리 + 상태의 값으로 급을 나눔
//            option = "a";
//        } else if (battery + state >= 50) {
//            option = "b";
//        } else {
//            option = "c";
//        }


        int battery;
        if(getMacbookPriceReqDto.getBatteryState() > 700){
            battery = 0;
        }else {
            battery = 30 - getMacbookPriceReqDto.getBatteryState()/(700/30);
        }

        int restCarePoint = 0;

        if(getMacbookPriceReqDto.getCareOX().equals("O")) {
            Period restCareDate = Period.between(LocalDate.now(), LocalDate.parse(getMacbookPriceReqDto.getCareDate()));
            int restCareMonths = restCareDate.getYears() * 12 + restCareDate.getMonths();
            if (restCareMonths < 6) {
                restCarePoint = 0;
            } else if (restCareMonths < 12) {
                restCarePoint = 10;
            } else if (restCareMonths < 18) {
                restCarePoint = 20;
            } else if (restCareMonths < 24) {
                restCarePoint = 25;
            } else {
                restCarePoint = 30;
            }
        }

        int state;
        if(getMacbookPriceReqDto.getMacbookState().equals("Class A")){
            state = 40;
        } else if (getMacbookPriceReqDto.getMacbookState().equals("Class B")) {
            state = 30;
        } else {
            state = 20;
        }

        String option;
        if (battery + state + restCarePoint >= 80) {  //배터리 + 상태 + 잔여애플케어플러스의 값으로 급을 나눔
            option = "a";
        } else if (battery + state + restCarePoint >= 50) {
            option = "b";
        } else {
            option = "c";
        }



        int inch = Integer.parseInt(getMacbookPriceReqDto.getOptions());
        int price = macbookRepository.findByProductYearAndModelAndCpuAndInchAndKeyboardAndRamAndStorageAndOpt(getMacbookPriceReqDto.getYears(), model, cpu, inch, getMacbookPriceReqDto.getKeyboard(), getMacbookPriceReqDto.getRam(), getMacbookPriceReqDto.getStorage(), option).getPrice();  //급에 맞는 가격을 가져옴
        String macbookState = "Class " + option.toUpperCase();

        return new GetMacbookPriceResDto(getMacbookPriceReqDto, macbookState, price);
    }
}