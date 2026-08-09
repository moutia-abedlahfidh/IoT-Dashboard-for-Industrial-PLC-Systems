package com.RestfulWebApp.RestfulWebApp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.RestfulWebApp.RestfulWebApp.controller.ControllerRest;

@SpringBootTest
class RestfulWebAppApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
    void helloShouldReturnCorrectMessage() {
        ControllerRest controller = new ControllerRest(null);

        String result = controller.hello();

        assertEquals("Hello From Jenkins", result);
    }

}
