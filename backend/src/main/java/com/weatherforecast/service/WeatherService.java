package com.weatherforecast.service;

import com.weatherforecast.model.WeatherData;
import com.weatherforecast.model.WeatherAlert;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface WeatherService {
    Optional<WeatherData> getCurrentWeather(String cityId);
    List<WeatherData> getHourlyForecast(String cityId, int hours);
    List<WeatherData> getDailyForecast(String cityId, int days);
    List<WeatherAlert> getWeatherAlerts(String cityId);
    List<WeatherAlert> getAllActiveAlerts();
    WeatherData updateWeatherData(WeatherData weatherData);
    List<WeatherData> getHistoricalWeather(String cityId, LocalDateTime start, LocalDateTime end);
    
    // New methods for fetching weather by coordinates
    WeatherData getCurrentWeatherByCoordinates(double lat, double lon);
    List<WeatherData> getHourlyForecastByCoordinates(double lat, double lon, int hours);
    List<WeatherData> getDailyForecastByCoordinates(double lat, double lon, int days);
}