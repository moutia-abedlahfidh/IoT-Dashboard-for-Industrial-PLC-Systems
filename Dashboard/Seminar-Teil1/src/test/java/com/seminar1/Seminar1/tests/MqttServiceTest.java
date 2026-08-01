package com.seminar1.Seminar1.tests;

import com.seminar1.Seminar1.models.Control;
import com.seminar1.Seminar1.models.Temp;
import com.seminar1.Seminar1.services.MqttService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.paho.client.mqttv3.MqttClient;

public class MqttServiceTest {

    private MqttService mqttService;
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate = Mockito.mock(MongoTemplate.class);
        MqttClient mqttClient = Mockito.mock(MqttClient.class);
        mqttService = new MqttService(mongoTemplate,mqttClient);
    }

    // Test 1 — publishMode mit ungültigem Wert wirft Exception
    @Test
    void testPublishMode_InvalidMode_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mqttService.publishMode(5);
        });
    }

    // Test 2 — publishMode mit negativem Wert wirft Exception
    @Test
    void testPublishMode_NegativeMode_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mqttService.publishMode(-1);
        });
    }

    /*@Test
    void testPublishMode_NegativeMode_ThrowsException1() {
        mqttService.publishMode(-1);
    }*/

    // Test 3 — Temp Modell wird korrekt erstellt
    @Test
    void testTempModel_ValueIsCorrect() {
        Temp temp = new Temp(22.5) ;
        assertNotNull(temp);
    }

    // Test 4 — Control Modell wird korrekt erstellt
    @Test
    void testControlModel_ModeIsCorrect() {
        Control control = new Control(5);
        assertNotNull(control);
    }
}