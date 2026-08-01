package com.RestfulWebApp.RestfulWebApp.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "temperature")
public class Temperatur {

    @Id
    private String id;

    private float value;

    @Field("Time")
    private Date time;

    public Temperatur(float value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Temperatur{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", time=" + time +
                '}';
    }
}