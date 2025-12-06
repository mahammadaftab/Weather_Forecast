package com.weatherforecast.service;

public interface TimezoneService {
    String getTimezoneForCoordinates(double latitude, double longitude);
}