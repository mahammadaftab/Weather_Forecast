package com.weatherforecast.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "states")
public class State {
    @Id
    private String id;
    
    @Indexed
    private String countryId;
    
    @Indexed(unique = true)
    private String code;
    
    private String name;
    private String type; // State, Province, Region, etc.
    private String capital;
    private long population;
    private double area; // in square kilometers
    private double latitude;
    private double longitude;
    private String timezone;
    
    // Constructors
    public State() {}
    
    public State(String countryId, String code, String name) {
        this.countryId = countryId;
        this.code = code;
        this.name = name;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getCountryId() {
        return countryId;
    }
    
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getCapital() {
        return capital;
    }
    
    public void setCapital(String capital) {
        this.capital = capital;
    }
    
    public long getPopulation() {
        return population;
    }
    
    public void setPopulation(long population) {
        this.population = population;
    }
    
    public double getArea() {
        return area;
    }
    
    public void setArea(double area) {
        this.area = area;
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public String getTimezone() {
        return timezone;
    }
    
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}