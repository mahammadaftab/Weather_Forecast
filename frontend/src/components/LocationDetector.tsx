import React, { useEffect, useState } from 'react';
import { geolocationApi } from '../services/api';

interface Location {
  latitude: number;
  longitude: number;
  city?: string;
  country?: string;
}

interface LocationDetectorProps {
  onLocationDetected: (location: Location) => void;
}

const LocationDetector: React.FC<LocationDetectorProps> = ({ onLocationDetected }) => {
  const [isDetecting, setIsDetecting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const detectLocation = () => {
    setIsDetecting(true);
    setError(null);

    // First, try browser geolocation
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          // Success - we have coordinates
          const location: Location = {
            latitude: position.coords.latitude,
            longitude: position.coords.longitude
          };
          
          // Try to find the closest city
          findClosestCity(position.coords.latitude, position.coords.longitude);
          
          onLocationDetected(location);
          setIsDetecting(false);
        },
        (err) => {
          // Geolocation failed, fall back to IP-based detection
          console.warn('Geolocation failed:', err);
          detectLocationByIP();
        },
        {
          timeout: 10000,
          enableHighAccuracy: true
        }
      );
    } else {
      // Browser doesn't support geolocation, fall back to IP-based detection
      console.warn('Geolocation not supported');
      detectLocationByIP();
    }
  };

  const findClosestCity = async (lat: number, lon: number) => {
    try {
      // In a real implementation, we would call a backend endpoint to find the closest city
      // For now, we'll simulate this by searching for cities near the coordinates
      console.log(`Finding closest city to ${lat}, ${lon}`);
      
      // We could implement a proper geocoding service here
      // For now, we'll just log the coordinates
    } catch (error) {
      console.error('Failed to find closest city:', error);
    }
  };

  const detectLocationByIP = async () => {
    try {
      const locationData = await geolocationApi.detectLocation();
      const location: Location = {
        latitude: locationData.latitude,
        longitude: locationData.longitude,
        city: locationData.name || 'Unknown Location',
        country: locationData.countryCode || 'Unknown Country'
      };
      onLocationDetected(location);
    } catch (error) {
      console.error('Failed to detect location:', error);
      // Provide a fallback location
      const fallbackLocation: Location = {
        latitude: 40.7128,
        longitude: -74.0060,
        city: 'New York',
        country: 'US'
      };
      onLocationDetected(fallbackLocation);
    } finally {
      setIsDetecting(false);
    }
  };

  useEffect(() => {
    // Auto-detect location when component mounts
    detectLocation();
  }, []);

  return (
    <div className="mb-4">
      {isDetecting && (
        <div className="flex items-center justify-center p-4 bg-blue-500/20 rounded-lg">
          <div className="animate-spin rounded-full h-6 w-6 border-b-2 border-white mr-2"></div>
          <span className="text-white">Detecting your location...</span>
        </div>
      )}
      
      {error && (
        <div className="p-4 bg-red-500/20 rounded-lg text-white">
          <p>{error}</p>
          <button 
            onClick={detectLocation}
            className="mt-2 px-4 py-2 bg-white/20 rounded-lg hover:bg-white/30 transition"
          >
            Try Again
          </button>
        </div>
      )}
      
      {!isDetecting && !error && (
        <button 
          onClick={detectLocation}
          className="flex items-center px-4 py-2 bg-white/20 rounded-lg text-white hover:bg-white/30 transition"
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
          </svg>
          Detect My Location
        </button>
      )}
    </div>
  );
};

export default LocationDetector;