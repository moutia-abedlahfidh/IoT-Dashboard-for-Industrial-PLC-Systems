package com.seminar1.Seminar1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeminarTeil1Application {
	private static final Logger log = LoggerFactory.getLogger(SeminarTeil1Application.class);
	public static void main(String[] args) {
		SpringApplication.run(SeminarTeil1Application.class, args);
		log.info("MQTT-Microservice gestartet.");
	}

}
