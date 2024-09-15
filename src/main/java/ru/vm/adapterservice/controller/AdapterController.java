package ru.vm.adapterservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vm.adapterservice.model.MsgA;
import ru.vm.adapterservice.model.MsgB;
import ru.vm.adapterservice.service.AdapterService;
import ru.vm.adapterservice.service.DataSenderService;


@RestController
@RequestMapping("/adapter")
public class AdapterController {

    @Autowired
    private AdapterService adapterService;

    @Autowired
    private DataSenderService dataSenderService;

    @PostMapping
    public ResponseEntity<MsgB> processMessage(@RequestBody MsgA message) {
        try {
            MsgB result = adapterService.process(message);
            dataSenderService.sendToServiceB(result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
