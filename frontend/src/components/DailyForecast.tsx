import React from 'react';

interface DailyForecastItem {
  day: string;
  highTemp: number;
  lowTemp: number;
  icon: string;
}

interface DailyForecastProps {
  forecast: DailyForecastItem[];
}

const DailyForecast: React.FC<DailyForecastProps> = ({ forecast }) => {
  return (
    <div>
      <h3 className="text-xl font-semibold text-white mb-4">7-Day Forecast</h3>
      <div className="space-y-3">
        {forecast.map((item, index) => (
          <div key={index} className="bg-white/20 backdrop-blur-sm rounded-2xl p-4 flex justify-between items-center text-white">
            <p className="font-medium">{item.day}</p>
            <div className="text-2xl">{item.icon}</div>
            <div className="flex items-center space-x-4">
              <span>{item.highTemp}°</span>
              <span className="text-white/70">{item.lowTemp}°</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default DailyForecast;