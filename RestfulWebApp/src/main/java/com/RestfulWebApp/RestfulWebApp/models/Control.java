package com.RestfulWebApp.RestfulWebApp.models;

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
