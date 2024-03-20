package com.example.swiftgathering_server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DrawingInfoDTO {
    private double fullWidth;
    private double fullHeight;
    private double x;
    private double y;
    private String event;

    // Getters and setters
    public double getFullWidth() {
        return fullWidth;
    }

    public void setFullWidth(double fullWidth) {
        this.fullWidth = fullWidth;
    }

    public double getFullHeight() {
        return fullHeight;
    }

    public void setFullHeight(double fullHeight) {
        this.fullHeight = fullHeight;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
