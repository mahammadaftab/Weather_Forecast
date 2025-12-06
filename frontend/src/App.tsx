import React, { useState, useEffect } from 'react';
import LocationDetector from './components/LocationDetector';
import WeatherCard from './components/WeatherCard';
import HourlyForecast from './components/HourlyForecast';
import DailyForecast from './components/DailyForecast';
import SearchBar from './components/SearchBar';
import ThemeToggle from './components/ThemeToggle';
import WorldMap from './components/WorldMap';
import useWeatherUpdates from './hooks/useWeatherUpdates';
import { publicWeatherApi } from './services/api';

interface Location {
  latitude: number;
  longitude: number;
  city?: string;
  country?: string;
}

function App() {
  const [location, setLocation] = useState<Location | null>(null);
  const [selectedCityId, setSelectedCityId] = useState<string | null>(null);
  const [fallbackCityId, setFallbackCityId] = useState<string | null>(null);
  const [searchCoordinates, setSearchCoordinates] = useState<{lat: number, lon: number} | null>(null);
  
  // Use either the selected city ID or the fallback city ID
  const effectiveCityId = selectedCityId || fallbackCityId;
  const { weatherData, isConnected, error } = useWeatherUpdates(
    effectiveCityId, 
    searchCoordinates?.lat, 
    searchCoordinates?.lon
  );

  // Set a default city when the app loads
  useEffect(() => {
    if (!selectedCityId && !fallbackCityId && !searchCoordinates) {
      // Fallback to hardcoded New York city ID
      setFallbackCityId('693421ea78be066b83653876');
    }
  }, [selectedCityId, fallbackCityId, searchCoordinates]);

  const handleLocationDetected = async (detectedLocation: Location) => {
    setLocation(detectedLocation);
    console.log('Location detected:', detectedLocation);
    
    // Try to find the closest city in our database based on coordinates
    if (detectedLocation.latitude && detectedLocation.longitude) {
      try {
        // In a real implementation, we would call an API to find the closest city
        // For now, we'll just log the coordinates
        console.log(`Searching for city near ${detectedLocation.latitude}, ${detectedLocation.longitude}`);
      } catch (error) {
        console.error('Failed to find city by coordinates:', error);
      }
    }
  };
  
  const handleSearch = async (query: string) => {
    console.log('Search query:', query);
    try {
      // Perform the search
      console.log('Searching for:', query);
    } catch (error) {
      console.error('Search failed:', error);
    }
  };
  
  const handleResultSelect = async (result: any) => {
    console.log('Selected result:', result);
    
    // Clear previous selections
    setSelectedCityId(null);
    setSearchCoordinates(null);
    
    // If the result has coordinates, fetch weather data by coordinates
    if (result.latitude && result.longitude) {
      try {
        // Set coordinates to trigger weather update
        setSearchCoordinates({lat: result.latitude, lon: result.longitude});
        
        // Update the location with the selected city
        if (location) {
          setLocation({
            ...location,
            city: result.name,
            country: result.country
          });
        }
      } catch (error) {
        console.error('Failed to fetch weather data by coordinates:', error);
      }
    } else if (result.id) {
      // If it's a city from our database, use the normal flow
      setSelectedCityId(result.id);
      setSearchCoordinates(null);
      
      // Update the location with the selected city
      if (location) {
        setLocation({
          ...location,
          city: result.name,
          country: result.country
        });
      }
    }
    
    // Fetch weather data for the selected city/coordinates
    try {
      // The useWeatherUpdates hook will automatically fetch data when selectedCityId changes
      console.log('Fetching weather data for:', result);
    } catch (error) {
      console.error('Failed to fetch weather data:', error);
    }
  };

  // Generate hourly forecast data
  const [hourlyForecast, setHourlyForecast] = useState<any[]>([]);
  const [dailyForecast, setDailyForecast] = useState<any[]>([]);

  // Fetch real hourly forecast data
  const fetchHourlyForecast = async (cityId: string | null, lat?: number, lon?: number) => {
    try {
      let data;
      if (lat && lon) {
        // Fetch by coordinates
        data = await publicWeatherApi.getHourlyForecastByCoordinates(lat, lon, 24);
      } else if (cityId) {
        // Fetch by city ID
        data = await publicWeatherApi.getHourlyForecast(cityId, 24);
      }
      
      // Transform the data to match the HourlyForecast component's expected structure
      const transformedData = data ? data.map((item: any) => ({
        time: new Date(item.timestamp).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
        temperature: item.temperature,
        feelsLike: item.feelsLike,
        icon: item.weatherIcon || '❓',
        precipitation: 0, // This would need to come from the API if available
        windSpeed: item.windSpeed,
        humidity: item.humidity
      })) : [];
      
      setHourlyForecast(transformedData);
    } catch (error) {
      console.error('Failed to fetch hourly forecast:', error);
      // Fallback to generated data if API fails
      setHourlyForecast(generateHourlyForecast());
    }
  };

  // Fetch real daily forecast data
  const fetchDailyForecast = async (cityId: string | null, lat?: number, lon?: number) => {
    try {
      let data;
      if (lat && lon) {
        // Fetch by coordinates
        data = await publicWeatherApi.getDailyForecastByCoordinates(lat, lon, 7);
      } else if (cityId) {
        // Fetch by city ID
        data = await publicWeatherApi.getDailyForecast(cityId, 7);
      }
      
      // Transform the data to match the DailyForecast component's expected structure
      const transformedData = data ? data.map((item: any, index: number) => {
        const date = new Date(item.timestamp);
        const dayNames = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
        
        // For the first item (today), show "Today" instead of the day name
        // For subsequent items, calculate the correct day name based on today
        let dayName;
        if (index === 0) {
          dayName = 'Today';
        } else {
          dayName = dayNames[date.getDay()];
        }
        
        return {
          day: dayName,
          date: date.toLocaleDateString([], { month: 'short', day: 'numeric' }),
          highTemp: Math.round(item.temperature),
          lowTemp: Math.round(item.temperature - 5), // This is just an approximation
          icon: item.weatherIcon || '❓',
          precipitation: 0, // This would need to come from the API if available
          windSpeed: item.windSpeed || 0,
          humidity: item.humidity || 0,
          uvIndex: item.uvIndex || 0
        };
      }) : [];
      
      setDailyForecast(transformedData);
    } catch (error) {
      console.error('Failed to fetch daily forecast:', error);
      // Fallback to generated data if API fails
      setDailyForecast(generateDailyForecast());
    }
  };

  // Effect to fetch forecast data when weather data changes
  useEffect(() => {
    if (searchCoordinates) {
      fetchHourlyForecast(null, searchCoordinates.lat, searchCoordinates.lon);
      fetchDailyForecast(null, searchCoordinates.lat, searchCoordinates.lon);
    } else if (effectiveCityId) {
      fetchHourlyForecast(effectiveCityId);
      fetchDailyForecast(effectiveCityId);
    }
  }, [effectiveCityId, searchCoordinates]);

  // Generate mock hourly forecast data
  const generateHourlyForecast = () => {
    return Array.from({ length: 24 }, (_, i) => ({
      time: new Date(Date.now() + i * 3600000).toLocaleTimeString([], { hour: '2-digit' }),
      temperature: Math.floor(Math.random() * 10) + 20,
      condition: ['Sunny', 'Cloudy', 'Rainy'][Math.floor(Math.random() * 3)],
      icon: ['☀️', '☁️', '🌧️'][Math.floor(Math.random() * 3)]
    }));
  };

  // Generate mock daily forecast data
  const generateDailyForecast = () => {
    const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    return Array.from({ length: 7 }, (_, i) => ({
      day: days[(new Date().getDay() + i) % 7],
      high: Math.floor(Math.random() * 10) + 25,
      low: Math.floor(Math.random() * 10) + 15,
      condition: ['Sunny', 'Cloudy', 'Rainy'][Math.floor(Math.random() * 3)],
      icon: ['☀️', '☁️', '🌧️'][Math.floor(Math.random() * 3)]
    }));
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-400 via-purple-500 to-pink-500 dark:from-gray-900 dark:via-gray-800 dark:to-gray-900 text-white">
      {/* Header */}
      <header className="py-6 px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center">
          <h1 className="text-4xl font-bold">Java Development Weather Forecast App</h1>
          <ThemeToggle />
        </div>
      </header>

      {/* Main Content */}
      <main className="container mx-auto px-4 py-8">
        {/* Location Detector */}
        <LocationDetector onLocationDetected={handleLocationDetected} />

        {/* Search Bar */}
        <SearchBar onSearch={handleSearch} onResultSelect={handleResultSelect} />

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
          city={location?.city || weatherData?.cityName || "Unknown Location"}
          dateTime={weatherData?.timestamp ?? ""}
        />

        {/* Hourly Forecast */}
        <HourlyForecast forecast={hourlyForecast} />

        {/* 7-Day Forecast */}
        <DailyForecast forecast={dailyForecast} />

        {/* World Map */}
        <WorldMap />
      </main>

      {/* Footer */}
      <footer className="py-6 px-4 text-center text-white/80">
        <p>© 2025 Java Development Weather Forecast App. All rights reserved.</p>
      </footer>
    </div>
  );
}

export default App;