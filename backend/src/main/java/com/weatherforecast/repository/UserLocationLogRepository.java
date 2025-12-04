package com.weatherforecast.repository;

import com.weatherforecast.model.UserLocationLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLocationLogRepository extends MongoRepository<UserLocationLog, String> {
    List<UserLocationLog> findByUserIdOrderByTimestampDesc(String userId);
    List<UserLocationLog> findByCityId(String cityId);
}