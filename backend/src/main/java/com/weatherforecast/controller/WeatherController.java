package com.weatherforecast.controller;

import com.weatherforecast.dto.WeatherResponseDTO;
import com.weatherforecast.model.WeatherData;
import com.weatherforecast.service.impl.WeatherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WeatherController {
    
    @Autowired
    private WeatherServiceImpl weatherService;
    
    @GetMapping("/current/{cityId}")
    public ResponseEntity<WeatherResponseDTO> getCurrentWeather(@PathVariable String cityId) {
        return weatherService.getCurrentWeather(cityId)
                .map(weatherData -> ResponseEntity.ok(new WeatherResponseDTO(weatherData)))
                .orElse(ResponseEntity.notFound().build());
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
    
    @GetMapping("/alerts/{cityId}")
    public ResponseEntity<?> getWeatherAlerts(@PathVariable String cityId) {
        return ResponseEntity.ok(weatherService.getWeatherAlerts(cityId));
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
}