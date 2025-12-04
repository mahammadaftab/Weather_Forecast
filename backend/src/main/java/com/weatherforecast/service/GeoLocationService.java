package com.weatherforecast.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeoLocationService {
    
    @Value("${ipapi.api.key:}")
    private String ipApiKey;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public GeoLocationService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    
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
            location.setRegionName(root.path("regionName").asText());
            location.setCityName(root.path("city").asText());
            
            return location;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch location data from IP geolocation service", e);
        }
    }
    
    public static class GeoLocation {
        private double latitude;
        private double longitude;
        private String countryCode;
        private String regionName;
        private String cityName;
        
        // Getters and setters
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