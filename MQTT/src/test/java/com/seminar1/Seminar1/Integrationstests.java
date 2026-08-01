package com.seminar1.Seminar1;

import com.seminar1.Seminar1.services.MqttService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.eclipse.paho.client.mqttv3.MqttClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MqttIntegrationTest {

    @Autowired
    private MqttService mqttService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @MockitoBean
    private MqttClient mqttClient;

    @Test
    void mqttNachrichtWirdInMongoGespeichert() throws Exception {

        // MQTT simulieren
        long countBefore = mongoTemplate.getCollection("S7_1500/Temperatur/Ist").countDocuments();
        mqttService.simulateMessage("S7_1500/Temperatur/Ist", "23.57");

        // Prüfen ob in MongoDB gespeichert wurde
        long countAfter = mongoTemplate.getCollection("S7_1500/Temperatur/Ist").countDocuments();
        assertThat(countAfter).isEqualTo(countBefore+1);
        // ein Beispiel , wenn der Integrationstest nicht erfolgreich durchgeführt ist
        // assertThat(countAfter).isEqualTo(countBefore);

        assertThat(mqttService.getLatestValueTemperaturIst()).isEqualTo(23.57);
    }
}