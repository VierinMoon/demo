package ru.vm.adapterservice.weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.vm.adapterservice.model.MsgA;
import ru.vm.adapterservice.model.MsgB;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class WeatherService {

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public int getTemperature(MsgA message) {
        try {
            String coordinates = message.getCoordinates().getLatitude() + "," + message.getCoordinates().getLongitude();

            String url = String.format("%s?q=%s&units=metric&appid=%s",
                    weatherApiUrl, coordinates, apiKey);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());

                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode mainNode = root.get("main");

                return mainNode.get("temp").asInt();
            } else {
                throw new RuntimeException("Failed to fetch weather data");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching weather data", e);
        }
    }
}


