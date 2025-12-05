import React from 'react';

interface WeatherCardProps {
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
  city: string;
  dateTime: string;
}

const WeatherCard: React.FC<WeatherCardProps> = ({
  temperature,
  feelsLike,
  condition,
  icon,
  humidity,
  windSpeed,
  windDirection,
  pressure,
  uvIndex,
  visibility,
  clouds,
  sunrise,
  sunset,
  city,
  dateTime
}) => {
  // Convert wind direction to compass direction
  const getWindDirection = (degrees: number) => {
    const directions = ['N', 'NE', 'E', 'SE', 'S', 'SW', 'W', 'NW'];
    const index = Math.round(degrees / 45) % 8;
    return directions[index];
  };

  // Format time from ISO string to HH:MM AM/PM
  const formatTime = (isoString: string) => {
    if (!isoString) return '--:--';
    const date = new Date(isoString);
    return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  };

  return (
    <div className="bg-white/20 backdrop-blur-sm rounded-3xl p-6 mb-8 text-white">
      <div className="flex justify-between items-start mb-4">
        <div>
          <h2 className="text-xl font-semibold mb-1">{city}</h2>
          <p className="text-white/80">{condition}</p>
        </div>
        <div className="text-right">
          <p className="text-white/80">{dateTime}</p>
        </div>
      </div>
      
      <div className="flex items-center justify-between mb-6">
        <div className="text-6xl font-light">{Math.round(temperature)}°</div>
        <div className="text-right">
          <div className="text-5xl">{icon}</div>
          <p className="text-white/80 mt-2">Feels like {Math.round(feelsLike)}°</p>
        </div>
      </div>
      
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        <div className="bg-white/10 rounded-2xl p-4 text-center">
          <p className="text-white/80 mb-1">Humidity</p>
          <p className="text-xl font-semibold">{humidity}%</p>
        </div>
        <div className="bg-white/10 rounded-2xl p-4 text-center">
          <p className="text-white/80 mb-1">Wind</p>
          <p className="text-xl font-semibold">{Math.round(windSpeed)} km/h</p>
          <p className="text-white/80 text-sm">{getWindDirection(windDirection)}</p>
        </div>
        <div className="bg-white/10 rounded-2xl p-4 text-center">
          <p className="text-white/80 mb-1">Pressure</p>
          <p className="text-xl font-semibold">{pressure} hPa</p>
        </div>
        <div className="bg-white/10 rounded-2xl p-4 text-center">
          <p className="text-white/80 mb-1">UV Index</p>
          <p className="text-xl font-semibold">{uvIndex}</p>
        </div>
        <div className="bg-white/10 rounded-2xl p-4 text-center">
          <p className="text-white/80 mb-1">Visibility</p>
          <p className="text-xl font-semibold">{visibility.toFixed(1)} km</p>
        </div>
        <div className="bg-white/10 rounded-2xl p-4 text-center">
          <p className="text-white/80 mb-1">Clouds</p>
          <p className="text-xl font-semibold">{clouds}%</p>
        </div>
        <div className="bg-white/10 rounded-2xl p-4 text-center">
          <p className="text-white/80 mb-1">Sunrise</p>
          <p className="text-xl font-semibold">{formatTime(sunrise)}</p>
        </div>
        <div className="bg-white/10 rounded-2xl p-4 text-center">
          <p className="text-white/80 mb-1">Sunset</p>
          <p className="text-xl font-semibold">{formatTime(sunset)}</p>
        </div>
      </div>
    </div>
  );
};

export default WeatherCard;