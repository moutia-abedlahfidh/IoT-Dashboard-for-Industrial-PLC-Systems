package com.seminar1.Seminar1.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.seminar1.Seminar1.models.Control;
import com.seminar1.Seminar1.models.Position;
import com.seminar1.Seminar1.models.Temp;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MqttService {

    private static final Logger log = LoggerFactory.getLogger(MqttService.class);

    private MqttClient client;
    private final MongoTemplate mongoTemplate;
    private MqttCallback mqttCallback;
    String value;
    private Double latestValueTemperaturSoll = null;
    private Double latestValueTemperaturIst = null;
    private Double latestValueTemperaturDifferenz = null;
    private Double latestValueWago750Status = null;
    private Integer latestValueWago750Control = null;

    public MqttService(MongoTemplate mongoTemplate,MqttClient client) {
        this.mongoTemplate = mongoTemplate;
        this.client = client;
        this.mqttCallback = buildCallback();
    }

    public Double getLatestValueTemperaturSoll() { return latestValueTemperaturSoll; }
    public Double getLatestValueTemperaturIst() { return latestValueTemperaturIst; }
    public Double getLatestValueTemperaturDifferenz() { return latestValueTemperaturDifferenz; }
    public Double getLatestValueWago750Status() { return latestValueWago750Status; }

    private double roundToTwoDecimals(String value) {
        return Math.round(Double.parseDouble(value) * 100.0) / 100.0;
    }
    public void simulateMessage(String topic, String payload) throws Exception {
        MqttMessage message = new MqttMessage(payload.getBytes());
        mqttCallback.messageArrived(topic, message);
    }

    public void publishMode(int mode) {
        if (mode < 0 || mode > 3) {
            throw new IllegalArgumentException("Mode must be 0, 1, 2 or 3");
        }

        try {
            String topic = "Wago750/Control";
            MqttMessage message = new MqttMessage(String.valueOf(mode).getBytes());
            message.setQos(0);
            client.publish(topic, message);
            mongoTemplate.save(new Temp(mode), "Wago750/Control");

            log.info("MQTT-Nachricht gesendet -> Topic: '{}', Payload: '{}'", topic, mode);

        } catch (MqttException e) {
            log.error("Fehler beim Senden der MQTT-Nachricht: {}", e.getMessage());
            throw new RuntimeException("Could not publish mode", e);
        }
    }

    private MqttCallback buildCallback() {
        return new MqttCallback() {
 
            @Override
            public void connectionLost(Throwable cause) {
                log.warn("MQTT-Verbindung unterbrochen: {}", cause.getMessage());
            }
 
            @Override
            public void messageArrived(String topic, MqttMessage message) {
                try {
                    switch (topic) {
 
                        case "S7_1500/Temperatur/Soll":
                            String value = new String(message.getPayload());
                            double soll = roundToTwoDecimals(value);
                            if (latestValueTemperaturSoll == null || soll != latestValueTemperaturSoll) {
                                latestValueTemperaturSoll = soll;
                                mongoTemplate.save(new Temp(soll), "S7_1500/Temperatur/Soll");
                                log.info("Gespeichert - Temperatur Soll: {}", soll);
                            }
                            break;
 
                        case "S7_1500/Temperatur/Ist":
                            value = new String(message.getPayload());
                            double ist = roundToTwoDecimals(value);
                            if (latestValueTemperaturIst == null || ist != latestValueTemperaturIst) {
                                latestValueTemperaturIst = ist;
                                mongoTemplate.save(new Temp(ist), "S7_1500/Temperatur/Ist");
                                log.info("Gespeichert - Temperatur Ist: {}", ist);
                            }
                            break;
 
                        case "S7_1500/Temperatur/Differenz":
                            value = new String(message.getPayload());
                            double differenz = roundToTwoDecimals(value);
                            if (latestValueTemperaturDifferenz == null || differenz != latestValueTemperaturDifferenz) {
                                latestValueTemperaturDifferenz = differenz;
                                mongoTemplate.save(new Temp(differenz), "S7_1500/Temperatur/Differenz");
                                log.info("Gespeichert - Temperatur Differenz: {}", differenz);
                            }
                            break;
 
                        case "Wago750/Status":
                            value = new String(message.getPayload());
                            value = value.replace("[", "").replace("]", "");
                            double status = roundToTwoDecimals(value);
                            if (latestValueWago750Status == null || status != latestValueWago750Status) {
                                latestValueWago750Status = status;
                                mongoTemplate.save(new Temp(status), "Wago750/Status");
                                log.info("Gespeichert - Wago750 Status: {}", status);
                            }
                            break;
 
                        case "Wago750/Control":
                            String value_string = new String(message.getPayload());
                            int value_int = Integer.parseInt(value_string);
                            if (latestValueWago750Control == null || value_int != latestValueWago750Control) {
                                latestValueWago750Control = value_int;
                                mongoTemplate.save(new Control(value_int), "Wago750/Control");
                                log.info("Gespeichert - Wago750 Control: {}", value_int);
                            }
                            break;
 
                        case "LocCheck/862315069391414/Data/GNSS":
                            value = new String(message.getPayload());
                            try {
                                ObjectMapper mapper = new ObjectMapper();
                                Position position = mapper.readValue(value, Position.class);
                                mongoTemplate.save(position, "GPS");
                                log.info("Gespeichert - GPS Position");
                            } catch (Exception e) {
                                log.error("Fehler beim Verarbeiten der GPS-Daten: {}", e.getMessage());
                            }
                            break;
 
                        default:
                            log.warn("Unbekanntes MQTT-Topic empfangen: {}", topic);
                    }
                } catch (NumberFormatException e) {
                    log.error("Ungültiger Zahlenwert von Topic '{}': {}", topic, value);
                }
            }
 
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {}
        };
    }

    @PostConstruct
    public void connect() throws MqttException {

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName("abedlahfidh.moutia@stud.hs-bochum.de");
        options.setPassword("018523142".toCharArray());
        options.setCleanSession(true);

        client.setCallback(this.mqttCallback);
        client.connect(options);

        client.subscribe("S7_1500/Temperatur/Soll");
        client.subscribe("S7_1500/Temperatur/Ist");
        client.subscribe("S7_1500/Temperatur/Differenz");
        client.subscribe("LocCheck/862315069391414/Data/GNSS");
        client.subscribe("Wago750/Status");
        client.subscribe("Wago750/Control");

        log.info("MQTT-Microservice gestartet und mit Broker verbunden: tcp://sr-labor.ddns.net:1883");
    }
}