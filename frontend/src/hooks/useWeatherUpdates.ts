import { useState, useEffect } from 'react';
import weatherWebSocket from '../services/websocket';
import { publicWeatherApi } from '../services/api';

interface WeatherData {
  temperature: number;
  feelsLike: number;
  condition: string;
  icon: string;
  humidity: number;
  windSpeed: number;
  windDirection: number;
  pressure: number;
  uvIndex: number;
  visibility: number;
  clouds: number;
  sunrise: string;
  sunset: string;
  timestamp: string;
  cityName?: string;
}

const useWeatherUpdates = (cityId: string | null, latitude?: number, longitude?: number) => {
  const [weatherData, setWeatherData] = useState<WeatherData | null>(null);
  const [isConnected, setIsConnected] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    // If we have coordinates, fetch weather by coordinates
    if (latitude && longitude) {
      const fetchWeatherByCoordinates = async () => {
        try {
          const data = await publicWeatherApi.getCurrentWeatherByCoordinates(latitude, longitude);
          handleWeatherUpdate(data);
        } catch (err: any) {
          console.error('Failed to fetch weather data by coordinates:', err);
          setError(err.message || 'Failed to fetch weather data');
        }
      };
      
      fetchWeatherByCoordinates();
      return;
    }
    
    // If we have a city ID, fetch weather by city ID
    if (cityId) {
      const fetchWeatherByCityId = async () => {
        try {
          const data = await publicWeatherApi.getCurrentWeather(cityId);
          handleWeatherUpdate(data);
        } catch (err: any) {
          console.error('Failed to fetch weather data by city ID:', err);
          setError(err.message || 'Failed to fetch weather data');
        }
      };
      
      fetchWeatherByCityId();
      return;
    }
    
    // Clean up function
    return () => {
      // Any cleanup code if needed
    };
  }, [cityId, latitude, longitude]);

  const handleWeatherUpdate = (data: any) => {
    try {
      // Map the incoming data to our WeatherData interface
      const mappedData: WeatherData = {
        temperature: data.temperature,
        feelsLike: data.feelsLike,
        condition: data.weatherMain || data.condition || 'Unknown',
        icon: data.weatherIcon || data.icon || '❓',
        humidity: data.humidity,
        windSpeed: data.windSpeed,
        windDirection: data.windDirection,
        pressure: data.pressure,
        uvIndex: data.uvIndex || 0,
        visibility: data.visibility,
        clouds: data.clouds,
        sunrise: data.sunrise,
        sunset: data.sunset,
        timestamp: data.timestamp,
        cityName: data.cityName
      };
      
      setWeatherData(mappedData);
      setError(null);
    } catch (err: any) {
      console.error('Failed to process weather data:', err);
      setError('Failed to process weather data');
    }
  };

  return { weatherData, isConnected, error };
};

export default useWeatherUpdates;