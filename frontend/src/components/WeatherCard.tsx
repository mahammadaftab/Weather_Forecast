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
  timezone?: string;
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
  dateTime,
  timezone
}) => {
  // Convert wind direction to compass direction
  const getWindDirection = (degrees: number) => {
    const directions = ['N', 'NE', 'E', 'SE', 'S', 'SW', 'W', 'NW'];
    const index = Math.round(degrees / 45) % 8;
    return directions[index];
  };

  // Format time from ISO string to HH:MM AM/PM with timezone consideration
  const formatTime = (isoString: string) => {
    if (!isoString) {
      // If no time, show a default placeholder
      return '--:--';
    }
    
    try {
      const date = new Date(isoString);
      // Check if the date is valid
      if (isNaN(date.getTime())) {
        return '--:--';
      }
      
      const options: Intl.DateTimeFormatOptions = { 
        hour: '2-digit', 
        minute: '2-digit'
      };
      
      // Add timezone if it's a valid IANA timezone identifier
      if (timezone) {
        try {
          // Test if the timezone is valid by checking if it contains only valid characters
          // Valid timezone identifiers contain only letters, numbers, underscores, slashes, and hyphens
          if (/^[a-zA-Z0-9_\/\-+]+$/.test(timezone)) {
            // Additional check for IANA timezone format (not just UTC+offset)
            if (!/^UTC[+-]\d+$/.test(timezone)) {
              // Test if the timezone is valid
              Intl.DateTimeFormat(undefined, { timeZone: timezone });
              options.timeZone = timezone;
              options.timeZoneName = 'short';
            } else {
              // Handle UTC+offset format by converting to proper IANA timezone
              console.warn('Unsupported timezone format (UTC+offset):', timezone);
            }
          }
        } catch (e) {
          // If timezone is invalid, we'll display without it
          console.warn('Invalid timezone:', timezone);
        }
      }
      
      return date.toLocaleTimeString([], options);
    } catch (error) {
      return '--:--';
    }
  };

  // Format date and time with timezone consideration
  const formatDateTime = (isoString: string) => {
    if (!isoString) {
      // If no timestamp, show a default message with current time
      const now = new Date();
      const options: Intl.DateTimeFormatOptions = {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      };
      
      // Add timezone if it's a valid IANA timezone identifier
      if (timezone) {
        try {
          // Test if the timezone is valid by checking if it contains only valid characters
          // Valid timezone identifiers contain only letters, numbers, underscores, slashes, and hyphens
          if (/^[a-zA-Z0-9_\/\-+]+$/.test(timezone)) {
            // Additional check for IANA timezone format (not just UTC+offset)
            if (!/^UTC[+-]\d+$/.test(timezone)) {
              // Test if the timezone is valid
              Intl.DateTimeFormat(undefined, { timeZone: timezone });
              options.timeZone = timezone;
              options.timeZoneName = 'short';
            } else {
              // Handle UTC+offset format by converting to proper IANA timezone
              console.warn('Unsupported timezone format (UTC+offset):', timezone);
            }
          }
        } catch (e) {
          // If timezone is invalid, we'll display without it
          console.warn('Invalid timezone:', timezone);
        }
      }
      
      return now.toLocaleString([], options);
    }
    
    try {
      const date = new Date(isoString);
      // Check if the date is valid
      if (isNaN(date.getTime())) {
        // If invalid date, show current time
        const now = new Date();
        const options: Intl.DateTimeFormatOptions = {
          weekday: 'long',
          year: 'numeric',
          month: 'long',
          day: 'numeric',
          hour: '2-digit',
          minute: '2-digit'
        };
        
        // Add timezone if it's a valid IANA timezone identifier
        if (timezone) {
          try {
            // Test if the timezone is valid by checking if it contains only valid characters
            // Valid timezone identifiers contain only letters, numbers, underscores, slashes, and hyphens
            if (/^[a-zA-Z0-9_\/\-+]+$/.test(timezone)) {
              // Additional check for IANA timezone format (not just UTC+offset)
              if (!/^UTC[+-]\d+$/.test(timezone)) {
                // Test if the timezone is valid
                Intl.DateTimeFormat(undefined, { timeZone: timezone });
                options.timeZone = timezone;
                options.timeZoneName = 'short';
              } else {
                // Handle UTC+offset format by converting to proper IANA timezone
                console.warn('Unsupported timezone format (UTC+offset):', timezone);
              }
            }
          } catch (e) {
            // If timezone is invalid, we'll display without it
            console.warn('Invalid timezone:', timezone);
          }
        }
        
        return now.toLocaleString([], options);
      }
      
      const options: Intl.DateTimeFormatOptions = {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      };
      
      // Add timezone if it's a valid IANA timezone identifier
      if (timezone) {
        try {
          // Test if the timezone is valid by checking if it contains only valid characters
          // Valid timezone identifiers contain only letters, numbers, underscores, slashes, and hyphens
          if (/^[a-zA-Z0-9_\/\-+]+$/.test(timezone)) {
            // Additional check for IANA timezone format (not just UTC+offset)
            if (!/^UTC[+-]\d+$/.test(timezone)) {
              // Test if the timezone is valid
              Intl.DateTimeFormat(undefined, { timeZone: timezone });
              options.timeZone = timezone;
              options.timeZoneName = 'short';
            } else {
              // Handle UTC+offset format by converting to proper IANA timezone
              console.warn('Unsupported timezone format (UTC+offset):', timezone);
            }
          }
        } catch (e) {
          // If timezone is invalid, we'll display without it
          console.warn('Invalid timezone:', timezone);
        }
      }
      
      return date.toLocaleString([], options);
    } catch (error) {
      // If there's an error, show current time
      const now = new Date();
      const options: Intl.DateTimeFormatOptions = {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      };
      
      // Add timezone if it's a valid IANA timezone identifier
      if (timezone) {
        try {
          // Test if the timezone is valid by checking if it contains only valid characters
          // Valid timezone identifiers contain only letters, numbers, underscores, slashes, and hyphens
          if (/^[a-zA-Z0-9_\/\-+]+$/.test(timezone)) {
            // Additional check for IANA timezone format (not just UTC+offset)
            if (!/^UTC[+-]\d+$/.test(timezone)) {
              // Test if the timezone is valid
              Intl.DateTimeFormat(undefined, { timeZone: timezone });
              options.timeZone = timezone;
              options.timeZoneName = 'short';
            } else {
              // Handle UTC+offset format by converting to proper IANA timezone
              console.warn('Unsupported timezone format (UTC+offset):', timezone);
            }
          }
        } catch (e) {
          // If timezone is invalid, we'll display without it
          console.warn('Invalid timezone:', timezone);
        }
      }
      
      return now.toLocaleString([], options);
    }
  };

  return (
    <div className="bg-white/20 backdrop-blur-sm rounded-3xl p-6 mb-8 text-white dark:bg-gray-800/30 dark:text-gray-100">
      <div className="flex justify-between items-start mb-4">
        <div>
          <h2 className="text-xl font-semibold mb-1">{city || 'Unknown Location'}</h2>
          <p className="text-white/80 dark:text-gray-300/80">{condition}</p>
        </div>
        <div className="text-right">
          <p className="text-white/80 dark:text-gray-300/80">{formatDateTime(dateTime)}</p>
          {timezone && <p className="text-white/60 text-sm dark:text-gray-400/60">{timezone}</p>}
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
        <div className="bg-white/10 rounded-2xl p-4 text-center dark:bg-gray-700/30">
          <p className="text-white/80 mb-1 dark:text-gray-300/80">Humidity</p>
          <p className="text-xl font-semibold">{humidity}%</p>
        </div>
        <div className="bg-white/10 rounded-2xl p-4 text-center dark:bg-gray-700/30">
          <p className="text-white/80 mb-1 dark:text-gray-300/80">Wind</p>
          <p className="text-xl font-semibold">{Math.round(windSpeed)} km/h</p>
          <p className="text-white/80 text-sm dark:text-gray-300/80">{getWindDirection(windDirection)}</p>
        </div>
        <div className="bg-white/10 rounded-2xl p-4 text-center dark:bg-gray-700/30">
          <p className="text-white/80 mb-1 dark:text-gray-300/80">Pressure</p>
          <p className="text-xl font-semibold">{pressure} hPa</p>
        </div>
        <div className="bg-white/10 rounded-2xl p-4 text-center dark:bg-gray-700/30">
          <p className="text-white/80 mb-1 dark:text-gray-300/80">UV Index</p>
          <p className="text-xl font-semibold">{uvIndex}</p>
        </div>
        <div className="bg-white/10 rounded-2xl p-4 text-center dark:bg-gray-700/30">
          <p className="text-white/80 mb-1 dark:text-gray-300/80">Visibility</p>
          <p className="text-xl font-semibold">{visibility.toFixed(1)} km</p>
        </div>
        <div className="bg-white/10 rounded-2xl p-4 text-center dark:bg-gray-700/30">
          <p className="text-white/80 mb-1 dark:text-gray-300/80">Clouds</p>
          <p className="text-xl font-semibold">{clouds}%</p>
        </div>
        <div className="bg-white/10 rounded-2xl p-4 text-center dark:bg-gray-700/30">
          <p className="text-white/80 mb-1 dark:text-gray-300/80">Sunrise</p>
          <p className="text-xl font-semibold">{formatTime(sunrise)}</p>
        </div>
        <div className="bg-white/10 rounded-2xl p-4 text-center dark:bg-gray-700/30">
          <p className="text-white/80 mb-1 dark:text-gray-300/80">Sunset</p>
          <p className="text-xl font-semibold">{formatTime(sunset)}</p>
        </div>
      </div>
    </div>
  );
};

export default WeatherCard;