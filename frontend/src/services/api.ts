// API service for communicating with the backend

const API_BASE_URL = 'http://localhost:8080/api';

// Generic fetch function with error handling
const apiFetch = async (url: string, options: RequestInit = {}) => {
  try {
    const response = await fetch(url, {
      ...options,
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
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
  
  getCityById: (cityId: string) => {
    return apiFetch(`${API_BASE_URL}/location/cities/${cityId}`);
  },
};

// Weather API
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
  geolocationApi,
};