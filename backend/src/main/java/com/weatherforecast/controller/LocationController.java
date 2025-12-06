package com.weatherforecast.controller;

import com.weatherforecast.dto.LocationDTO;
import com.weatherforecast.model.City;
import com.weatherforecast.model.Country;
import com.weatherforecast.model.State;
import com.weatherforecast.service.GeoLocationService;
import com.weatherforecast.service.impl.LocationServiceImpl;
import com.weatherforecast.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/location")
@CrossOrigin(maxAge = 3600)
public class LocationController {
    
    @Autowired
    private LocationServiceImpl locationService;
    
    @Autowired
    private GeoLocationService geoLocationService;
    
    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(locationService.getAllCountries());
    }
    
    @GetMapping("/countries/{countryId}")
    public ResponseEntity<Country> getCountryById(@PathVariable String countryId) {
        // Validate and sanitize input
        String sanitizedCountryId = ValidationUtil.validateAndSanitizeCityId(countryId);
        if (sanitizedCountryId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return locationService.getCountryById(sanitizedCountryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/countries/code/{code}")
    public ResponseEntity<Country> getCountryByCode(@PathVariable String code) {
        // Validate and sanitize input
        String sanitizedCode = ValidationUtil.validateAndSanitizeName(code);
        if (sanitizedCode == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return locationService.getCountryByCode(sanitizedCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/states/country/{countryId}")
    public ResponseEntity<List<State>> getStatesByCountryId(@PathVariable String countryId) {
        // Validate and sanitize input
        String sanitizedCountryId = ValidationUtil.validateAndSanitizeCityId(countryId);
        if (sanitizedCountryId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(locationService.getStatesByCountryId(sanitizedCountryId));
    }
    
    @GetMapping("/states/{stateId}")
    public ResponseEntity<State> getStateById(@PathVariable String stateId) {
        // Validate and sanitize input
        String sanitizedStateId = ValidationUtil.validateAndSanitizeCityId(stateId);
        if (sanitizedStateId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return locationService.getStateById(sanitizedStateId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cities/country/{countryId}")
    public ResponseEntity<List<City>> getCitiesByCountryId(@PathVariable String countryId) {
        // Validate and sanitize input
        String sanitizedCountryId = ValidationUtil.validateAndSanitizeCityId(countryId);
        if (sanitizedCountryId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(locationService.getCitiesByCountryId(sanitizedCountryId));
    }
    
    @GetMapping("/cities/state/{stateId}")
    public ResponseEntity<List<City>> getCitiesByStateId(@PathVariable String stateId) {
        // Validate and sanitize input
        String sanitizedStateId = ValidationUtil.validateAndSanitizeCityId(stateId);
        if (sanitizedStateId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(locationService.getCitiesByStateId(sanitizedStateId));
    }
    
    @GetMapping("/cities/search")
    public ResponseEntity<List<LocationDTO>> searchCities(@RequestParam String name) {
        // Validate and sanitize input
        String sanitizedName = ValidationUtil.validateAndSanitizeName(name);
        if (sanitizedName == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<City> cities = locationService.searchCitiesByName(sanitizedName);
        List<LocationDTO> locationDTOs = cities.stream()
                .map(city -> {
                    // Get country name if available, otherwise use countryCode
                    String countryName = "";
                    if (city.getCountryId() != null && !city.getCountryId().isEmpty()) {
                        countryName = locationService.getCountryById(city.getCountryId())
                                .map(Country::getName)
                                .orElse(city.getCountryCode() != null ? city.getCountryCode() : "");
                    } else if (city.getCountryCode() != null) {
                        countryName = city.getCountryCode();
                    }
                    
                    // Get state name if available, otherwise use stateCode
                    String stateName = "";
                    if (city.getStateId() != null && !city.getStateId().isEmpty()) {
                        stateName = locationService.getStateById(city.getStateId())
                                .map(State::getName)
                                .orElse(city.getStateCode() != null ? city.getStateCode() : "");
                    } else if (city.getStateCode() != null) {
                        stateName = city.getStateCode();
                    }
                    
                    return new LocationDTO(
                            city.getId(),
                            city.getName(),
                            countryName,
                            stateName,
                            city.getLatitude(),
                            city.getLongitude());
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(locationDTOs);
    }
    
    @GetMapping("/cities/{cityId}")
    public ResponseEntity<City> getCityById(@PathVariable String cityId) {
        // Validate and sanitize input
        String sanitizedCityId = ValidationUtil.validateAndSanitizeCityId(cityId);
        if (sanitizedCityId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return locationService.getCityById(sanitizedCityId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // New endpoint to search for any location using external geocoding service
    @GetMapping("/search")
    public ResponseEntity<List<LocationDTO>> searchLocations(@RequestParam String query) {
        try {
            // Use the geolocation service to search for locations
            List<GeoLocationService.GeoLocation> locations = geoLocationService.searchLocations(query);
            
            // Convert to LocationDTO format
            List<LocationDTO> locationDTOs = locations.stream()
                .map(location -> {
                    LocationDTO dto = new LocationDTO();
                    dto.setId(""); // Will be generated when saved
                    dto.setName(location.getCityName() != null ? location.getCityName() : 
                               (location.getRegionName() != null ? location.getRegionName() : "Unknown"));
                    dto.setCountryCode(location.getCountryCode() != null ? location.getCountryCode() : "");
                    dto.setStateCode(location.getRegionName() != null ? location.getRegionName() : "");
                    dto.setLatitude(location.getLatitude());
                    dto.setLongitude(location.getLongitude());
                    return dto;
                })
                .collect(Collectors.toList());
                
            return ResponseEntity.ok(locationDTOs);
        } catch (Exception e) {
            System.err.println("Failed to search locations: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // New endpoint to initialize default cities
    @PostMapping("/cities/init-default")
    public ResponseEntity<String> initializeDefaultCities() {
        try {
            // Create some default cities for testing
            locationService.createDefaultCities();
            return ResponseEntity.ok("Default cities initialized successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to initialize default cities: " + e.getMessage());
        }
    }
}