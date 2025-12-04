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
  pressure: number;
  uvIndex: number;
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
          condition: data.weatherMain,
          icon: data.weatherIcon,
          humidity: data.humidity,
          windSpeed: data.windSpeed,
          pressure: data.pressure,
          uvIndex: data.uvIndex,
          timestamp: data.timestamp
        });
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
      } catch (err) {
        console.error('Failed to fetch initial weather data:', err);
        setError('Failed to fetch initial weather data');
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
    } catch (err) {
      console.error('Failed to connect to WebSocket:', err);
      setError('Failed to connect to weather updates');
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