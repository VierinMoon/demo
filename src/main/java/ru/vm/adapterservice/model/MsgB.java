package ru.vm.adapterservice.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MsgB {
    private String txt;
    private LocalDateTime createdDt;
    private int currentTemp;
}
