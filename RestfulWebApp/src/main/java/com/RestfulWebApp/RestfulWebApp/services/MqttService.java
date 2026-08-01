package com.RestfulWebApp.RestfulWebApp.services;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.RestfulWebApp.RestfulWebApp.models.Control;
import com.RestfulWebApp.RestfulWebApp.models.Position;
import com.RestfulWebApp.RestfulWebApp.models.Temperatur;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class MqttService {

    private static final Logger log = LoggerFactory.getLogger(MqttService.class);

    private final ReactiveMongoTemplate mongoTemplateRactive;
    private final MongoTemplate mongoTemplate;

    public MqttService(ReactiveMongoTemplate mongoTemplateRactive, MongoTemplate mongoTemplate) {
        this.mongoTemplateRactive = mongoTemplateRactive;
        this.mongoTemplate = mongoTemplate;
    }

    public Flux<String> liveDifferenz() {
        return mongoTemplateRactive
                .changeStream(Temperatur.class)
                .watchCollection("S7_1500/Temperatur/Differenz")
                .listen()
                .doOnSubscribe(s -> log.info("Change Stream gestartet: Temperatur Differenz"))
                .doOnNext(event -> log.debug("Event empfangen - Differenz: {}", event))
                .map(event -> String.valueOf(event.getRaw()));
    }

    public Flux<String> liveIst() {
        return mongoTemplateRactive
                .changeStream(Temperatur.class)
                .watchCollection("S7_1500/Temperatur/Ist")
                .listen()
                .doOnSubscribe(s -> log.info("Change Stream gestartet: Temperatur Ist"))
                .doOnNext(event -> log.debug("Event empfangen - Ist: {}", event))
                .map(event -> String.valueOf(event.getRaw()));
    }

    public Flux<String> liveSoll() {
        return mongoTemplateRactive
                .changeStream(Temperatur.class)
                .watchCollection("S7_1500/Temperatur/Soll")
                .listen()
                .doOnSubscribe(s -> log.info("Change Stream gestartet: Temperatur Soll"))
                .doOnNext(event -> log.debug("Event empfangen - Soll: {}", event))
                .map(event -> String.valueOf(event.getRaw()));
    }

    public Flux<String> liveStatus() {
        return mongoTemplateRactive
                .changeStream(Temperatur.class)
                .watchCollection("Wago750/Status")
                .listen()
                .doOnSubscribe(s -> log.info("Change Stream gestartet: Wago750 Status"))
                .doOnNext(event -> log.debug("Event empfangen - Status: {}", event))
                .map(event -> String.valueOf(event.getRaw()));
    }

    public Flux<String> liveControl() {
        return mongoTemplateRactive
                .changeStream(Control.class)
                .watchCollection("Wago750/Control")
                .listen()
                .doOnSubscribe(s -> log.info("Change Stream gestartet: Wago750 Control"))
                .doOnNext(event -> log.debug("Event empfangen - Control: {}", event))
                .map(event -> String.valueOf(event.getRaw()));
    }

    public Flux<String> livePosition() {
        return mongoTemplateRactive
                .changeStream(Position.class)
                .watchCollection("GPS")
                .listen()
                .doOnSubscribe(s -> log.info("Change Stream gestartet: GPS Position"))
                .doOnNext(event -> log.debug("Event empfangen - Position: {}", event))
                .map(event -> String.valueOf(event.getRaw()));
    }

    public void publishMode(int mode) {
        throw new UnsupportedOperationException("Unimplemented method 'publishMode'");
    }

    public List<Temperatur> getValues(int choice, Boolean getAll) {
        Query query = getAll == false
                ? new Query().with(Sort.by(Sort.Direction.DESC, "time")).limit(1)
                : new Query();
        List<Temperatur> values = mongoTemplate.find(
                query,
                Temperatur.class,
                choice == 0 ? "S7_1500/Temperatur/Differenz" : (choice == 1 ? "S7_1500/Temperatur/Ist" : "S7_1500/Temperatur/Soll")
        );
        return values;
    }
}