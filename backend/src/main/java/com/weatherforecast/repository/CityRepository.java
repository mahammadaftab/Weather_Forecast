package com.weatherforecast.repository;

import com.weatherforecast.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends MongoRepository<City, String> {
    Optional<City> findByName(String name);
    List<City> findByCountryId(String countryId);
    List<City> findByStateId(String stateId);
    List<City> findByNameContainingIgnoreCase(String name);
}