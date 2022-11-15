package com.example.soldapple.create_price.service;

import com.example.soldapple.create_price.dto.MacbookResDto;
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
        List<String> sotrageList = iPhoneRepository.findAllByProductYearAndModelOrderByStorage(year,model).stream().map(IPhone::getStorage).distinct().collect(Collectors.toList());
        return sotrageList;
    }

    public List<Integer> macbookFirst() {
        List<Integer> yearList = macbookRepository.findAllByOrderByProductYear().stream().map(Macbook::getProductYear).distinct().collect(Collectors.toList());
        return yearList;
    }

    public List<String> macbookSecond(Integer year) {
        List<String> modelCpuList = macbookRepository.findAllByProductYearOrderByModel(year).stream().map(macbook -> new String(macbook.getModel() + " - " + macbook.getCpu())).distinct().collect(Collectors.toList());
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
}