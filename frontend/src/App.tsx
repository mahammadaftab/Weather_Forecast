import React, { useState, useEffect } from 'react';
import LocationDetector from './components/LocationDetector';
import WeatherCard from './components/WeatherCard';
import HourlyForecast, { HourlyForecastItem } from './components/HourlyForecast';
import DailyForecast, { DailyForecastItem } from './components/DailyForecast';
import SearchBar from './components/SearchBar';
import ThemeToggle from './components/ThemeToggle';
import WorldMap from './components/WorldMap';
import useWeatherUpdates from './hooks/useWeatherUpdates';
import { weatherApi } from './services/api';

interface Location {
  latitude: number;
  longitude: number;
  city?: string;
  country?: string;
}

function App() {
  const [location, setLocation] = useState<Location | null>(null);
  const [selectedCityId, setSelectedCityId] = useState<string | null>(null);
  
  const { weatherData, isConnected, error } = useWeatherUpdates(selectedCityId);
  
  const handleLocationDetected = async (detectedLocation: Location) => {
    setLocation(detectedLocation);
    console.log('Location detected:', detectedLocation);
    
    // In a real implementation, this would find the closest city and set selectedCityId
    // For now, we'll just set a default city ID
    setSelectedCityId('default-city-id');
    
    // Fetch weather data for the detected location
    try {
      // The useWeatherUpdates hook will automatically fetch data when selectedCityId changes
      console.log('Fetching weather data for location:', detectedLocation);
    } catch (error) {
      console.error('Failed to fetch weather data:', error);
    }
  };
  
  const handleSearch = async (query: string) => {
    console.log('Search query:', query);
    try {
      console.log('Searching for:', query);
    } catch (error) {
      console.error('Search failed:', error);
    }
  };
  
  const handleResultSelect = async (result: any) => {
    console.log('Selected result:', result);
    setSelectedCityId(result.id);
    
    // Fetch weather data for the selected city
    try {
      // The useWeatherUpdates hook will automatically fetch data when selectedCityId changes
      console.log('Fetching weather data for city:', result.id);
    } catch (error) {
      console.error('Failed to fetch weather data:', error);
    }
  };

  // Generate hourly forecast data
  const generateHourlyForecast = (): HourlyForecastItem[] => {
    if (!weatherData) return [];
    
    const hours: HourlyForecastItem[] = [];
    const now = new Date();
    
    for (let i = 0; i < 24; i++) {
      const time = new Date(now);
      time.setHours(now.getHours() + i);
      
      hours.push({
        time: i === 0 ? 'Now' : time.toLocaleTimeString([], { hour: 'numeric' }),
        temperature: weatherData.temperature + (Math.random() * 4 - 2),
        feelsLike: weatherData.feelsLike + (Math.random() * 4 - 2),
        icon: weatherData.icon,
        precipitation: Math.floor(Math.random() * 30),
        windSpeed: weatherData.windSpeed + (Math.random() * 10 - 5),
        humidity: weatherData.humidity + (Math.random() * 20 - 10)
      });
    }
    
    return hours;
  };
  
  // Generate daily forecast data
  const generateDailyForecast = (): DailyForecastItem[] => {
    if (!weatherData) return [];
    
    const days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    const forecast: DailyForecastItem[] = [];
    
    for (let i = 0; i < 7; i++) {
      const date = new Date();
      date.setDate(date.getDate() + i);
      
      forecast.push({
        day: i === 0 ? 'Today' : days[date.getDay()],
        date: date.toLocaleDateString([], { month: 'short', day: 'numeric' }),
        highTemp: weatherData.temperature + 2 + (Math.random() * 5),
        lowTemp: weatherData.temperature - 2 - (Math.random() * 5),
        icon: weatherData.icon,
        precipitation: Math.floor(Math.random() * 50),
        windSpeed: weatherData.windSpeed + (Math.random() * 15 - 7.5),
        humidity: weatherData.humidity + (Math.random() * 30 - 15),
        uvIndex: Math.floor(Math.random() * 10)
      });
    }
    
    return forecast;
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-400 to-blue-600 p-4 md:p-8">
      <div className="max-w-6xl mx-auto">
        {/* Header */}
        <header className="flex justify-between items-center mb-8">
          <h1 className="text-2xl md:text-3xl font-bold text-white">Weather Forecast</h1>
          <div className="flex items-center space-x-4">
            <ThemeToggle />
            <button className="bg-white/20 backdrop-blur-sm rounded-full p-2 text-white hover:bg-white/30 transition">
              <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
              </svg>
            </button>
          </div>
        </header>

        {/* Location Detector */}
        <LocationDetector onLocationDetected={handleLocationDetected} />
        
        {/* Search Bar */}
        <SearchBar onSearch={handleSearch} onResultSelect={handleResultSelect} />

        {/* Connection Status */}
        {selectedCityId && (
          <div className={`mb-4 p-2 rounded-lg text-center ${isConnected ? 'bg-green-500/20 text-green-200' : 'bg-red-500/20 text-red-200'}`}>
            {isConnected ? 'Live updates connected' : 'Connecting to live updates...'}
          </div>
        )}
        
        {/* Error Message */}
        {error && (
          <div className="mb-4 p-3 rounded-lg bg-red-500/30 text-red-200 border border-red-500/50">
            <div className="flex items-center">
              <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" viewBox="0 0 20 20" fill="currentColor">
                <path fillRule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clipRule="evenodd" />
              </svg>
              <span>{error}</span>
            </div>
            <button 
              onClick={() => window.location.reload()}
              className="mt-2 text-sm underline hover:text-white"
            >
              Refresh Page
            </button>
          </div>
        )}
        
        {/* Current Weather Card */}
        <WeatherCard 
          temperature={weatherData?.temperature ?? 0}
          feelsLike={weatherData?.feelsLike ?? 0}
          condition={weatherData?.condition ?? "Unknown"}
          icon={weatherData?.icon ?? "❓"}
          humidity={weatherData?.humidity ?? 0}
          windSpeed={weatherData?.windSpeed ?? 0}
          windDirection={weatherData?.windDirection ?? 0}
          pressure={weatherData?.pressure ?? 0}
          uvIndex={weatherData?.uvIndex ?? 0}
          visibility={weatherData?.visibility ?? 0}
          clouds={weatherData?.clouds ?? 0}
          sunrise={weatherData?.sunrise ?? ""}
          sunset={weatherData?.sunset ?? ""}
          city={location?.city ?? "Unknown Location"}
          dateTime={weatherData?.timestamp ? new Date(weatherData.timestamp).toLocaleString() : "--"}
        />

        {/* Hourly Forecast */}
        <HourlyForecast 
          forecast={generateHourlyForecast()}
        />

        {/* 7-Day Forecast */}
        <DailyForecast 
          forecast={generateDailyForecast()}
        />
        
        {/* World Map */}
        <WorldMap />
      </div>
    </div>
  );
}

export default App;
