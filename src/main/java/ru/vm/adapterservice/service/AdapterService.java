package ru.vm.adapterservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vm.adapterservice.model.MsgA;
import ru.vm.adapterservice.model.MsgB;
import ru.vm.adapterservice.weather.WeatherService;

import java.time.LocalDateTime;

@Service
public class AdapterService {

    @Autowired
    private WeatherService weatherService;

    public MsgB process(MsgA message) throws IllegalArgumentException {
        if (message.getMsg() == null || message.getMsg().isEmpty()) {
            throw new IllegalArgumentException("Empty message received");
        }

        if (!message.getLng().equals("ru")) {
            throw new IllegalArgumentException("Invalid language code");
        }

        if (message.getCoordinates() == null) {
            throw new IllegalArgumentException("Missing coordinates");
        }

        try {
            int temperature = weatherService.getTemperature(message);
            MsgB result = new MsgB();
            result.setTxt(message.getMsg());
            result.setCreatedDt(LocalDateTime.now());
            result.setCurrentTemp(temperature);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Error processing message", e);
        }
    }
}

