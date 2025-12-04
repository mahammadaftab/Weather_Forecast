package com.weatherforecast.dto;

import com.weatherforecast.model.WeatherData;
import com.weatherforecast.model.WeatherAlert;

import java.time.LocalDateTime;
import java.util.List;

public class WeatherResponseDTO {
    private String cityId;
    private String cityName;
    private LocalDateTime timestamp;
    private double temperature;
    private double feelsLike;
    private int pressure;
    private int humidity;
    private double windSpeed;
    private int windDirection;
    private int clouds;
    private double visibility;
    private int aqi;
    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;
    private LocalDateTime sunrise;
    private LocalDateTime sunset;
    private double uvIndex;
    private List<WeatherAlert> alerts;
    
    public WeatherResponseDTO() {}
    
    public WeatherResponseDTO(WeatherData weatherData) {
        this.cityId = weatherData.getCityId();
        this.timestamp = weatherData.getTimestamp();
        this.temperature = weatherData.getTemperature();
        this.feelsLike = weatherData.getFeelsLike();
        this.pressure = weatherData.getPressure();
        this.humidity = weatherData.getHumidity();
        this.windSpeed = weatherData.getWindSpeed();
        this.windDirection = weatherData.getWindDirection();
        this.clouds = weatherData.getClouds();
        this.visibility = weatherData.getVisibility();
        this.aqi = weatherData.getAqi();
        this.weatherMain = weatherData.getWeatherMain();
        this.weatherDescription = weatherData.getWeatherDescription();
        this.weatherIcon = weatherData.getWeatherIcon();
        this.sunrise = weatherData.getSunrise();
        this.sunset = weatherData.getSunset();
        this.uvIndex = weatherData.getUvIndex();
        this.alerts = weatherData.getAlerts();
    }
    
    // Getters and Setters
    public String getCityId() {
        return cityId;
    }
    
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
    
    public String getCityName() {
        return cityName;
    }
    
    public void setCityName(String cityName) {
        this.cityName = cityName;
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