package com.weatherforecast.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "cities")
public class City {
    @Id
    private String id;
    
    @Indexed
    private String stateId;
    
    @Indexed
    private String countryId;
    
    @Indexed(unique = true)
    private String name;
    
    private String asciiName; // ASCII representation of the city name
    private String alternateNames; // Comma-separated list of alternate names
    private double latitude;
    private double longitude;
    private long population;
    private long elevation; // in meters
    private String timezone;
    private String countryCode; // ISO 3166-1 alpha-2 code
    private String stateCode; // State/province code
    private int importance; // Importance score for search ranking
    private String featureClass; // Geographic feature class
    private String featureCode; // Geographic feature code
    private String admin1Code; // Administrative division code
    private String admin2Code; // Secondary administrative division code
    
    // Constructors
    public City() {}
    
    public City(String stateId, String countryId, String name) {
        this.stateId = stateId;
        this.countryId = countryId;
        this.name = name;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getStateId() {
        return stateId;
    }
    
    public void setStateId(String stateId) {
        this.stateId = stateId;
    }
    
    public String getCountryId() {
        return countryId;
    }
    
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAsciiName() {
        return asciiName;
    }
    
    public void setAsciiName(String asciiName) {
        this.asciiName = asciiName;
    }
    
    public String getAlternateNames() {
        return alternateNames;
    }
    
    public void setAlternateNames(String alternateNames) {
        this.alternateNames = alternateNames;
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
    
    public long getPopulation() {
        return population;
    }
    
    public void setPopulation(long population) {
        this.population = population;
    }
    
    public long getElevation() {
        return elevation;
    }
    
    public void setElevation(long elevation) {
        this.elevation = elevation;
    }
    
    public String getTimezone() {
        return timezone;
    }
    
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
    
    public String getCountryCode() {
        return countryCode;
    }
    
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public String getStateCode() {
        return stateCode;
    }
    
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
    
    public int getImportance() {
        return importance;
    }
    
    public void setImportance(int importance) {
        this.importance = importance;
    }
    
    public String getFeatureClass() {
        return featureClass;
    }
    
    public void setFeatureClass(String featureClass) {
        this.featureClass = featureClass;
    }
    
    public String getFeatureCode() {
        return featureCode;
    }
    
    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }
    
    public String getAdmin1Code() {
        return admin1Code;
    }
    
    public void setAdmin1Code(String admin1Code) {
        this.admin1Code = admin1Code;
    }
    
    public String getAdmin2Code() {
        return admin2Code;
    }
    
    public void setAdmin2Code(String admin2Code) {
        this.admin2Code = admin2Code;
    }
}