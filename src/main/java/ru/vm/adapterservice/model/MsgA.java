package ru.vm.adapterservice.model;

import lombok.Data;

@Data
public class MsgA {
    private String msg;
    private String lng;
    private Coordinates coordinates;
}

