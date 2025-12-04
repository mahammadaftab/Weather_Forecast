package com.weatherforecast.controller;

import com.weatherforecast.dto.LocationDTO;
import com.weatherforecast.service.GeoLocationService;
import com.weatherforecast.service.impl.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/geolocation")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GeolocationController {
    
    @Autowired
    private GeoLocationService geoLocationService;
    
    @Autowired
    private LocationServiceImpl locationService;
    
    @GetMapping("/detect")
    public ResponseEntity<LocationDTO> detectLocation(HttpServletRequest request) {
        try {
            // Get client IP address
            String ipAddress = getClientIpAddress(request);
            
            // Get location from IP
            GeoLocationService.GeoLocation geoLocation = geoLocationService.getLocationFromIP(ipAddress);
            
            // Create LocationDTO
            LocationDTO locationDTO = new LocationDTO();
            locationDTO.setName(geoLocation.getCityName());
            locationDTO.setCountryCode(geoLocation.getCountryCode());
            locationDTO.setStateCode(geoLocation.getRegionName());
            locationDTO.setLatitude(geoLocation.getLatitude());
            locationDTO.setLongitude(geoLocation.getLongitude());
            
            return ResponseEntity.ok(locationDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        } else {
            // If there are multiple IPs, get the first one
            return xForwardedForHeader.split(",")[0].trim();
        }
    }
}