package com.weatherforecast.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "weather_data")
public class WeatherData {
    @Id
    private String id;
    
    @Indexed
    private String cityId;
    
    @Indexed
    private LocalDateTime timestamp;
    
    private double temperature;
    private double feelsLike;
    private int pressure;
    private int humidity;
    private double windSpeed;
    private int windDirection;
    private int clouds;
    private double visibility;
    private int aqi; // Air Quality Index
    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;
    private double rainLastHour;
    private double snowLastHour;
    
    // Sunrise and sunset times
    private LocalDateTime sunrise;
    private LocalDateTime sunset;
    
    // Additional data
    private double uvIndex;
    private List<WeatherAlert> alerts;
    
    // Constructors
    public WeatherData() {}
    
    public WeatherData(String cityId, LocalDateTime timestamp) {
        this.cityId = cityId;
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getCityId() {
        return cityId;
    }
    
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public double getTemperature() {
        return temperature;
    }
    
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    
    public double getFeelsLike() {
        return feelsLike;
    }
    
    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }
    
    public int getPressure() {
        return pressure;
    }
    
    public void setPressure(int pressure) {
        this.pressure = pressure;
    }
    
    public int getHumidity() {
        return humidity;
    }
    
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
    
    public double getWindSpeed() {
        return windSpeed;
    }
    
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
    
    public int getWindDirection() {
        return windDirection;
    }
    
    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }
    
    public int getClouds() {
        return clouds;
    }
    
    public void setClouds(int clouds) {
        this.clouds = clouds;
    }
    
    public double getVisibility() {
        return visibility;
    }
    
    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }
    
    public int getAqi() {
        return aqi;
    }
    
    public void setAqi(int aqi) {
        this.aqi = aqi;
    }
    
    public String getWeatherMain() {
        return weatherMain;
    }
    
    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }
    
    public String getWeatherDescription() {
        return weatherDescription;
    }
    
    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
    
    public String getWeatherIcon() {
        return weatherIcon;
    }
    
    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
    
    public double getRainLastHour() {
        return rainLastHour;
    }
    
    public void setRainLastHour(double rainLastHour) {
        this.rainLastHour = rainLastHour;
    }
    
    public double getSnowLastHour() {
        return snowLastHour;
    }
    
    public void setSnowLastHour(double snowLastHour) {
        this.snowLastHour = snowLastHour;
    }
    
    public LocalDateTime getSunrise() {
        return sunrise;
    }
    
    public void setSunrise(LocalDateTime sunrise) {
        this.sunrise = sunrise;
    }
    
    public LocalDateTime getSunset() {
        return sunset;
    }
    
    public void setSunset(LocalDateTime sunset) {
        this.sunset = sunset;
    }
    
    public double getUvIndex() {
        return uvIndex;
    }
    
    public void setUvIndex(double uvIndex) {
        this.uvIndex = uvIndex;
    }
    
    public List<WeatherAlert> getAlerts() {
        return alerts;
    }
    
    public void setAlerts(List<WeatherAlert> alerts) {
        this.alerts = alerts;
    }
}