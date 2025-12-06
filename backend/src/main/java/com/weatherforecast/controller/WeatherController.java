package com.weatherforecast.controller;

import com.weatherforecast.dto.WeatherResponseDTO;
import com.weatherforecast.model.WeatherData;
import com.weatherforecast.scheduled.WeatherDataFetcherService;
import com.weatherforecast.service.LocationService;
import com.weatherforecast.service.TimezoneService;
import com.weatherforecast.service.impl.WeatherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(maxAge = 3600)
public class WeatherController {
    
    @Autowired
    private WeatherServiceImpl weatherService;
    
    @Autowired
    private WeatherDataFetcherService weatherDataFetcherService;
    
    @Autowired
    private LocationService locationService;
    
    @Autowired
    private TimezoneService timezoneService;
    
    // Public endpoint for current weather (no authentication required)
    @GetMapping("/public/current/{cityId}")
    public ResponseEntity<WeatherResponseDTO> getPublicCurrentWeather(@PathVariable String cityId) {
        return weatherService.getCurrentWeather(cityId)
                .map(weatherData -> {
                    WeatherResponseDTO dto = new WeatherResponseDTO(weatherData);
                    // Add timezone information if available
                    locationService.getCityById(cityId).ifPresent(city -> {
                        if (city.getTimezone() != null && !city.getTimezone().isEmpty()) {
                            dto.setTimezone(city.getTimezone());
                        } else {
                            // If city doesn't have timezone, calculate it based on coordinates
                            dto.setTimezone(getTimezoneForCoordinates(city.getLatitude(), city.getLongitude()));
                        }
                    });
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/current/{cityId}")
    public ResponseEntity<WeatherResponseDTO> getCurrentWeather(@PathVariable String cityId) {
        return weatherService.getCurrentWeather(cityId)
                .map(weatherData -> {
                    WeatherResponseDTO dto = new WeatherResponseDTO(weatherData);
                    // Add timezone information if available
                    locationService.getCityById(cityId).ifPresent(city -> {
                        if (city.getTimezone() != null && !city.getTimezone().isEmpty()) {
                            dto.setTimezone(city.getTimezone());
                        } else {
                            // If city doesn't have timezone, calculate it based on coordinates
                            dto.setTimezone(getTimezoneForCoordinates(city.getLatitude(), city.getLongitude()));
                        }
                    });
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Public endpoint for hourly forecast (no authentication required)
    @GetMapping("/public/forecast/hourly/{cityId}")
    public ResponseEntity<List<WeatherResponseDTO>> getPublicHourlyForecast(
            @PathVariable String cityId,
            @RequestParam(defaultValue = "24") int hours) {
        List<WeatherData> weatherDataList = weatherService.getHourlyForecast(cityId, hours);
        List<WeatherResponseDTO> responseList = weatherDataList.stream()
                .map(weatherData -> {
                    WeatherResponseDTO dto = new WeatherResponseDTO(weatherData);
                    // Add timezone information if available
                    locationService.getCityById(cityId).ifPresent(city -> {
                        if (city.getTimezone() != null && !city.getTimezone().isEmpty()) {
                            dto.setTimezone(city.getTimezone());
                        } else {
                            // If city doesn't have timezone, calculate it based on coordinates
                            dto.setTimezone(getTimezoneForCoordinates(city.getLatitude(), city.getLongitude()));
                        }
                    });
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
    
    @GetMapping("/forecast/hourly/{cityId}")
    public ResponseEntity<List<WeatherResponseDTO>> getHourlyForecast(
            @PathVariable String cityId,
            @RequestParam(defaultValue = "24") int hours) {
        List<WeatherData> weatherDataList = weatherService.getHourlyForecast(cityId, hours);
        List<WeatherResponseDTO> responseList = weatherDataList.stream()
                .map(weatherData -> {
                    WeatherResponseDTO dto = new WeatherResponseDTO(weatherData);
                    // Add timezone information if available
                    locationService.getCityById(cityId).ifPresent(city -> {
                        if (city.getTimezone() != null && !city.getTimezone().isEmpty()) {
                            dto.setTimezone(city.getTimezone());
                        } else {
                            // If city doesn't have timezone, calculate it based on coordinates
                            dto.setTimezone(getTimezoneForCoordinates(city.getLatitude(), city.getLongitude()));
                        }
                    });
                    return dto;
                })
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
                .map(weatherData -> {
                    WeatherResponseDTO dto = new WeatherResponseDTO(weatherData);
                    // Add timezone information if available
                    locationService.getCityById(cityId).ifPresent(city -> {
                        if (city.getTimezone() != null && !city.getTimezone().isEmpty()) {
                            dto.setTimezone(city.getTimezone());
                        } else {
                            // If city doesn't have timezone, calculate it based on coordinates
                            dto.setTimezone(getTimezoneForCoordinates(city.getLatitude(), city.getLongitude()));
                        }
                    });
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
    
    @GetMapping("/forecast/daily/{cityId}")
    public ResponseEntity<List<WeatherResponseDTO>> getDailyForecast(
            @PathVariable String cityId,
            @RequestParam(defaultValue = "7") int days) {
        List<WeatherData> weatherDataList = weatherService.getDailyForecast(cityId, days);
        List<WeatherResponseDTO> responseList = weatherDataList.stream()
                .map(weatherData -> {
                    WeatherResponseDTO dto = new WeatherResponseDTO(weatherData);
                    // Add timezone information if available
                    locationService.getCityById(cityId).ifPresent(city -> {
                        if (city.getTimezone() != null && !city.getTimezone().isEmpty()) {
                            dto.setTimezone(city.getTimezone());
                        } else {
                            // If city doesn't have timezone, calculate it based on coordinates
                            dto.setTimezone(getTimezoneForCoordinates(city.getLatitude(), city.getLongitude()));
                        }
                    });
                    return dto;
                })
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
                WeatherResponseDTO dto = new WeatherResponseDTO(weatherData);
                // Set timezone for coordinates
                dto.setTimezone(getTimezoneForCoordinates(lat, lon));
                return ResponseEntity.ok(dto);
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
                    .map(weatherData -> {
                        WeatherResponseDTO dto = new WeatherResponseDTO(weatherData);
                        // Set timezone for coordinates
                        dto.setTimezone(getTimezoneForCoordinates(lat, lon));
                        return dto;
                    })
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
                    .map(weatherData -> {
                        WeatherResponseDTO dto = new WeatherResponseDTO(weatherData);
                        // Set timezone for coordinates
                        dto.setTimezone(getTimezoneForCoordinates(lat, lon));
                        return dto;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            System.err.println("Failed to fetch daily forecast by coordinates: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
    
    // Public endpoint to get current weather for multiple major cities
    @GetMapping("/public/world-map")
    public ResponseEntity<List<WeatherResponseDTO>> getWorldMapWeather() {
        try {
            // Define major cities around the world with their coordinates
            List<WorldCity> majorCities = Arrays.asList(
                new WorldCity("New York", "US", 40.7128, -74.0060),
                new WorldCity("London", "GB", 51.5074, -0.1278),
                new WorldCity("Tokyo", "JP", 35.6762, 139.6503),
                new WorldCity("Sydney", "AU", -33.8688, 151.2093),
                new WorldCity("Moscow", "RU", 55.7558, 37.6173),
                new WorldCity("Rio de Janeiro", "BR", -22.9068, -43.1729),
                new WorldCity("Cairo", "EG", 30.0444, 31.2357),
                new WorldCity("Delhi", "IN", 28.6139, 77.2090),
                new WorldCity("Beijing", "CN", 39.9042, 116.4074),
                new WorldCity("Paris", "FR", 48.8566, 2.3522),
                new WorldCity("Berlin", "DE", 52.5200, 13.4050),
                new WorldCity("Toronto", "CA", 43.6532, -79.3832),
                new WorldCity("Mexico City", "MX", 19.4326, -99.1332),
                new WorldCity("Johannesburg", "ZA", -26.2041, 28.0473),
                new WorldCity("Dubai", "AE", 25.2048, 55.2708)
            );
            
            // Fetch current weather for each city
            List<WeatherResponseDTO> worldWeather = majorCities.stream()
                .map(city -> {
                    try {
                        WeatherData weatherData = weatherService.getCurrentWeatherByCoordinates(city.getLatitude(), city.getLongitude());
                        if (weatherData != null) {
                            WeatherResponseDTO dto = new WeatherResponseDTO(weatherData);
                            dto.setCityName(city.getName() + ", " + city.getCountryCode());
                            // Set timezone for each city
                            dto.setTimezone(getTimezoneForCoordinates(city.getLatitude(), city.getLongitude()));
                            return dto;
                        }
                    } catch (Exception e) {
                        System.err.println("Failed to fetch weather for " + city.getName() + ": " + e.getMessage());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
                
            return ResponseEntity.ok(worldWeather);
        } catch (Exception e) {
            System.err.println("Failed to fetch world map weather: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
    
    // Helper method to get timezone for coordinates
    private String getTimezoneForCoordinates(double latitude, double longitude) {
        return timezoneService.getTimezoneForCoordinates(latitude, longitude);
    }
    
    // Helper class for world cities
    private static class WorldCity {
        private String name;
        private String countryCode;
        private double latitude;
        private double longitude;
        
        public WorldCity(String name, String countryCode, double latitude, double longitude) {
            this.name = name;
            this.countryCode = countryCode;
            this.latitude = latitude;
            this.longitude = longitude;
        }
        
        // Getters
        public String getName() { return name; }
        public String getCountryCode() { return countryCode; }
        public double getLatitude() { return latitude; }
        public double getLongitude() { return longitude; }
    }
}