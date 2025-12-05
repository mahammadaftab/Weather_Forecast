package com.weatherforecast.repository;

import com.weatherforecast.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends MongoRepository<City, String> {
    Optional<City> findByName(String name);
    List<City> findByCountryId(String countryId);
    List<City> findByStateId(String stateId);
    List<City> findByNameContainingIgnoreCase(String name);
    
    // Advanced search methods
    Page<City> findByNameContainingIgnoreCaseOrCountryIdContainingIgnoreCaseOrStateIdContainingIgnoreCase(
        String name, String countryId, String stateId, Pageable pageable);
    
    @Query("{ $or: [ { 'name': { $regex: ?0, $options: 'i' } }, { 'countryId': { $regex: ?0, $options: 'i' } }, { 'stateId': { $regex: ?0, $options: 'i' } } ] }")
    Page<City> findBySearchTerm(String searchTerm, Pageable pageable);
    
    // Find cities by coordinates with a certain radius
    @Query("{ 'latitude': { $gte: ?0, $lte: ?1 }, 'longitude': { $gte: ?2, $lte: ?3 } }")
    List<City> findByCoordinatesWithinBounds(double minLat, double maxLat, double minLon, double maxLon);
}