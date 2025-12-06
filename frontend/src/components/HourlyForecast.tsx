import React from 'react';

export interface HourlyForecastItem {
  time: string;
  temperature: number;
  feelsLike: number;
  icon: string;
  precipitation: number;
  windSpeed: number;
  humidity: number;
}

interface HourlyForecastProps {
  forecast: HourlyForecastItem[];
}

const HourlyForecast: React.FC<HourlyForecastProps> = ({ forecast }) => {
  return (
    <div className="mb-8">
      <h3 className="text-xl font-semibold text-white mb-4 dark:text-gray-100">Hourly Forecast</h3>
      <div className="flex overflow-x-auto pb-4 space-x-4">
        {forecast.map((item, index) => (
          <div key={index} className="bg-white/20 backdrop-blur-sm rounded-2xl p-4 min-w-[120px] text-center text-white dark:bg-gray-800/30 dark:text-gray-100">
            <p className="mb-2 font-medium">{item.time}</p>
            <div className="text-3xl mb-2">{item.icon}</div>
            <p className="text-xl font-semibold mb-1">{Math.round(item.temperature)}°</p>
            <p className="text-white/80 text-sm mb-1 dark:text-gray-300/80">Feels: {Math.round(item.feelsLike)}°</p>
            {item.precipitation > 0 && (
              <p className="text-blue-300 text-sm mb-1">{item.precipitation}%</p>
            )}
            <div className="flex justify-center items-center text-white/70 text-sm mt-1 dark:text-gray-400/70">
              <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
              </svg>
              {Math.round(item.windSpeed)} km/h
            </div>
            <div className="flex justify-center items-center text-white/70 text-sm mt-1 dark:text-gray-400/70">
              <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 15a4 4 0 004 4h9a5 5 0 10-.1-9.999 5.002 5.002 0 10-9.78 2.096A4 4 0 003 15z" />
              </svg>
              {item.humidity}%
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default HourlyForecast;