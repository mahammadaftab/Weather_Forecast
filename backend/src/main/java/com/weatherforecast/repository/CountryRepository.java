package com.weatherforecast.repository;

import com.weatherforecast.model.Country;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends MongoRepository<Country, String> {
    Optional<Country> findByCode(String code);
    Optional<Country> findByName(String name);
}