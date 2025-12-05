package com.weatherforecast.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        
        cacheManager.setCaches(Arrays.asList(
            // Current weather data cache (shorter expiration as it changes frequently)
            new CaffeineCache("currentWeather", Caffeine.newBuilder()
                .maximumSize(5000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build()),
                
            // Hourly forecast cache
            new CaffeineCache("hourlyForecast", Caffeine.newBuilder()
                .maximumSize(3000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build()),
                
            // Daily forecast cache
            new CaffeineCache("dailyForecast", Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build()),
                
            // Location data cache (longer expiration as it rarely changes)
            new CaffeineCache("allCountries", Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build()),
                
            new CaffeineCache("countryById", Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build()),
                
            new CaffeineCache("countryByCode", Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build()),
                
            new CaffeineCache("statesByCountry", Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build()),
                
            new CaffeineCache("stateById", Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build()),
                
            new CaffeineCache("stateByCode", Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build()),
                
            new CaffeineCache("citiesByCountry", Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build()),
                
            new CaffeineCache("citiesByState", Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build()),
                
            new CaffeineCache("cityById", Caffeine.newBuilder()
                .maximumSize(5000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build()),
                
            new CaffeineCache("cityByName", Caffeine.newBuilder()
                .maximumSize(5000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build()),
                
            // Search results cache
            new CaffeineCache("citiesByName", Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build()),
                
            new CaffeineCache("searchCities", Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build()),
                
            // Coordinate-based lookups cache
            new CaffeineCache("citiesByCoordinates", Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build()),
                
            new CaffeineCache("countryByCoordinates", Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build()),
                
            new CaffeineCache("stateByCoordinates", Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build()),
                
            new CaffeineCache("cityByCoordinates", Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build()),
                
            // External API caches
            new CaffeineCache("currentWeatherByCoords", Caffeine.newBuilder()
                .maximumSize(5000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build()),
                
            new CaffeineCache("currentWeatherByCity", Caffeine.newBuilder()
                .maximumSize(5000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build()),
                
            new CaffeineCache("locationFromIP", Caffeine.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build()),
                
            new CaffeineCache("reverseGeocode", Caffeine.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build())
        ));
        
        return cacheManager;
    }
}