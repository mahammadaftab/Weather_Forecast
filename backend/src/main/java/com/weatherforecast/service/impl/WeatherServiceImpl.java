package com.weatherforecast.service.impl;

import com.weatherforecast.model.WeatherData;
import com.weatherforecast.model.WeatherAlert;
import com.weatherforecast.repository.WeatherDataRepository;
import com.weatherforecast.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService {
    
    @Autowired
    private WeatherDataRepository weatherDataRepository;
    
    @Override
    @Cacheable(value = "currentWeather", key = "#cityId")
    public Optional<WeatherData> getCurrentWeather(String cityId) {
        // Get the most recent weather data for the city
        List<WeatherData> weatherDataList = weatherDataRepository.findByCityIdOrderByTimestampDesc(cityId);
        return weatherDataList.isEmpty() ? Optional.empty() : Optional.of(weatherDataList.get(0));
    }
    
    @Override
    @Cacheable(value = "hourlyForecast", key = "#cityId + ':' + #hours")
    public List<WeatherData> getHourlyForecast(String cityId, int hours) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusHours(hours);
        return weatherDataRepository.findByCityIdAndTimestampBetweenOrderByTimestampAsc(cityId, now, end);
    }
    
    @Override
    @Cacheable(value = "dailyForecast", key = "#cityId + ':' + #days")
    public List<WeatherData> getDailyForecast(String cityId, int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusDays(days);
        return weatherDataRepository.findByCityIdAndTimestampBetweenOrderByTimestampAsc(cityId, now, end);
    }
    
    @Override
    public List<WeatherAlert> getWeatherAlerts(String cityId) {
        Optional<WeatherData> currentWeather = getCurrentWeather(cityId);
        if (currentWeather.isPresent() && currentWeather.get().getAlerts() != null) {
            return currentWeather.get().getAlerts();
        }
        return new ArrayList<>();
    }
    
    @Override
    public List<WeatherAlert> getAllActiveAlerts() {
        // This would retrieve all active alerts across all cities
        // Implementation would depend on how alerts are stored and managed
        return new ArrayList<>();
    }
    
    @Override
    public WeatherData updateWeatherData(WeatherData weatherData) {
        return weatherDataRepository.save(weatherData);
    }
    
    @Override
    public List<WeatherData> getHistoricalWeather(String cityId, LocalDateTime start, LocalDateTime end) {
        return weatherDataRepository.findByCityIdAndTimestampBetweenOrderByTimestampAsc(cityId, start, end);
    }
}