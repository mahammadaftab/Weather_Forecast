package com.weatherforecast.scheduled;

import com.weatherforecast.config.WeatherWebSocketHandler;
import com.weatherforecast.model.City;
import com.weatherforecast.model.WeatherData;
import com.weatherforecast.repository.CityRepository;
import com.weatherforecast.service.WeatherApiService;
import com.weatherforecast.service.impl.WeatherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Scheduled service to fetch real-time weather data from OpenWeatherMap API
 */
@Service
public class WeatherDataFetcherService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherApiService weatherApiService;

    @Autowired
    private WeatherServiceImpl weatherService;
    
    @Autowired
    private WeatherWebSocketHandler webSocketHandler;

    // Thread pool for concurrent API requests
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * Fetch current weather data for all cities every 10 minutes
     * Cron expression: 0 *\/10 * * * * (every 10 minutes)
     */
    @Scheduled(cron = "0 */10 * * * *")
    public void fetchCurrentWeatherForAllCities() {
        System.out.println("Starting scheduled weather data fetch at " + LocalDateTime.now());

        // Get all cities from the database
        List<City> cities = cityRepository.findAll();

        // Fetch weather data for each city concurrently
        CompletableFuture<?>[] futures = cities.stream()
            .map(city -> CompletableFuture.runAsync(() -> fetchAndStoreWeatherData(city), executorService))
            .toArray(CompletableFuture[]::new);

        // Wait for all requests to complete
        CompletableFuture.allOf(futures).join();

        System.out.println("Completed scheduled weather data fetch for " + cities.size() + " cities");
    }

    /**
     * Fetch and store weather data for a specific city
     */
    private void fetchAndStoreWeatherData(City city) {
        try {
            System.out.println("Fetching weather data for city: " + city.getName());

            // Fetch current weather data from OpenWeatherMap API
            WeatherData weatherData = weatherApiService.getCurrentWeatherByCoordinates(
                city.getLatitude(), 
                city.getLongitude()
            );

            // Set the city ID
            weatherData.setCityId(city.getId());
            weatherData.setTimestamp(LocalDateTime.now());

            // Save the weather data to the database
            weatherService.updateWeatherData(weatherData);
            
            // Broadcast the update via WebSocket
            webSocketHandler.broadcastWeatherUpdate(city.getId(), weatherData);

            System.out.println("Successfully fetched and stored weather data for city: " + city.getName());
        } catch (Exception e) {
            System.err.println("Failed to fetch weather data for city: " + city.getName() + ". Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Fetch weather data for a specific city by name (can be called manually)
     */
    public void fetchWeatherDataForCity(String cityName) {
        cityRepository.findByName(cityName).ifPresent(this::fetchAndStoreWeatherData);
    }

    /**
     * Shutdown the executor service when the application stops
     */
    public void shutdown() {
        executorService.shutdown();
    }
}