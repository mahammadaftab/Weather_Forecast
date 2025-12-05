import { useState, useEffect } from 'react';
import weatherWebSocket from '../services/websocket';
import { weatherApi } from '../services/api';

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
}

const useWeatherUpdates = (cityId: string | null) => {
  const [weatherData, setWeatherData] = useState<WeatherData | null>(null);
  const [isConnected, setIsConnected] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!cityId) return;

    const handleWeatherUpdate = (data: any) => {
      try {
        setWeatherData({
          temperature: data.temperature,
          feelsLike: data.feelsLike,
          condition: data.weatherMain || data.condition,
          icon: data.weatherIcon || data.icon,
          humidity: data.humidity,
          windSpeed: data.windSpeed,
          windDirection: data.windDirection || 0,
          pressure: data.pressure,
          uvIndex: data.uvIndex || 0,
          visibility: data.visibility || 0,
          clouds: data.clouds || 0,
          sunrise: data.sunrise || '',
          sunset: data.sunset || '',
          timestamp: data.timestamp
        });
        // Clear any previous errors when we receive data
        setError(null);
      } catch (err) {
        console.error('Failed to process weather update:', err);
        setError('Failed to process weather update');
      }
    };

    // Fetch initial weather data
    const fetchInitialWeatherData = async () => {
      try {
        const data = await weatherApi.getCurrentWeather(cityId);
        handleWeatherUpdate(data);
      } catch (err: any) {
        console.error('Failed to fetch initial weather data:', err);
        setError(err.message || 'Failed to fetch initial weather data');
      }
    };

    // Connect to WebSocket
    try {
      weatherWebSocket.connect(cityId);
      weatherWebSocket.subscribe(handleWeatherUpdate);
      setIsConnected(true);
      setError(null);
      
      // Fetch initial data
      fetchInitialWeatherData();
    } catch (err: any) {
      console.error('Failed to connect to WebSocket:', err);
      setError(err.message || 'Failed to connect to weather updates');
      setIsConnected(false);
    }

    // Cleanup function
    return () => {
      weatherWebSocket.unsubscribe(handleWeatherUpdate);
      weatherWebSocket.disconnect();
      setIsConnected(false);
    };
  }, [cityId]);

  return { weatherData, isConnected, error };
};

export default useWeatherUpdates;