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
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
    
    @Override
    public Optional<Country> getCountryById(String id) {
        return countryRepository.findById(id);
    }
    
    @Override
    public Optional<Country> getCountryByCode(String code) {
        return countryRepository.findByCode(code);
    }
    
    @Override
    public List<State> getStatesByCountryId(String countryId) {
        return stateRepository.findByCountryId(countryId);
    }
    
    @Override
    public Optional<State> getStateById(String id) {
        return stateRepository.findById(id);
    }
    
    @Override
    public Optional<State> getStateByCode(String code) {
        return stateRepository.findByCode(code);
    }
    
    @Override
    public List<City> getCitiesByCountryId(String countryId) {
        return cityRepository.findByCountryId(countryId);
    }
    
    @Override
    public List<City> getCitiesByStateId(String stateId) {
        return cityRepository.findByStateId(stateId);
    }
    
    @Override
    public List<City> searchCitiesByName(String name) {
        return cityRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Override
    public Optional<City> getCityById(String id) {
        return cityRepository.findById(id);
    }
    
    @Override
    public Optional<City> getCityByName(String name) {
        return cityRepository.findByName(name);
    }
    
    @Override
    public Optional<Country> detectCountryByCoordinates(double latitude, double longitude) {
        // In a real implementation, we would use a reverse geocoding service
        // or a database with coordinate mappings to countries
        // This is a simplified implementation
        return countryRepository.findAll().stream().findFirst();
    }
    
    @Override
    public Optional<State> detectStateByCoordinates(double latitude, double longitude) {
        // In a real implementation, we would use a reverse geocoding service
        // or a database with coordinate mappings to states
        // This is a simplified implementation
        return stateRepository.findAll().stream().findFirst();
    }
    
    @Override
    public Optional<City> detectCityByCoordinates(double latitude, double longitude) {
        // In a real implementation, we would use a reverse geocoding service
        // or a database with coordinate mappings to cities
        // This is a simplified implementation
        return cityRepository.findAll().stream().findFirst();
    }
}