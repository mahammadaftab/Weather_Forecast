package com.weatherforecast.service;

import com.weatherforecast.model.City;
import com.weatherforecast.model.Country;
import com.weatherforecast.model.State;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LocationService {
    List<Country> getAllCountries();
    Optional<Country> getCountryById(String id);
    Optional<Country> getCountryByCode(String code);
    
    List<State> getStatesByCountryId(String countryId);
    Optional<State> getStateById(String id);
    Optional<State> getStateByCode(String code);
    
    List<City> getCitiesByCountryId(String countryId);
    List<City> getCitiesByStateId(String stateId);
    List<City> searchCitiesByName(String name);
    Optional<City> getCityById(String id);
    Optional<City> getCityByName(String name);
    
    Optional<Country> detectCountryByCoordinates(double latitude, double longitude);
    Optional<State> detectStateByCoordinates(double latitude, double longitude);
    Optional<City> detectCityByCoordinates(double latitude, double longitude);
}