import React from 'react';

export interface DailyForecastItem {
  day: string;
  date: string;
  highTemp: number;
  lowTemp: number;
  icon: string;
  precipitation: number;
  windSpeed: number;
  humidity: number;
  uvIndex: number;
}

interface DailyForecastProps {
  forecast: DailyForecastItem[];
}

const DailyForecast: React.FC<DailyForecastProps> = ({ forecast }) => {
  return (
    <div>
      <h3 className="text-xl font-semibold text-white mb-4 dark:text-gray-100">7-Day Forecast</h3>
      <div className="space-y-3">
        {forecast.map((item, index) => (
          <div key={index} className="bg-white/20 backdrop-blur-sm rounded-2xl p-4 text-white dark:bg-gray-800/30 dark:text-gray-100">
            <div className="flex justify-between items-center mb-2">
              <div>
                <p className="font-medium">{item.day}</p>
                <p className="text-white/70 text-sm dark:text-gray-300/70">{item.date}</p>
              </div>
              <div className="text-3xl">{item.icon}</div>
              <div className="flex items-center space-x-4">
                <span className="font-semibold">{Math.round(item.highTemp)}°</span>
                <span className="text-white/70 dark:text-gray-300/70">{Math.round(item.lowTemp)}°</span>
              </div>
            </div>
            <div className="flex justify-between text-sm text-white/80 pt-2 border-t border-white/10 dark:text-gray-300/80 dark:border-gray-700/50">
              {item.precipitation > 0 && (
                <div className="flex items-center">
                  <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4 mr-1 text-blue-300" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 15a4 4 0 004 4h9a5 5 0 10-.1-9.999 5.002 5.002 0 10-9.78 2.096A4 4 0 003 15z" />
                  </svg>
                  {item.precipitation}%
                </div>
              )}
              <div className="flex items-center">
                <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
                </svg>
                {Math.round(item.windSpeed)} km/h
              </div>
              <div className="flex items-center">
                <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
                </svg>
                UV {item.uvIndex}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default DailyForecast;