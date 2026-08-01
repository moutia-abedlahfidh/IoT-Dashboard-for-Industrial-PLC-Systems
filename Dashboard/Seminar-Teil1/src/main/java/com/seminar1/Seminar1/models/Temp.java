package com.seminar1.Seminar1.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class Temp {

    @Id
    private String id;
    
    private double value;
    private LocalDateTime Time;

    public Temp(double value) {
        this.value = value ;
        this.Time = LocalDateTime.now();
    }
}