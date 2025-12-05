package com.weatherforecast.controller;

import com.weatherforecast.dto.LocationDTO;
import com.weatherforecast.model.City;
import com.weatherforecast.model.Country;
import com.weatherforecast.model.State;
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
@CrossOrigin(origins = "*", maxAge = 3600)
public class LocationController {
    
    @Autowired
    private LocationServiceImpl locationService;
    
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
        // Validate country code
        if (!ValidationUtil.isValidCountryCode(code)) {
            return ResponseEntity.badRequest().build();
        }
        
        return locationService.getCountryByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/states/{countryId}")
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
                .map(city -> new LocationDTO(
                        city.getId(),
                        city.getName(),
                        city.getCountryId(),
                        city.getStateId(),
                        city.getLatitude(),
                        city.getLongitude()))
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
}