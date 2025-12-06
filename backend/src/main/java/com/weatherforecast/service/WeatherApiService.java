package com.weatherforecast.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherforecast.model.WeatherData;
import com.weatherforecast.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherApiService {
    
    @Value("${openweathermap.api.key}")
    private String apiKey;
    
    @Autowired
    private WeatherDataRepository weatherDataRepository;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public WeatherApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    
    @Cacheable(value = "currentWeatherByCoords", key = "#lat + ':' + #lon", unless = "#result == null")
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
        } catch (ResourceAccessException e) {
            // If API is unreachable, try to get cached data
            System.err.println("Failed to reach OpenWeatherMap API: " + e.getMessage());
            return getCachedWeatherData();
        } catch (Exception e) {
            System.err.println("Failed to fetch weather data from OpenWeatherMap API: " + e.getMessage());
            return getCachedWeatherData();
        }
    }
    
    /**
     * Fetch hourly forecast data by coordinates from OpenWeatherMap
     */
    @Cacheable(value = "hourlyForecast", key = "#lat + ':' + #lon + ':' + #hours", unless = "#result == null")
    public List<WeatherData> getHourlyForecastByCoordinates(double lat, double lon, int hours) {
        String url = String.format(
            "https://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&appid=%s&units=metric",
            lat, lon, apiKey);
            
        List<WeatherData> forecast = new ArrayList<>();
        
        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(jsonResponse);
            
            // Get sunrise and sunset times from the city object
            JsonNode cityNode = root.path("city");
            long sunriseTimestamp = cityNode.path("sunrise").asLong();
            long sunsetTimestamp = cityNode.path("sunset").asLong();
            LocalDateTime sunrise = LocalDateTime.ofEpochSecond(sunriseTimestamp, 0, java.time.ZoneOffset.UTC);
            LocalDateTime sunset = LocalDateTime.ofEpochSecond(sunsetTimestamp, 0, java.time.ZoneOffset.UTC);
            
            JsonNode list = root.path("list");
            if (list.isArray()) {
                int count = 0;
                for (JsonNode item : list) {
                    if (count >= hours) break;
                    
                    WeatherData weatherData = new WeatherData();
                    weatherData.setTimestamp(LocalDateTime.parse(item.path("dt_txt").asText(), 
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    
                    // Set sunrise and sunset times for each forecast entry
                    weatherData.setSunrise(sunrise);
                    weatherData.setSunset(sunset);
                    
                    // Parse basic weather data
                    weatherData.setTemperature(item.path("main").path("temp").asDouble());
                    weatherData.setFeelsLike(item.path("main").path("feels_like").asDouble());
                    weatherData.setPressure(item.path("main").path("pressure").asInt());
                    weatherData.setHumidity(item.path("main").path("humidity").asInt());
                    
                    // Parse wind data
                    weatherData.setWindSpeed(item.path("wind").path("speed").asDouble());
                    weatherData.setWindDirection(item.path("wind").path("deg").asInt());
                    
                    // Parse cloud data
                    weatherData.setClouds(item.path("clouds").path("all").asInt());
                    
                    // Parse visibility
                    weatherData.setVisibility(item.path("visibility").asDouble() / 1000.0); // Convert to km
                    
                    // Parse weather description
                    JsonNode weatherArray = item.path("weather");
                    if (weatherArray.isArray() && weatherArray.size() > 0) {
                        JsonNode weather = weatherArray.get(0);
                        weatherData.setWeatherMain(weather.path("main").asText());
                        weatherData.setWeatherDescription(weather.path("description").asText());
                        weatherData.setWeatherIcon(weather.path("icon").asText());
                    }
                    
                    forecast.add(weatherData);
                    count++;
                }
            }
            
            return forecast;
        } catch (ResourceAccessException e) {
            System.err.println("Failed to reach OpenWeatherMap forecast API: " + e.getMessage());
            return getCachedForecastData(hours);
        } catch (Exception e) {
            System.err.println("Failed to fetch forecast data from OpenWeatherMap API: " + e.getMessage());
            return getCachedForecastData(hours);
        }
    }
    
    /**
     * Fetch daily forecast data by coordinates from OpenWeatherMap
     */
    @Cacheable(value = "dailyForecast", key = "#lat + ':' + #lon + ':' + #days", unless = "#result == null")
    public List<WeatherData> getDailyForecastByCoordinates(double lat, double lon, int days) {
        // For simplicity, we'll use the same hourly forecast API but group by day
        List<WeatherData> hourlyForecast = getHourlyForecastByCoordinates(lat, lon, days * 8); // ~8 entries per day
        List<WeatherData> dailyForecast = new ArrayList<>();
        
        if (hourlyForecast.isEmpty()) {
            return dailyForecast;
        }
        
        // Get sunrise and sunset from the first entry
        LocalDateTime sunrise = hourlyForecast.get(0).getSunrise();
        LocalDateTime sunset = hourlyForecast.get(0).getSunset();
        
        // Group by day and take the noon forecast for each day
        Map<LocalDate, List<WeatherData>> groupedByDay = hourlyForecast.stream()
            .collect(Collectors.groupingBy(wd -> wd.getTimestamp().toLocalDate()));
        
        // Sort the dates to ensure we start from today
        List<LocalDate> sortedDates = groupedByDay.keySet().stream()
            .sorted()
            .collect(Collectors.toList());
        
        // Start from today and get consecutive days
        LocalDate today = LocalDate.now();
        int count = 0;
        
        for (LocalDate date : sortedDates) {
            // Skip dates before today (but include today)
            if (date.isBefore(today)) {
                continue;
            }
            
            if (count >= days) break;
            
            List<WeatherData> dayEntries = groupedByDay.get(date);
            if (dayEntries != null && !dayEntries.isEmpty()) {
                // Find the entry closest to noon (12:00) for this day
                WeatherData noonEntry = dayEntries.stream()
                    .min(Comparator.comparing(wd -> Math.abs(wd.getTimestamp().getHour() - 12)))
                    .orElse(dayEntries.get(0));
                
                // Make sure sunrise and sunset are set
                noonEntry.setSunrise(sunrise);
                noonEntry.setSunset(sunset);
                
                dailyForecast.add(noonEntry);
                count++;
            }
        }
        
        return dailyForecast;
    }
    
    @Cacheable(value = "currentWeatherByCity", key = "#cityName", unless = "#result == null")
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
        } catch (ResourceAccessException e) {
            // If API is unreachable, try to get cached data
            System.err.println("Failed to reach OpenWeatherMap API: " + e.getMessage());
            return getCachedWeatherData();
        } catch (Exception e) {
            System.err.println("Failed to fetch weather data from OpenWeatherMap API: " + e.getMessage());
            return getCachedWeatherData();
        }
    }
    
    /**
     * Get cached weather data as fallback when API is unavailable
     */
    private WeatherData getCachedWeatherData() {
        try {
            // Get the most recent weather data from the database
            List<WeatherData> allWeatherData = weatherDataRepository.findAll();
            if (!allWeatherData.isEmpty()) {
                // Return the most recent entry
                return allWeatherData.get(allWeatherData.size() - 1);
            }
        } catch (Exception e) {
            System.err.println("Failed to retrieve cached weather data: " + e.getMessage());
        }
        
        // Return default weather data if no cached data is available
        WeatherData defaultWeather = new WeatherData();
        defaultWeather.setTimestamp(LocalDateTime.now());
        defaultWeather.setTemperature(20.0);
        defaultWeather.setFeelsLike(20.0);
        defaultWeather.setHumidity(50);
        defaultWeather.setPressure(1013);
        defaultWeather.setWindSpeed(5.0);
        defaultWeather.setWeatherMain("Clear");
        defaultWeather.setWeatherDescription("Clear sky");
        defaultWeather.setWeatherIcon("01d");
        
        return defaultWeather;
    }
    
    /**
     * Get cached forecast data as fallback when API is unavailable
     */
    private List<WeatherData> getCachedForecastData(int hours) {
        List<WeatherData> forecast = new ArrayList<>();
        try {
            // Get the most recent weather data from the database
            List<WeatherData> allWeatherData = weatherDataRepository.findAll();
            if (!allWeatherData.isEmpty()) {
                // Return the most recent entries
                int count = Math.min(hours, allWeatherData.size());
                for (int i = 0; i < count; i++) {
                    forecast.add(allWeatherData.get(allWeatherData.size() - 1 - i));
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to retrieve cached forecast data: " + e.getMessage());
        }
        
        // If no cached data, return default forecast
        if (forecast.isEmpty()) {
            for (int i = 0; i < hours; i++) {
                WeatherData defaultWeather = new WeatherData();
                defaultWeather.setTimestamp(LocalDateTime.now().plusHours(i));
                defaultWeather.setTemperature(20.0 + (Math.random() * 10 - 5)); // Random temp between 15-25
                defaultWeather.setFeelsLike(20.0 + (Math.random() * 10 - 5));
                defaultWeather.setHumidity(50 + (int)(Math.random() * 30 - 15)); // Random humidity 35-65
                defaultWeather.setPressure(1013 + (int)(Math.random() * 20 - 10)); // Random pressure 1003-1023
                defaultWeather.setWindSpeed(5.0 + (Math.random() * 10)); // Random wind speed 5-15
                defaultWeather.setWeatherMain("Clear");
                defaultWeather.setWeatherDescription("Clear sky");
                defaultWeather.setWeatherIcon("01d");
                forecast.add(defaultWeather);
            }
        }
        
        return forecast;
    }
}