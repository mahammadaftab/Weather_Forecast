package com.weatherforecast.model;

import java.time.LocalDateTime;

public class WeatherAlert {
    private String event;
    private String description;
    private String category;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int severity; // 1-10 scale
    
    // Constructors
    public WeatherAlert() {}
    
    public WeatherAlert(String event, String description, String category) {
        this.event = event;
        this.description = description;
        this.category = category;
    }
    
    // Getters and Setters
    public String getEvent() {
        return event;
    }
    
    public void setEvent(String event) {
        this.event = event;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public int getSeverity() {
        return severity;
    }
    
    public void setSeverity(int severity) {
        this.severity = severity;
    }
}