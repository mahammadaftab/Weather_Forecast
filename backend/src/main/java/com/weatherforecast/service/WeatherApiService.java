package com.weatherforecast.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherforecast.model.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class WeatherApiService {
    
    @Value("${openweathermap.api.key}")
    private String apiKey;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public WeatherApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    
    public WeatherData getCurrentWeatherByCoordinates(double lat, double lon) {
        String url = String.format(
            "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s&units=metric",
            lat, lon, apiKey);
            
        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(jsonResponse);
            
            WeatherData weatherData = new WeatherData();
            weatherData.setTimestamp(LocalDateTime.now());
            
            // Parse basic weather data
            weatherData.setTemperature(root.path("main").path("temp").asDouble());
            weatherData.setFeelsLike(root.path("main").path("feels_like").asDouble());
            weatherData.setPressure(root.path("main").path("pressure").asInt());
            weatherData.setHumidity(root.path("main").path("humidity").asInt());
            
            // Parse wind data
            weatherData.setWindSpeed(root.path("wind").path("speed").asDouble());
            weatherData.setWindDirection(root.path("wind").path("deg").asInt());
            
            // Parse cloud data
            weatherData.setClouds(root.path("clouds").path("all").asInt());
            
            // Parse visibility
            weatherData.setVisibility(root.path("visibility").asDouble() / 1000.0); // Convert to km
            
            // Parse weather description
            JsonNode weatherArray = root.path("weather");
            if (weatherArray.isArray() && weatherArray.size() > 0) {
                JsonNode weather = weatherArray.get(0);
                weatherData.setWeatherMain(weather.path("main").asText());
                weatherData.setWeatherDescription(weather.path("description").asText());
                weatherData.setWeatherIcon(weather.path("icon").asText());
            }
            
            // Parse sunrise and sunset
            long sunriseTimestamp = root.path("sys").path("sunrise").asLong();
            long sunsetTimestamp = root.path("sys").path("sunset").asLong();
            weatherData.setSunrise(LocalDateTime.ofEpochSecond(sunriseTimestamp, 0, java.time.ZoneOffset.UTC));
            weatherData.setSunset(LocalDateTime.ofEpochSecond(sunsetTimestamp, 0, java.time.ZoneOffset.UTC));
            
            return weatherData;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch weather data from OpenWeatherMap API", e);
        }
    }
    
    public WeatherData getCurrentWeatherByCityName(String cityName) {
        String url = String.format(
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
            cityName, apiKey);
            
        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(jsonResponse);
            
            WeatherData weatherData = new WeatherData();
            weatherData.setTimestamp(LocalDateTime.now());
            
            // Parse basic weather data
            weatherData.setTemperature(root.path("main").path("temp").asDouble());
            weatherData.setFeelsLike(root.path("main").path("feels_like").asDouble());
            weatherData.setPressure(root.path("main").path("pressure").asInt());
            weatherData.setHumidity(root.path("main").path("humidity").asInt());
            
            // Parse wind data
            weatherData.setWindSpeed(root.path("wind").path("speed").asDouble());
            weatherData.setWindDirection(root.path("wind").path("deg").asInt());
            
            // Parse cloud data
            weatherData.setClouds(root.path("clouds").path("all").asInt());
            
            // Parse visibility
            weatherData.setVisibility(root.path("visibility").asDouble() / 1000.0); // Convert to km
            
            // Parse weather description
            JsonNode weatherArray = root.path("weather");
            if (weatherArray.isArray() && weatherArray.size() > 0) {
                JsonNode weather = weatherArray.get(0);
                weatherData.setWeatherMain(weather.path("main").asText());
                weatherData.setWeatherDescription(weather.path("description").asText());
                weatherData.setWeatherIcon(weather.path("icon").asText());
            }
            
            // Parse sunrise and sunset
            long sunriseTimestamp = root.path("sys").path("sunrise").asLong();
            long sunsetTimestamp = root.path("sys").path("sunset").asLong();
            weatherData.setSunrise(LocalDateTime.ofEpochSecond(sunriseTimestamp, 0, java.time.ZoneOffset.UTC));
            weatherData.setSunset(LocalDateTime.ofEpochSecond(sunsetTimestamp, 0, java.time.ZoneOffset.UTC));
            
            return weatherData;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch weather data from OpenWeatherMap API", e);
        }
    }
}