package ru.vm.adapterservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.vm.adapterservice.model.MsgB;

@Service
public class DataSenderService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.b.url}")
    private String serviceBUrl;

    public void sendToServiceB(MsgB data) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<MsgB> entity = new HttpEntity<>(data, headers);

            ResponseEntity<Void> response = restTemplate.postForEntity(serviceBUrl, entity, Void.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to send data to Service B");
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Error sending data to Service B", e);
        }
    }
}
