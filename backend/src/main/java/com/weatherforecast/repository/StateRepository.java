package com.weatherforecast.repository;

import com.weatherforecast.model.State;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends MongoRepository<State, String> {
    Optional<State> findByCode(String code);
    Optional<State> findByName(String name);
    List<State> findByCountryId(String countryId);
}