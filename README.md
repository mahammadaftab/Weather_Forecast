# Global Weather Forecasting Platform

A production-grade, enterprise-level global weather forecasting platform that provides accurate weather insights for every country, state, and major city worldwide.

## 🌟 Features

- **Real-time Weather Data**: Live temperature, AQI, humidity, and more
- **Location Detection**: Automatic user location detection (browser geolocation + IP fallback)
- **Comprehensive Forecasts**: 24-hour and 7-day forecasts
- **Weather Alerts**: Real-time extreme weather notifications
- **Global Coverage**: Weather data for cities worldwide
- **Interactive Maps**: Visual world weather map
- **Responsive Design**: Mobile-first, fully responsive UI
- **Theme Support**: Light, dark, and auto themes

## 🏗️ Architecture

### Backend (Java Spring Boot)
- **Framework**: Spring Boot 3.x
- **Database**: MongoDB
- **Security**: JWT-based authentication
- **Caching**: Caffeine Cache
- **APIs**: OpenWeatherMap, WeatherAPI integrations

### Frontend (React + Vite)
- **Framework**: React 18 with TypeScript
- **Build Tool**: Vite
- **Styling**: TailwindCSS
- **State Management**: React Hooks
- **Real-time**: WebSocket connections

## 📁 Project Structure

```
weather-forecast/
├── backend/                 # Java Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/weatherforecast/
│   │   │   │   ├── controller/     # REST controllers
│   │   │   │   ├── service/        # Business logic
│   │   │   │   ├── repository/     # Database repositories
│   │   │   │   ├── model/          # Data models
│   │   │   │   ├── dto/            # Data transfer objects
│   │   │   │   ├── config/         # Configuration classes
│   │   │   │   ├── security/       # Security configurations
│   │   │   │   └── util/           # Utility classes
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml              # Maven configuration
├── frontend/                # React frontend
│   ├── src/
│   │   ├── components/      # Reusable UI components
│   │   ├── contexts/        # React contexts
│   │   ├── hooks/           # Custom React hooks
│   │   ├── services/        # API services
│   │   └── App.tsx          # Main application component
│   ├── index.html           # HTML entry point
│   └── vite.config.js       # Vite configuration
└── README.md                # Project documentation
```

## 🚀 Getting Started

### Prerequisites

- **Java 17+** for backend
- **Node.js 16+** for frontend
- **MongoDB** database
- **OpenWeatherMap API key**

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Install dependencies:
   ```bash
   mvn clean install
   ```

3. Configure environment variables:
   ```bash
   # Create application.properties or use environment variables
   export SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/weather_forecast
   export OPENWEATHERMAP_API_KEY=your_api_key_here
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

4. Build for production:
   ```bash
   npm run build
   ```

## 🔐 Security

- **JWT Authentication**: Secure user authentication
- **Role-based Access**: Different permissions for user roles
- **Input Validation**: Backend validation for all inputs
- **CORS Protection**: Controlled cross-origin requests
- **Rate Limiting**: API rate limiting to prevent abuse

## 🛠️ API Endpoints

### Authentication
- `POST /api/auth/signin` - User login
- `POST /api/auth/signup` - User registration

### Location
- `GET /api/location/countries` - Get all countries
- `GET /api/location/states/{countryId}` - Get states by country
- `GET /api/location/cities/state/{stateId}` - Get cities by state
- `GET /api/location/cities/search?name={query}` - Search cities

### Weather
- `GET /api/weather/current/{cityId}` - Current weather
- `GET /api/weather/forecast/hourly/{cityId}?hours={n}` - Hourly forecast
- `GET /api/weather/forecast/daily/{cityId}?days={n}` - Daily forecast
- `GET /api/weather/alerts/{cityId}` - Weather alerts

## 🧪 Testing

### Backend
- **Unit Tests**: JUnit 5 with Mockito
- **Integration Tests**: SpringBootTest
- **API Tests**: Postman collections

### Frontend
- **Component Tests**: Vitest + React Testing Library
- **E2E Tests**: Cypress (optional)

Run backend tests:
```bash
cd backend
mvn test
```

Run frontend tests:
```bash
cd frontend
npm run test
```

## 📊 Performance

- **Caching**: In-memory caching with Caffeine
- **Database Indexes**: Optimized MongoDB queries
- **Connection Pooling**: Efficient database connections
- **Async Processing**: Non-blocking operations

## 🌍 Deployment

### Production Build

1. Build the frontend:
   ```bash
   cd frontend
   npm run build
   ```

2. Package the backend:
   ```bash
   cd backend
   mvn clean package
   ```

### Environment Variables

| Variable | Description | Required |
|----------|-------------|----------|
| `SPRING_DATA_MONGODB_URI` | MongoDB connection URI | Yes |
| `OPENWEATHERMAP_API_KEY` | OpenWeatherMap API key | Yes |
| `JWT_SECRET` | Secret for JWT tokens | Yes |
| `SERVER_PORT` | Application port (default: 8080) | No |

### Docker Deployment (Optional)

Create a `docker-compose.yml`:
```yaml
version: '3.8'
services:
  mongodb:
    image: mongo:latest
    ports:
      - "27017:27017"
    volumes:
      - mongodb_data:/data/db

  backend:
    build: ./backend
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATA_MONGODB_URI=mongodb://mongodb:27017/weather_forecast
      - OPENWEATHERMAP_API_KEY=your_api_key_here
    depends_on:
      - mongodb

  frontend:
    build: ./frontend
    ports:
      - "3000:3000"
    depends_on:
      - backend

volumes:
  mongodb_data:
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [OpenWeatherMap](https://openweathermap.org/api) for weather data
- [ip-api](http://ip-api.com) for geolocation services
- [React](https://reactjs.org) for the frontend framework
- [Spring Boot](https://spring.io/projects/spring-boot) for the backend framework