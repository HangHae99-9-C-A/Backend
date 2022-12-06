package com.example.soldapple.create_price.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MacbookResDto {
    private List<String> keyboard;
    private List<String> ram;
    private List<String> storage;

    public MacbookResDto(List<String> keyboard, List<String> ram, List<String> storage) {
        this.keyboard = keyboard;
        this.ram = ram;
        this.storage = storage;
    }
}
