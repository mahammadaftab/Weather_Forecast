import React, { useState, useEffect } from 'react';
import LocationDetector from './components/LocationDetector';
import WeatherCard from './components/WeatherCard';
import HourlyForecast from './components/HourlyForecast';
import DailyForecast from './components/DailyForecast';
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
    // In a real implementation, this would call your search API
    try {
      // This is just a placeholder - you would implement actual search logic
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
        
        {/* Current Weather Card */}
        <WeatherCard 
          temperature={weatherData?.temperature ?? 0}
          feelsLike={weatherData?.feelsLike ?? 0}
          condition={weatherData?.condition ?? "Unknown"}
          icon={weatherData?.icon ?? "❓"}
          humidity={weatherData?.humidity ?? 0}
          windSpeed={weatherData?.windSpeed ?? 0}
          pressure={weatherData?.pressure ?? 0}
          uvIndex={weatherData?.uvIndex ?? 0}
          city={location?.city ?? "Unknown Location"}
          dateTime={weatherData?.timestamp ? new Date(weatherData.timestamp).toLocaleString() : "--"}
        />

        {/* Hourly Forecast */}
        <HourlyForecast 
          forecast={weatherData ? [{
            time: 'Now',
            temperature: weatherData.temperature,
            icon: weatherData.icon
          }] : []}
        />

        {/* 7-Day Forecast */}
        <DailyForecast 
          forecast={weatherData ? [{
            day: 'Today',
            highTemp: weatherData.temperature + 2,
            lowTemp: weatherData.temperature - 2,
            icon: weatherData.icon
          }] : []}
        />
        
        {/* World Map */}
        <WorldMap />
      </div>
    </div>
  );
}

export default App;
