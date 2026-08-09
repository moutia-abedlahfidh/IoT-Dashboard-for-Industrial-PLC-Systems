package com.RestfulWebApp.RestfulWebApp.controller;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.RestfulWebApp.RestfulWebApp.models.Position;
import com.RestfulWebApp.RestfulWebApp.models.Temperatur;
import com.RestfulWebApp.RestfulWebApp.services.MqttService;
import reactor.core.publisher.Flux;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ControllerRest {

    private final MqttService service;
    private static final Logger log = LoggerFactory.getLogger(ControllerRest.class);
    public ControllerRest(MqttService service) {
        this.service = service;
    }

    @PostMapping("/control/{mode}")
    public String publishMode(@PathVariable int mode) {
        service.publishMode(mode);
        log.info("GET /api/livesoll - Live-Stream Temperatur Soll angefordert");
        return "Mode " + mode + " published";
    }

    @GetMapping(value="/livesoll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getLiveSoll() {
        return service.liveSoll() ;
    }

    @GetMapping()
    public String hello(){
        return "Hello From Jenkins" ;
    }

    @GetMapping(value="/livediff", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getLiveDiff() {
        log.info("GET /api/livediff - Live-Stream Temperatur Differenz angefordert");
        return service.liveDifferenz() ;
    }

    @GetMapping(value="/liveist", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getLiveIst() {
        log.info("GET /api/liveist - Live-Stream Temperatur Ist angefordert");
        return service.liveIst() ;
    }

    @GetMapping(value="/livestatus", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getLiveStatus() {
        log.info("GET /api/livestatus - Live-Stream Wago750 Status angefordert");
        return service.liveStatus() ;
    }

    @GetMapping(value="/livecontrol", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getLiveControl() {
        log.info("GET /api/livecontrol - Live-Stream Wago750 Control angefordert");
        return service.liveControl() ;
    }

    @GetMapping(value="/liveposition", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getLivePosition() {
        log.info("GET /api/liveposition - Live-Stream GPS Position angefordert");
        return service.livePosition() ;
    }

    @GetMapping("/temperature/Soll/all")
    public List<Temperatur> getSollAll() {
        log.info("GET /api/temperature/Soll/all - Abruf aller Soll-Temperaturen");
        return service.getValues(2,true);
    }

    @GetMapping("/temperature/lst/all")
    public List<Temperatur> getIstAll() {
        log.info("GET /api/temperature/lst/all - Abruf aller Ist-Temperaturen");
        return service.getValues(1,true);
    }

    @GetMapping("/temperature/differenz/all")
    public List<Temperatur> getDiffAll() {
        log.info("GET /api/temperature/differenz/all - Abruf aller Differenz-Temperaturen");
        return service.getValues(0,true);
    }
    
}

