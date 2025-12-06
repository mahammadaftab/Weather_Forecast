// API service for communicating with the backend

const API_BASE_URL = 'http://localhost:8080/api';

// Store JWT token
let authToken: string | null = null;

// Set the auth token
export const setAuthToken = (token: string | null) => {
  authToken = token;
};

// Generic fetch function with error handling
const apiFetch = async (url: string, options: RequestInit = {}) => {
  try {
    // Add auth header if token exists
    const headers: HeadersInit = {
      'Content-Type': 'application/json',
      ...options.headers,
    };
    
    if (authToken) {
      headers['Authorization'] = `Bearer ${authToken}`;
    }
    
    const response = await fetch(url, {
      ...options,
      headers,
    });
    
    if (!response.ok) {
      // Try to parse error response
      let errorMessage = `HTTP error! status: ${response.status}`;
      try {
        const errorData = await response.json();
        if (errorData.message) {
          errorMessage = errorData.message;
        }
      } catch (e) {
        // If we can't parse the error response, use the default message
      }
      throw new Error(errorMessage);
    }
    
    return await response.json();
  } catch (error) {
    console.error('API request failed:', error);
    // Show user-friendly error message
    if (error instanceof TypeError && error.message.includes('fetch')) {
      throw new Error('Unable to connect to the server. Please check your internet connection.');
    }
    throw error;
  }
};

// Auth API
export const authApi = {
  login: (username: string, password: string) => {
    return apiFetch(`${API_BASE_URL}/auth/signin`, {
      method: 'POST',
      body: JSON.stringify({ username, password }),
    }).then((response) => {
      if (response.token) {
        setAuthToken(response.token);
      }
      return response;
    });
  },
  
  register: (userData: any) => {
    return apiFetch(`${API_BASE_URL}/auth/signup`, {
      method: 'POST',
      body: JSON.stringify(userData),
    });
  },
};

// Location API
export const locationApi = {
  getAllCountries: () => {
    return apiFetch(`${API_BASE_URL}/location/countries`);
  },
  
  getCountryById: (countryId: string) => {
    return apiFetch(`${API_BASE_URL}/location/countries/${countryId}`);
  },
  
  getStatesByCountryId: (countryId: string) => {
    return apiFetch(`${API_BASE_URL}/location/states/${countryId}`);
  },
  
  getCitiesByStateId: (stateId: string) => {
    return apiFetch(`${API_BASE_URL}/location/cities/state/${stateId}`);
  },
  
  searchCities: (name: string) => {
    return apiFetch(`${API_BASE_URL}/location/cities/search?name=${encodeURIComponent(name)}`);
  },
  
  // New method to search any location
  searchLocations: (query: string) => {
    return apiFetch(`${API_BASE_URL}/location/search?query=${encodeURIComponent(query)}`);
  },
  
  getCityById: (cityId: string) => {
    return apiFetch(`${API_BASE_URL}/location/cities/${cityId}`);
  },
};

// Public Weather API (no authentication required)
export const publicWeatherApi = {
  getCurrentWeather: (cityId: string) => {
    return apiFetch(`${API_BASE_URL}/weather/public/current/${cityId}`);
  },
  
  getHourlyForecast: (cityId: string, hours: number = 24) => {
    return apiFetch(`${API_BASE_URL}/weather/public/forecast/hourly/${cityId}?hours=${hours}`);
  },
  
  getDailyForecast: (cityId: string, days: number = 7) => {
    return apiFetch(`${API_BASE_URL}/weather/public/forecast/daily/${cityId}?days=${days}`);
  },
  
  getWeatherAlerts: (cityId: string) => {
    return apiFetch(`${API_BASE_URL}/weather/public/alerts/${cityId}`);
  },
  
  getHistoricalWeather: (cityId: string, start: string, end: string) => {
    return apiFetch(`${API_BASE_URL}/weather/public/historical/${cityId}?start=${start}&end=${end}`);
  },
  
  // New methods to get weather by coordinates
  getCurrentWeatherByCoordinates: (lat: number, lon: number) => {
    return apiFetch(`${API_BASE_URL}/weather/public/current-by-coordinates?lat=${lat}&lon=${lon}`);
  },
  
  getHourlyForecastByCoordinates: (lat: number, lon: number, hours: number = 24) => {
    return apiFetch(`${API_BASE_URL}/weather/public/forecast/hourly-by-coordinates?lat=${lat}&lon=${lon}&hours=${hours}`);
  },
  
  getDailyForecastByCoordinates: (lat: number, lon: number, days: number = 7) => {
    return apiFetch(`${API_BASE_URL}/weather/public/forecast/daily-by-coordinates?lat=${lat}&lon=${lon}&days=${days}`);
  },
  
  // World map weather data
  getWorldMapWeather: () => {
    return apiFetch(`${API_BASE_URL}/weather/public/world-map`);
  }
};

// Weather API (requires authentication)
export const weatherApi = {
  getCurrentWeather: (cityId: string) => {
    return apiFetch(`${API_BASE_URL}/weather/current/${cityId}`);
  },
  
  getHourlyForecast: (cityId: string, hours: number = 24) => {
    return apiFetch(`${API_BASE_URL}/weather/forecast/hourly/${cityId}?hours=${hours}`);
  },
  
  getDailyForecast: (cityId: string, days: number = 7) => {
    return apiFetch(`${API_BASE_URL}/weather/forecast/daily/${cityId}?days=${days}`);
  },
  
  getWeatherAlerts: (cityId: string) => {
    return apiFetch(`${API_BASE_URL}/weather/alerts/${cityId}`);
  },
  
  getHistoricalWeather: (cityId: string, start: string, end: string) => {
    return apiFetch(`${API_BASE_URL}/weather/historical/${cityId}?start=${start}&end=${end}`);
  },
};

// Geolocation API
export const geolocationApi = {
  detectLocation: () => {
    return apiFetch(`${API_BASE_URL}/geolocation/detect`);
  },
};

export default {
  authApi,
  locationApi,
  weatherApi,
  publicWeatherApi,
  geolocationApi,
  setAuthToken,
};