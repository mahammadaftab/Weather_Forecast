package com.weatherforecast.controller;

import com.weatherforecast.dto.WeatherResponseDTO;
import com.weatherforecast.model.WeatherData;
import com.weatherforecast.scheduled.WeatherDataFetcherService;
import com.weatherforecast.service.impl.WeatherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(maxAge = 3600)
public class WeatherController {
    
    @Autowired
    private WeatherServiceImpl weatherService;
    
    @Autowired
    private WeatherDataFetcherService weatherDataFetcherService;
    
    // Public endpoint for current weather (no authentication required)
    @GetMapping("/public/current/{cityId}")
    public ResponseEntity<WeatherResponseDTO> getPublicCurrentWeather(@PathVariable String cityId) {
        return weatherService.getCurrentWeather(cityId)
                .map(weatherData -> ResponseEntity.ok(new WeatherResponseDTO(weatherData)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/current/{cityId}")
    public ResponseEntity<WeatherResponseDTO> getCurrentWeather(@PathVariable String cityId) {
        return weatherService.getCurrentWeather(cityId)
                .map(weatherData -> ResponseEntity.ok(new WeatherResponseDTO(weatherData)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Public endpoint for hourly forecast (no authentication required)
    @GetMapping("/public/forecast/hourly/{cityId}")
    public ResponseEntity<List<WeatherResponseDTO>> getPublicHourlyForecast(
            @PathVariable String cityId,
            @RequestParam(defaultValue = "24") int hours) {
        List<WeatherData> weatherDataList = weatherService.getHourlyForecast(cityId, hours);
        List<WeatherResponseDTO> responseList = weatherDataList.stream()
                .map(WeatherResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
    
    @GetMapping("/forecast/hourly/{cityId}")
    public ResponseEntity<List<WeatherResponseDTO>> getHourlyForecast(
            @PathVariable String cityId,
            @RequestParam(defaultValue = "24") int hours) {
        List<WeatherData> weatherDataList = weatherService.getHourlyForecast(cityId, hours);
        List<WeatherResponseDTO> responseList = weatherDataList.stream()
                .map(WeatherResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
    
    // Public endpoint for daily forecast (no authentication required)
    @GetMapping("/public/forecast/daily/{cityId}")
    public ResponseEntity<List<WeatherResponseDTO>> getPublicDailyForecast(
            @PathVariable String cityId,
            @RequestParam(defaultValue = "7") int days) {
        List<WeatherData> weatherDataList = weatherService.getDailyForecast(cityId, days);
        List<WeatherResponseDTO> responseList = weatherDataList.stream()
                .map(WeatherResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
    
    @GetMapping("/forecast/daily/{cityId}")
    public ResponseEntity<List<WeatherResponseDTO>> getDailyForecast(
            @PathVariable String cityId,
            @RequestParam(defaultValue = "7") int days) {
        List<WeatherData> weatherDataList = weatherService.getDailyForecast(cityId, days);
        List<WeatherResponseDTO> responseList = weatherDataList.stream()
                .map(WeatherResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
    
    // Public endpoint for weather alerts (no authentication required)
    @GetMapping("/public/alerts/{cityId}")
    public ResponseEntity<?> getPublicWeatherAlerts(@PathVariable String cityId) {
        return ResponseEntity.ok(weatherService.getWeatherAlerts(cityId));
    }
    
    @GetMapping("/alerts/{cityId}")
    public ResponseEntity<?> getWeatherAlerts(@PathVariable String cityId) {
        return ResponseEntity.ok(weatherService.getWeatherAlerts(cityId));
    }
    
    // Public endpoint for historical weather (no authentication required)
    @GetMapping("/public/historical/{cityId}")
    public ResponseEntity<List<WeatherResponseDTO>> getPublicHistoricalWeather(
            @PathVariable String cityId,
            @RequestParam String start,
            @RequestParam String end) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        List<WeatherData> weatherDataList = weatherService.getHistoricalWeather(cityId, startDate, endDate);
        List<WeatherResponseDTO> responseList = weatherDataList.stream()
                .map(WeatherResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
    
    @GetMapping("/historical/{cityId}")
    public ResponseEntity<List<WeatherResponseDTO>> getHistoricalWeather(
            @PathVariable String cityId,
            @RequestParam String start,
            @RequestParam String end) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        List<WeatherData> weatherDataList = weatherService.getHistoricalWeather(cityId, startDate, endDate);
        List<WeatherResponseDTO> responseList = weatherDataList.stream()
                .map(WeatherResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
    
    // Endpoint to manually trigger weather data fetch for a specific city
    @PostMapping("/fetch/{cityName}")
    public ResponseEntity<String> fetchWeatherDataForCity(@PathVariable String cityName) {
        try {
            weatherDataFetcherService.fetchWeatherDataForCity(cityName);
            return ResponseEntity.ok("Weather data fetch initiated for city: " + cityName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to fetch weather data for city: " + cityName + ". Error: " + e.getMessage());
        }
    }
    
    // New endpoint to get weather data for any location by coordinates
    @GetMapping("/public/current-by-coordinates")
    public ResponseEntity<WeatherResponseDTO> getPublicCurrentWeatherByCoordinates(
            @RequestParam double lat,
            @RequestParam double lon) {
        try {
            // Fetch current weather data from OpenWeatherMap API
            WeatherData weatherData = weatherService.getCurrentWeatherByCoordinates(lat, lon);
            
            if (weatherData != null) {
                return ResponseEntity.ok(new WeatherResponseDTO(weatherData));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch weather data by coordinates: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
    
    // New endpoint to get hourly forecast for any location by coordinates
    @GetMapping("/public/forecast/hourly-by-coordinates")
    public ResponseEntity<List<WeatherResponseDTO>> getPublicHourlyForecastByCoordinates(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "24") int hours) {
        try {
            List<WeatherData> weatherDataList = weatherService.getHourlyForecastByCoordinates(lat, lon, hours);
            List<WeatherResponseDTO> responseList = weatherDataList.stream()
                    .map(WeatherResponseDTO::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            System.err.println("Failed to fetch hourly forecast by coordinates: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
    
    // New endpoint to get daily forecast for any location by coordinates
    @GetMapping("/public/forecast/daily-by-coordinates")
    public ResponseEntity<List<WeatherResponseDTO>> getPublicDailyForecastByCoordinates(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "7") int days) {
        try {
            List<WeatherData> weatherDataList = weatherService.getDailyForecastByCoordinates(lat, lon, days);
            List<WeatherResponseDTO> responseList = weatherDataList.stream()
                    .map(WeatherResponseDTO::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            System.err.println("Failed to fetch daily forecast by coordinates: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}