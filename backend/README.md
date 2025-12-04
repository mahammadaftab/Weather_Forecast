# Weather Forecast Backend

Java Spring Boot backend for the Global Weather Forecasting Platform.

## 🏗️ Architecture

The backend follows a layered architecture pattern:

```
com.weatherforecast
├── controller/     # REST controllers
├── service/        # Business logic implementations
├── repository/     # Database repositories
├── model/          # Data models
├── dto/            # Data transfer objects
├── config/         # Configuration classes
├── security/       # Security configurations
├── util/           # Utility classes
└── exception/      # Exception handlers
```

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB database

### Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```

2. Navigate to the backend directory:
   ```bash
   cd backend
   ```

3. Install dependencies:
   ```bash
   mvn clean install
   ```

### Configuration

Create `src/main/resources/application.properties`:

```properties
# Server configuration
server.port=8080

# MongoDB configuration
spring.data.mongodb.uri=mongodb://localhost:27017/weather_forecast

# JWT configuration
app.jwtSecret=weatherForecastSecretKey
app.jwtExpirationInMs=86400000

# External API keys
openweathermap.api.key=your_openweathermap_api_key
ipapi.api.key=your_ipapi_key

# Logging
logging.level.com.weatherforecast=DEBUG
```

### Running the Application

```bash
# Development
mvn spring-boot:run

# Production
mvn clean package
java -jar target/weather-backend-0.0.1-SNAPSHOT.jar
```

## 🧪 Testing

Run unit and integration tests:

```bash
mvn test
```

## 🛠️ API Documentation

The API is documented using Swagger/OpenAPI. Access the documentation at:

```
http://localhost:8080/swagger-ui.html
```

## 📦 Dependencies

Key dependencies include:

- **Spring Boot Starter Web**: For building web applications
- **Spring Boot Starter Data MongoDB**: For MongoDB integration
- **Spring Boot Starter Security**: For security features
- **Spring Boot Starter Validation**: For input validation
- **Spring Boot Starter Cache**: For caching support
- **Caffeine**: High performance caching library
- **JJWT**: JSON Web Token library
- **Jackson**: JSON processing

## 🔐 Security

- JWT-based authentication
- Role-based access control
- Password encryption with BCrypt
- CORS configuration
- Input validation and sanitization

## 📊 Database Schema

### Collections

1. **countries** - Country information
2. **states** - State/province information
3. **cities** - City information
4. **weather_data** - Weather data records
5. **users** - User accounts
6. **user_location_logs** - User location history
7. **settings** - Application settings

## 🔄 Caching

The application uses Caffeine for in-memory caching:

- Current weather data: 30 minutes
- Forecast data: 1 hour
- Location data: 24 hours

## 📈 Performance

- Connection pooling for database connections
- Asynchronous processing for external API calls
- Efficient database indexing
- Response compression

## 🐳 Docker Support

Build Docker image:

```bash
docker build -t weather-backend .
```

Run with Docker:

```bash
docker run -p 8080:8080 \
  -e SPRING_DATA_MONGODB_URI=mongodb://host.docker.internal:27017/weather_forecast \
  -e OPENWEATHERMAP_API_KEY=your_api_key \
  weather-backend
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a pull request

## 📄 License

This project is licensed under the MIT License.