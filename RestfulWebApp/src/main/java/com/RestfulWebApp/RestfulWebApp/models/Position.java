package com.RestfulWebApp.RestfulWebApp.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class Position {

    @Id
    private String id;

    private double utc;
    private double latitude;
    private double longtitude;
    private double altitude;
    private double speed;

    private String sat;
    private LocalDateTime Time;
    private int fixtime;

    public Position(
            double utc,
            double latitude,
            double longtitude,
            double altitude,
            double speed,
            String sat,
            int fixtime) {

        this.utc = utc;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.altitude = altitude;
        this.speed = speed;
        this.sat = sat;
        this.fixtime = fixtime;
        this.Time = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public double getUtc() {
        return utc;
    }

    public void setUtc(double utc) {
        this.utc = utc;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getSat() {
        return sat;
    }

    public void setSat(String sat) {
        this.sat = sat.replaceAll("[\\r\\n]", "");
    }

    public int getFixtime() {
        return fixtime;
    }

    public void setFixtime(int fixtime) {
        this.fixtime = fixtime;
    }
}