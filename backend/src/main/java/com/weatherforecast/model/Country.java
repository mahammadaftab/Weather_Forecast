package com.weatherforecast.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

@Document(collection = "countries")
public class Country {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String code;
    
    private String name;
    private String continent;
    private List<String> timezones;
    private double latitude;
    private double longitude;
    
    // Constructors
    public Country() {}
    
    public Country(String code, String name, String continent) {
        this.code = code;
        this.name = name;
        this.continent = continent;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
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
    
    public String getContinent() {
        return continent;
    }
    
    public void setContinent(String continent) {
        this.continent = continent;
    }
    
    public List<String> getTimezones() {
        return timezones;
    }
    
    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
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
}