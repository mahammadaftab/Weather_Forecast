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
    private String code; // ISO 3166-1 alpha-2 code
    
    @Indexed(unique = true)
    private String code3; // ISO 3166-1 alpha-3 code
    
    private int numericCode; // ISO 3166-1 numeric code
    private String name;
    private String continent;
    private String subregion;
    private long population;
    private double area; // in square kilometers
    private List<String> timezones;
    private List<String> currencies;
    private List<String> languages;
    private String capital;
    private double latitude;
    private double longitude;
    private String flagEmoji;
    
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
    
    public String getCode3() {
        return code3;
    }
    
    public void setCode3(String code3) {
        this.code3 = code3;
    }
    
    public int getNumericCode() {
        return numericCode;
    }
    
    public void setNumericCode(int numericCode) {
        this.numericCode = numericCode;
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
    
    public String getSubregion() {
        return subregion;
    }
    
    public void setSubregion(String subregion) {
        this.subregion = subregion;
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
    
    public List<String> getTimezones() {
        return timezones;
    }
    
    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }
    
    public List<String> getCurrencies() {
        return currencies;
    }
    
    public void setCurrencies(List<String> currencies) {
        this.currencies = currencies;
    }
    
    public List<String> getLanguages() {
        return languages;
    }
    
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
    
    public String getCapital() {
        return capital;
    }
    
    public void setCapital(String capital) {
        this.capital = capital;
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
    
    public String getFlagEmoji() {
        return flagEmoji;
    }
    
    public void setFlagEmoji(String flagEmoji) {
        this.flagEmoji = flagEmoji;
    }
}