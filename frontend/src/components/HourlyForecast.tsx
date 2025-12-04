import React from 'react';

interface HourlyForecastItem {
  time: string;
  temperature: number;
  icon: string;
}

interface HourlyForecastProps {
  forecast: HourlyForecastItem[];
}

const HourlyForecast: React.FC<HourlyForecastProps> = ({ forecast }) => {
  return (
    <div className="mb-8">
      <h3 className="text-xl font-semibold text-white mb-4">Hourly Forecast</h3>
      <div className="flex overflow-x-auto pb-4 space-x-4">
        {forecast.map((item, index) => (
          <div key={index} className="bg-white/20 backdrop-blur-sm rounded-2xl p-4 min-w-[100px] text-center text-white">
            <p className="mb-2">{item.time}</p>
            <div className="text-2xl mb-2">{item.icon}</div>
            <p className="font-semibold">{item.temperature}°</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default HourlyForecast;