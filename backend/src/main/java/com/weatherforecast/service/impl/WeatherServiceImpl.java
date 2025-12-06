package com.weatherforecast.service.impl;

import com.weatherforecast.model.WeatherData;
import com.weatherforecast.model.WeatherAlert;
import com.weatherforecast.repository.WeatherDataRepository;
import com.weatherforecast.service.WeatherApiService;
import com.weatherforecast.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {
    
    @Autowired
    private WeatherDataRepository weatherDataRepository;
    
    @Autowired
    private WeatherApiService weatherApiService;
    
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
        // Start from the beginning of today to ensure we get data starting from today
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = start.plusDays(days);
        List<WeatherData> allData = weatherDataRepository.findByCityIdAndTimestampBetweenOrderByTimestampAsc(cityId, start, end);
        
        // Group by day and select one entry per day (preferably around noon)
        Map<LocalDate, List<WeatherData>> groupedByDay = allData.stream()
            .collect(Collectors.groupingBy(wd -> wd.getTimestamp().toLocalDate()));
        
        List<WeatherData> dailyForecast = new ArrayList<>();
        
        // Sort the dates and take one entry per day
        List<LocalDate> sortedDates = groupedByDay.keySet().stream()
            .sorted()
            .limit(days)
            .collect(Collectors.toList());
        
        for (LocalDate date : sortedDates) {
            List<WeatherData> dayEntries = groupedByDay.get(date);
            if (dayEntries != null && !dayEntries.isEmpty()) {
                // Find the entry closest to noon (12:00) for this day
                WeatherData noonEntry = dayEntries.stream()
                    .min(Comparator.comparing(wd -> Math.abs(wd.getTimestamp().getHour() - 12)))
                    .orElse(dayEntries.get(0));
                dailyForecast.add(noonEntry);
            }
        }
        
        return dailyForecast;
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
    
    // New implementations for fetching weather by coordinates
    @Override
    public WeatherData getCurrentWeatherByCoordinates(double lat, double lon) {
        return weatherApiService.getCurrentWeatherByCoordinates(lat, lon);
    }
    
    @Override
    public List<WeatherData> getHourlyForecastByCoordinates(double lat, double lon, int hours) {
        return weatherApiService.getHourlyForecastByCoordinates(lat, lon, hours);
    }
    
    @Override
    public List<WeatherData> getDailyForecastByCoordinates(double lat, double lon, int days) {
        return weatherApiService.getDailyForecastByCoordinates(lat, lon, days);
    }
}