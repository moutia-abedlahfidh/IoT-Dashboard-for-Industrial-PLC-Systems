package com.RestfulWebApp.RestfulWebApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.RestfulWebApp.RestfulWebApp.services.MqttService;

@SpringBootApplication
public class RestfulWebAppApplication {
	MqttService service  ;
	private static final Logger log = LoggerFactory.getLogger(RestfulWebAppApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(RestfulWebAppApplication.class, args);
		log.info("REST-Microservice gestartet.");
	}

}
