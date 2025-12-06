package com.weatherforecast.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeoLocationService {
    
    @Value("${ipapi.api.key:}")
    private String ipApiKey;
    
    @Value("${openweathermap.api.key}")
    private String openWeatherMapApiKey;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public GeoLocationService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    
    @Cacheable(value = "locationFromIP", key = "#ipAddress", unless = "#result == null")
    public GeoLocation getLocationFromIP(String ipAddress) {
        try {
            String url = ipApiKey != null && !ipApiKey.isEmpty() 
                ? String.format("http://api.ipapi.com/api/%s?access_key=%s", ipAddress, ipApiKey)
                : String.format("http://ip-api.com/json/%s", ipAddress);
                
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(jsonResponse);
            
            GeoLocation location = new GeoLocation();
            location.setLatitude(root.path("lat").asDouble());
            location.setLongitude(root.path("lon").asDouble());
            location.setCountryCode(root.path("countryCode").asText());
            location.setRegionName(root.path("regionName").asText(""));
            location.setCityName(root.path("city").asText());
            
            return location;
        } catch (ResourceAccessException e) {
            System.err.println("Failed to reach IP geolocation service: " + e.getMessage());
            // Fallback to default location
            GeoLocation location = new GeoLocation();
            location.setLatitude(40.7128);
            location.setLongitude(-74.0060);
            location.setCountryCode("US");
            location.setRegionName("New York");
            location.setCityName("New York City");
            return location;
        } catch (Exception e) {
            System.err.println("Failed to get location from IP: " + e.getMessage());
            // Fallback to default location
            GeoLocation location = new GeoLocation();
            location.setLatitude(40.7128);
            location.setLongitude(-74.0060);
            location.setCountryCode("US");
            location.setRegionName("New York");
            location.setCityName("New York City");
            return location;
        }
    }
    
    /**
     * Reverse geocode coordinates to get location information
     */
    @Cacheable(value = "reverseGeocode", key = "#latitude + ':' + #longitude", unless = "#result == null")
    public GeoLocation reverseGeocode(double latitude, double longitude) {
        try {
            // Use OpenWeatherMap reverse geocoding API
            String url = String.format(
                "http://api.openweathermap.org/geo/1.0/reverse?lat=%f&lon=%f&limit=1&appid=%s",
                latitude, longitude, openWeatherMapApiKey);
                
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(jsonResponse);
            
            if (root.isArray() && root.size() > 0) {
                JsonNode locationNode = root.get(0);
                
                GeoLocation location = new GeoLocation();
                location.setLatitude(locationNode.path("lat").asDouble());
                location.setLongitude(locationNode.path("lon").asDouble());
                location.setCountryCode(locationNode.path("country").asText());
                location.setRegionName(locationNode.path("state").asText(""));
                location.setCityName(locationNode.path("name").asText());
                
                return location;
            }
            
            // Fallback to the coordinates if reverse geocoding fails
            GeoLocation location = new GeoLocation();
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            return location;
        } catch (ResourceAccessException e) {
            System.err.println("Failed to reach reverse geocoding service: " + e.getMessage());
            // Fallback to the coordinates if reverse geocoding service is unreachable
            GeoLocation location = new GeoLocation();
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            return location;
        } catch (Exception e) {
            System.err.println("Failed to perform reverse geocoding: " + e.getMessage());
            // Fallback to the coordinates if reverse geocoding fails
            GeoLocation location = new GeoLocation();
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            return location;
        }
    }
    
    /**
     * Search for locations by name using OpenWeatherMap geocoding API
     */
    public List<GeoLocation> searchLocations(String query) {
        List<GeoLocation> locations = new ArrayList<>();
        try {
            // Use OpenWeatherMap direct geocoding API
            String url = String.format(
                "http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=5&appid=%s",
                query, openWeatherMapApiKey);
                
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(jsonResponse);
            
            if (root.isArray()) {
                for (JsonNode locationNode : root) {
                    GeoLocation location = new GeoLocation();
                    location.setLatitude(locationNode.path("lat").asDouble());
                    location.setLongitude(locationNode.path("lon").asDouble());
                    location.setCountryCode(locationNode.path("country").asText());
                    location.setRegionName(locationNode.path("state").asText(""));
                    location.setCityName(locationNode.path("name").asText());
                    locations.add(location);
                }
            }
            
            return locations;
        } catch (ResourceAccessException e) {
            System.err.println("Failed to reach geocoding service: " + e.getMessage());
            return locations;
        } catch (Exception e) {
            System.err.println("Failed to search locations: " + e.getMessage());
            return locations;
        }
    }
    
    public static class GeoLocation {
        private double latitude;
        private double longitude;
        private String countryCode;
        private String regionName;
        private String cityName;
        
        // Constructors
        public GeoLocation() {}
        
        public GeoLocation(double latitude, double longitude, String countryCode, String regionName, String cityName) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.countryCode = countryCode;
            this.regionName = regionName;
            this.cityName = cityName;
        }
        
        // Getters and Setters
        public double getLatitude() {
            return latitude;
        }
        
        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
        
        public double getLongitude() {
            return longitude;
        }
        
        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
        
        public String getCountryCode() {
            return countryCode;
        }
        
        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }
        
        public String getRegionName() {
            return regionName;
        }
        
        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }
        
        public String getCityName() {
            return cityName;
        }
        
        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
    }
}