package com.seminar1.Seminar1;

import com.seminar1.Seminar1.services.MqttService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WagoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MqttClient mqttClient;

    @Test
    void postRequestLoestMqttPublishAnWagoSpsAus() throws Exception {
        /*
        //Post Request senden
        mockMvc.perform(post("/wago/control/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Mode 2 published"));
        
        verify(mqttClient).publish(
                eq("Wago750/Control"),
                any(MqttMessage.class)
        );*/
    }
}
