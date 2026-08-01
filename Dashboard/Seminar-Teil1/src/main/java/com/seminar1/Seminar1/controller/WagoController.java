package com.seminar1.Seminar1.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.seminar1.Seminar1.services.MqttService;

@RestController
@RequestMapping("/wago")
@CrossOrigin(origins = "*")
public class WagoController {

    private static final Logger log = LoggerFactory.getLogger(WagoController.class);

    private final MqttService mqttService;

    public WagoController(MqttService mqttService) {
        this.mqttService = mqttService;
    }

    @PostMapping("/control/{mode}")
    public String publishMode(@PathVariable int mode) {
        mqttService.publishMode(mode);
        log.info("POST /wago/control - Steuerbefehl empfangen: {}", mode);
        return "Mode " + mode + " published";
    }
}
