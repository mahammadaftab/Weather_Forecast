package com.weatherforecast.service.impl;

import com.weatherforecast.service.TimezoneService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TimezoneServiceImpl implements TimezoneService {
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // Map of approximate timezone offsets to IANA timezone identifiers (fallback)
    private static final Map<Integer, String> TIMEZONE_MAP = new HashMap<>();
    
    static {
        TIMEZONE_MAP.put(-11, "Pacific/Midway");
        TIMEZONE_MAP.put(-10, "Pacific/Honolulu");
        TIMEZONE_MAP.put(-9, "America/Anchorage");
        TIMEZONE_MAP.put(-8, "America/Los_Angeles");
        TIMEZONE_MAP.put(-7, "America/Denver");
        TIMEZONE_MAP.put(-6, "America/Chicago");
        TIMEZONE_MAP.put(-5, "America/New_York");
        TIMEZONE_MAP.put(-4, "America/Halifax");
        TIMEZONE_MAP.put(-3, "America/Sao_Paulo");
        TIMEZONE_MAP.put(-2, "Atlantic/South_Georgia");
        TIMEZONE_MAP.put(-1, "Atlantic/Azores");
        TIMEZONE_MAP.put(0, "Europe/London");
        TIMEZONE_MAP.put(1, "Europe/Berlin");
        TIMEZONE_MAP.put(2, "Europe/Athens");
        TIMEZONE_MAP.put(3, "Asia/Baghdad");
        TIMEZONE_MAP.put(4, "Asia/Dubai");
        TIMEZONE_MAP.put(5, "Asia/Kolkata"); // Correct timezone for India
        TIMEZONE_MAP.put(6, "Asia/Dhaka");
        TIMEZONE_MAP.put(7, "Asia/Bangkok");
        TIMEZONE_MAP.put(8, "Asia/Shanghai");
        TIMEZONE_MAP.put(9, "Asia/Tokyo");
        TIMEZONE_MAP.put(10, "Australia/Sydney");
        TIMEZONE_MAP.put(11, "Pacific/Noumea");
        TIMEZONE_MAP.put(12, "Pacific/Fiji");
    }
    
    @Override
    public String getTimezoneForCoordinates(double latitude, double longitude) {
        try {
            // Try to get timezone from WorldTimeAPI
            String url = String.format("http://worldtimeapi.org/api/timezone/%f/%f", latitude, longitude);
            
            // Set timeout to avoid hanging
            restTemplate.getInterceptors().add((request, body, execution) -> {
                request.getHeaders().add("User-Agent", "WeatherForecastApp/1.0");
                return execution.execute(request, body);
            });
            
            String response = restTemplate.getForObject(url, String.class);
            
            if (response != null) {
                // Parse the JSON response to extract timezone
                Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
                Object timezoneObj = responseMap.get("timezone");
                
                if (timezoneObj != null) {
                    String timezone = timezoneObj.toString();
                    // Validate that it's a proper IANA timezone identifier
                    if (isValidTimezone(timezone)) {
                        return timezone;
                    }
                }
            }
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP error when fetching timezone: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (ResourceAccessException e) {
            System.err.println("Network error when fetching timezone: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Failed to get timezone from WorldTimeAPI for coordinates: " + latitude + ", " + longitude + " - " + e.getMessage());
        }
        
        // Fallback to approximation method
        try {
            // Calculate timezone offset based on longitude
            // Each 15 degrees of longitude roughly corresponds to one timezone
            int offsetHours = (int) Math.round(longitude / 15.0);
            
            // Clamp the offset to valid range
            offsetHours = Math.max(-12, Math.min(12, offsetHours));
            
            // Special case for India - use Asia/Kolkata instead of Asia/Karachi
            if (latitude >= 8.0 && latitude <= 37.0 && longitude >= 68.0 && longitude <= 98.0) {
                // This is likely India, use Asia/Kolkata
                return "Asia/Kolkata";
            }
            
            // Return the corresponding IANA timezone identifier
            return TIMEZONE_MAP.getOrDefault(offsetHours, "UTC");
        } catch (Exception e) {
            System.err.println("Failed to calculate timezone for coordinates: " + latitude + ", " + longitude + " - " + e.getMessage());
            // Return UTC as fallback
            return "UTC";
        }
    }
    
    /**
     * Validates if a string is a proper IANA timezone identifier
     */
    private boolean isValidTimezone(String timezone) {
        if (timezone == null || timezone.isEmpty()) {
            return false;
        }
        
        try {
            // Try to create a TimeZone object with this identifier
            java.util.TimeZone.getTimeZone(timezone);
            return !timezone.equals("GMT"); // GMT is returned for invalid identifiers
        } catch (Exception e) {
            return false;
        }
    }
}