package com.seminar1.Seminar1.models;

import java.time.LocalDateTime;

public class Control {
    @SuppressWarnings("unused")
    private int mode ;
    private LocalDateTime Time;

    public Control(int mode) {  
        this.mode = mode ;
        this.Time = LocalDateTime.now();
    }
}
