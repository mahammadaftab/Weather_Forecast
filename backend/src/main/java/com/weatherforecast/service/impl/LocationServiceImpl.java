package com.weatherforecast.service.impl;

import com.weatherforecast.model.City;
import com.weatherforecast.model.Country;
import com.weatherforecast.model.State;
import com.weatherforecast.repository.CityRepository;
import com.weatherforecast.repository.CountryRepository;
import com.weatherforecast.repository.StateRepository;
import com.weatherforecast.service.GeoLocationService;
import com.weatherforecast.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    private StateRepository stateRepository;
    
    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private GeoLocationService geoLocationService;
    
    @Override
    @Cacheable(value = "allCountries", key = "'all'")
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
    
    @Override
    @Cacheable(value = "countryById", key = "#id")
    public Optional<Country> getCountryById(String id) {
        return countryRepository.findById(id);
    }
    
    @Override
    @Cacheable(value = "countryByCode", key = "#code")
    public Optional<Country> getCountryByCode(String code) {
        return countryRepository.findByCode(code);
    }
    
    @Override
    @Cacheable(value = "statesByCountry", key = "#countryId")
    public List<State> getStatesByCountryId(String countryId) {
        return stateRepository.findByCountryId(countryId);
    }
    
    @Override
    @Cacheable(value = "stateById", key = "#id")
    public Optional<State> getStateById(String id) {
        return stateRepository.findById(id);
    }
    
    @Override
    @Cacheable(value = "stateByCode", key = "#code")
    public Optional<State> getStateByCode(String code) {
        return stateRepository.findByCode(code);
    }
    
    @Override
    @Cacheable(value = "citiesByCountry", key = "#countryId")
    public List<City> getCitiesByCountryId(String countryId) {
        return cityRepository.findByCountryId(countryId);
    }
    
    @Override
    @Cacheable(value = "citiesByState", key = "#stateId")
    public List<City> getCitiesByStateId(String stateId) {
        return cityRepository.findByStateId(stateId);
    }
    
    @Override
    @Cacheable(value = "citiesByName", key = "#name")
    public List<City> searchCitiesByName(String name) {
        return cityRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Override
    @Cacheable(value = "searchCities", key = "#searchTerm + ':' + #pageable.pageNumber + ':' + #pageable.pageSize")
    public Page<City> searchCities(String searchTerm, Pageable pageable) {
        return cityRepository.findBySearchTerm(searchTerm, pageable);
    }
    
    @Override
    @Cacheable(value = "cityById", key = "#id")
    public Optional<City> getCityById(String id) {
        return cityRepository.findById(id);
    }
    
    @Override
    @Cacheable(value = "cityByName", key = "#name")
    public Optional<City> getCityByName(String name) {
        return cityRepository.findByName(name);
    }
    
    @Override
    @Cacheable(value = "citiesByCoordinates", key = "#minLat + ':' + #maxLat + ':' + #minLon + ':' + #maxLon")
    public List<City> findCitiesByCoordinates(double minLat, double maxLat, double minLon, double maxLon) {
        return cityRepository.findByCoordinatesWithinBounds(minLat, maxLat, minLon, maxLon);
    }
    
    @Override
    @Cacheable(value = "countryByCoordinates", key = "#latitude + ':' + #longitude")
    public Optional<Country> detectCountryByCoordinates(double latitude, double longitude) {
        GeoLocationService.GeoLocation location = geoLocationService.reverseGeocode(latitude, longitude);
        return countryRepository.findByCode(location.getCountryCode());
    }
    
    @Override
    @Cacheable(value = "stateByCoordinates", key = "#latitude + ':' + #longitude")
    public Optional<State> detectStateByCoordinates(double latitude, double longitude) {
        // In a real implementation, we would use a reverse geocoding service
        // or a database with coordinate mappings to states
        // This is a simplified implementation
        return stateRepository.findAll().stream().findFirst();
    }
    
    @Override
    @Cacheable(value = "cityByCoordinates", key = "#latitude + ':' + #longitude")
    public Optional<City> detectCityByCoordinates(double latitude, double longitude) {
        GeoLocationService.GeoLocation location = geoLocationService.reverseGeocode(latitude, longitude);
        return cityRepository.findByName(location.getCityName());
    }
    
    /**
     * Create default cities for testing purposes
     */
    public void createDefaultCities() {
        // Get the US country
        Optional<Country> usCountryOpt = countryRepository.findByCode("US");
        if (!usCountryOpt.isPresent()) {
            return; // US country not found
        }
        
        Country usCountry = usCountryOpt.get();
        String usCountryId = usCountry.getId();
        
        // Create some major US cities
        createCityIfNotExists("New York", usCountryId, null, 40.7128, -74.0060);
        createCityIfNotExists("Los Angeles", usCountryId, null, 34.0522, -118.2437);
        createCityIfNotExists("Chicago", usCountryId, null, 41.8781, -87.6298);
        createCityIfNotExists("Houston", usCountryId, null, 29.7604, -95.3698);
        createCityIfNotExists("Phoenix", usCountryId, null, 33.4484, -112.0740);
        
        // Get the UK country
        Optional<Country> ukCountryOpt = countryRepository.findByCode("GB");
        if (ukCountryOpt.isPresent()) {
            Country ukCountry = ukCountryOpt.get();
            String ukCountryId = ukCountry.getId();
            createCityIfNotExists("London", ukCountryId, null, 51.5074, -0.1278);
        }
        
        // Get Japan
        Optional<Country> jpCountryOpt = countryRepository.findByCode("JP");
        if (jpCountryOpt.isPresent()) {
            Country jpCountry = jpCountryOpt.get();
            String jpCountryId = jpCountry.getId();
            createCityIfNotExists("Tokyo", jpCountryId, null, 35.6762, 139.6503);
        }
    }
    
    private void createCityIfNotExists(String name, String countryId, String stateId, double latitude, double longitude) {
        // Check if city already exists by name and country
        List<City> existingCities = cityRepository.findByCountryId(countryId);
        boolean cityExists = existingCities.stream()
            .anyMatch(city -> city.getName().equalsIgnoreCase(name));
            
        if (!cityExists) {
            City city = new City();
            city.setName(name);
            city.setCountryId(countryId);
            city.setStateId(stateId);
            city.setLatitude(latitude);
            city.setLongitude(longitude);
            city.setPopulation(1000000L); // Default population
            city.setElevation(0L); // Default elevation
            city.setFeatureCode("PPL"); // Populated place
            city.setImportance(10); // Default importance
            cityRepository.save(city);
        }
    }
}