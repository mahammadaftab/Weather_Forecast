package com.weatherforecast.repository;

import com.weatherforecast.model.WeatherData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherDataRepository extends MongoRepository<WeatherData, String> {
    Optional<WeatherData> findByCityIdAndTimestamp(String cityId, LocalDateTime timestamp);
    List<WeatherData> findByCityIdOrderByTimestampDesc(String cityId);
    List<WeatherData> findByCityIdAndTimestampBetweenOrderByTimestampAsc(String cityId, LocalDateTime start, LocalDateTime end);
}