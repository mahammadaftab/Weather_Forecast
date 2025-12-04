package com.weatherforecast.controller;

import com.weatherforecast.dto.LocationDTO;
import com.weatherforecast.model.City;
import com.weatherforecast.model.Country;
import com.weatherforecast.model.State;
import com.weatherforecast.service.impl.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
        return locationService.getCountryById(countryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/countries/code/{code}")
    public ResponseEntity<Country> getCountryByCode(@PathVariable String code) {
        return locationService.getCountryByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/states/{countryId}")
    public ResponseEntity<List<State>> getStatesByCountryId(@PathVariable String countryId) {
        return ResponseEntity.ok(locationService.getStatesByCountryId(countryId));
    }
    
    @GetMapping("/states/{stateId}")
    public ResponseEntity<State> getStateById(@PathVariable String stateId) {
        return locationService.getStateById(stateId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cities/country/{countryId}")
    public ResponseEntity<List<City>> getCitiesByCountryId(@PathVariable String countryId) {
        return ResponseEntity.ok(locationService.getCitiesByCountryId(countryId));
    }
    
    @GetMapping("/cities/state/{stateId}")
    public ResponseEntity<List<City>> getCitiesByStateId(@PathVariable String stateId) {
        return ResponseEntity.ok(locationService.getCitiesByStateId(stateId));
    }
    
    @GetMapping("/cities/search")
    public ResponseEntity<List<LocationDTO>> searchCities(@RequestParam String name) {
        List<City> cities = locationService.searchCitiesByName(name);
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
        return locationService.getCityById(cityId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}