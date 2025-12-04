import React, { useState, useRef, useEffect } from 'react';

interface CityWeather {
  id: string;
  name: string;
  country: string;
  latitude: number;
  longitude: number;
  temperature: number;
  condition: string;
}

const WorldMap: React.FC = () => {
  const [selectedCity, setSelectedCity] = useState<CityWeather | null>(null);
  const [hoveredCity, setHoveredCity] = useState<CityWeather | null>(null);
  const mapRef = useRef<HTMLDivElement>(null);

  // Mock city weather data
  const cities: CityWeather[] = [
    { id: '1', name: 'New York', country: 'US', latitude: 40.7128, longitude: -74.0060, temperature: 22, condition: 'Sunny' },
    { id: '2', name: 'London', country: 'UK', latitude: 51.5074, longitude: -0.1278, temperature: 18, condition: 'Cloudy' },
    { id: '3', name: 'Tokyo', country: 'JP', latitude: 35.6762, longitude: 139.6503, temperature: 25, condition: 'Rainy' },
    { id: '4', name: 'Sydney', country: 'AU', latitude: -33.8688, longitude: 151.2093, temperature: 19, condition: 'Partly Cloudy' },
    { id: '5', name: 'Moscow', country: 'RU', latitude: 55.7558, longitude: 37.6173, temperature: -5, condition: 'Snowy' },
    { id: '6', name: 'Rio de Janeiro', country: 'BR', latitude: -22.9068, longitude: -43.1729, temperature: 30, condition: 'Sunny' },
    { id: '7', name: 'Cairo', country: 'EG', latitude: 30.0444, longitude: 31.2357, temperature: 35, condition: 'Clear' },
    { id: '8', name: 'Delhi', country: 'IN', latitude: 28.6139, longitude: 77.2090, temperature: 28, condition: 'Hazy' },
  ];

  // Convert latitude/longitude to x/y coordinates on the map
  const convertToCoordinates = (latitude: number, longitude: number) => {
    // Simple conversion for demonstration purposes
    // In a real implementation, you would use a proper map projection library
    const x = ((longitude + 180) / 360) * 100;
    const y = ((90 - latitude) / 180) * 100;
    return { x, y };
  };

  // Get temperature color (blue for cold, red for hot)
  const getTemperatureColor = (temp: number) => {
    if (temp < 0) return 'bg-blue-500';
    if (temp < 10) return 'bg-blue-400';
    if (temp < 20) return 'bg-green-400';
    if (temp < 30) return 'bg-yellow-400';
    return 'bg-red-500';
  };

  // Get weather icon
  const getWeatherIcon = (condition: string) => {
    switch (condition.toLowerCase()) {
      case 'sunny': return '☀️';
      case 'cloudy': return '☁️';
      case 'rainy': return '🌧️';
      case 'snowy': return '❄️';
      case 'clear': return '☀️';
      case 'hazy': return '🌫️';
      case 'partly cloudy': return '⛅';
      default: return '🌡️';
    }
  };

  return (
    <div className="mb-8">
      <h3 className="text-xl font-semibold text-white mb-4">World Weather Map</h3>
      <div 
        ref={mapRef}
        className="relative w-full h-96 bg-blue-900/30 rounded-2xl overflow-hidden"
      >
        {/* Simplified world map background */}
        <div className="absolute inset-0 bg-gradient-to-b from-blue-400/20 to-blue-600/20">
          {/* Continents (simplified shapes) */}
          <div className="absolute top-1/4 left-1/4 w-1/4 h-1/3 bg-green-700/30 rounded-full"></div>
          <div className="absolute top-1/3 right-1/4 w-1/5 h-1/4 bg-green-700/30 rounded-full"></div>
          <div className="absolute bottom-1/4 left-1/3 w-1/6 h-1/5 bg-green-700/30 rounded-full"></div>
        </div>

        {/* City markers */}
        {cities.map((city) => {
          const { x, y } = convertToCoordinates(city.latitude, city.longitude);
          const isSelected = selectedCity?.id === city.id;
          const isHovered = hoveredCity?.id === city.id;
          
          return (
            <div
              key={city.id}
              className={`absolute transform -translate-x-1/2 -translate-y-1/2 cursor-pointer transition-all duration-200 ${
                isSelected ? 'z-10 scale-125' : isHovered ? 'z-5 scale-110' : 'z-0'
              }`}
              style={{ left: `${x}%`, top: `${y}%` }}
              onMouseEnter={() => setHoveredCity(city)}
              onMouseLeave={() => setHoveredCity(null)}
              onClick={() => setSelectedCity(city)}
            >
              <div className={`${getTemperatureColor(city.temperature)} w-6 h-6 rounded-full flex items-center justify-center text-white text-xs border-2 border-white/50`}>
                {getWeatherIcon(city.condition)}
              </div>
              
              {/* Temperature label */}
              {(isSelected || isHovered) && (
                <div className="absolute top-full left-1/2 transform -translate-x-1/2 mt-2 bg-black/70 text-white text-xs rounded-lg py-1 px-2 whitespace-nowrap">
                  <div className="font-semibold">{city.name}, {city.country}</div>
                  <div>{city.temperature}°C - {city.condition}</div>
                </div>
              )}
            </div>
          );
        })}

        {/* Selected city info panel */}
        {selectedCity && (
          <div className="absolute bottom-4 left-4 right-4 bg-white/20 backdrop-blur-sm rounded-2xl p-4 text-white">
            <div className="flex justify-between items-start">
              <div>
                <h4 className="text-lg font-bold">{selectedCity.name}, {selectedCity.country}</h4>
                <div className="flex items-center mt-1">
                  <span className="text-3xl mr-2">{getWeatherIcon(selectedCity.condition)}</span>
                  <div>
                    <div className="text-2xl font-light">{selectedCity.temperature}°C</div>
                    <div className="text-white/80">{selectedCity.condition}</div>
                  </div>
                </div>
              </div>
              <button 
                onClick={() => setSelectedCity(null)}
                className="text-white/70 hover:text-white"
              >
                ✕
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default WorldMap;