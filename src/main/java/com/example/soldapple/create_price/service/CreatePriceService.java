package com.example.soldapple.create_price.service;

import com.example.soldapple.global.entity.IPhone;
import com.example.soldapple.global.entity.Macbook;
import com.example.soldapple.global.repository.IPhoneRepository;
import com.example.soldapple.global.repository.MacbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CreatePriceService {
    private final IPhoneRepository iPhoneRepository;
    private final MacbookRepository macbookRepository;

    public List<Long> iphoneFirst() {
        List<IPhone> iPhones = iPhoneRepository.findAllByOrderByAsd();
        List<Long> longList = new ArrayList<>();
        for (int i = 0; i < iPhones.size(); i++) {
            if (i == 0) {
                longList.add(iPhones.get(i).getAsd());
            } else {
                if (iPhones.get(i).getAsd().equals(iPhones.get(i - 1).getAsd())) {
                    continue;
                }
                longList.add(iPhones.get(i).getAsd());
            }
        }
        return longList;
}

    public List<String> iphoneSecond(Long year) {
        List<IPhone> iPhones = iPhoneRepository.findAllByAsdOrderByModel(year);
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < iPhones.size(); i++) {
            if (i == 0) {
                stringList.add(iPhones.get(i).getModel());
            } else {
                if (iPhones.get(i).getModel().equals(iPhones.get(i - 1).getModel())) {
                    continue;
                }
                stringList.add(iPhones.get(i).getModel());
            }
        }
        return stringList;
    }

    public List<String> iphoneThird(Long year, String model) {
        List<IPhone> iPhones = iPhoneRepository.findAllByAsdAndModelOrderByStorage(year, model);
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < iPhones.size(); i++) {
            if (i == 0) {
                stringList.add(iPhones.get(i).getStorage());
            } else {
                if (iPhones.get(i).getStorage().equals(iPhones.get(i - 1).getStorage())) {
                    continue;
                }
                stringList.add(iPhones.get(i).getStorage());
            }
        }
        return stringList;
    }

    public List<Long> macbookFirst() {
        List<Macbook> macbooks = macbookRepository.findAllByOrderByAsd();
        List<Long> longList = new ArrayList<>();
        for (int i = 0; i < macbooks.size(); i++) {
            if (i == 0) {
                longList.add(macbooks.get(i).getAsd());
            } else {
                if (macbooks.get(i).getAsd().equals(macbooks.get(i - 1).getAsd())) {
                    continue;
                }
                longList.add(macbooks.get(i).getAsd());
            }
        }
        return longList;
    }
}