#!/bin/bash

# Weather Forecast Platform Setup Script

echo "🚀 Setting up Weather Forecast Platform..."

# Check if required tools are installed
echo "🔍 Checking prerequisites..."

# Check Java
if ! command -v java &> /dev/null
then
    echo "❌ Java is not installed. Please install Java 17 or higher."
    exit 1
fi

# Check Node.js
if ! command -v node &> /dev/null
then
    echo "❌ Node.js is not installed. Please install Node.js 16 or higher."
    exit 1
fi

# Check Maven
if ! command -v mvn &> /dev/null
then
    echo "❌ Maven is not installed. Please install Maven 3.6 or higher."
    exit 1
fi

# Check MongoDB
if ! command -v mongod &> /dev/null
then
    echo "⚠️  MongoDB is not installed. Please install MongoDB for full functionality."
fi

echo "✅ Prerequisites check completed."

# Set up backend
echo "🔧 Setting up backend..."
cd backend

# Install backend dependencies
echo "📥 Installing backend dependencies..."
mvn clean install

# Create application.properties if it doesn't exist
if [ ! -f "src/main/resources/application.properties" ]; then
    echo "📝 Creating application.properties..."
    cat > src/main/resources/application.properties << EOF
# Server configuration
server.port=8080

# MongoDB configuration
spring.data.mongodb.uri=mongodb://localhost:27017/weather_forecast

# JWT configuration
app.jwtSecret=weatherForecastSecretKey
app.jwtExpirationInMs=86400000

# External API keys (replace with your own keys)
openweathermap.api.key=your_openweathermap_api_key_here
ipapi.api.key=your_ipapi_key_here

# Logging
logging.level.com.weatherforecast=DEBUG
EOF
    echo "✅ Created application.properties. Please update API keys before running."
fi

cd ..

# Set up frontend
echo "🔧 Setting up frontend..."
cd frontend

# Install frontend dependencies
echo "📥 Installing frontend dependencies..."
npm install

# Create .env file if it doesn't exist
if [ ! -f ".env" ]; then
    echo "📝 Creating .env file..."
    cat > .env << EOF
# API Configuration
VITE_API_BASE_URL=http://localhost:8080/api

# Weather API keys (for client-side usage if needed)
VITE_OPENWEATHERMAP_API_KEY=your_openweathermap_api_key_here
EOF
    echo "✅ Created .env file. Please update API keys before running."
fi

cd ..

echo "🎉 Setup completed successfully!"

echo "
📝 Next steps:
1. Update API keys in backend/src/main/resources/application.properties
2. Start MongoDB database
3. Run backend: cd backend && mvn spring-boot:run
4. Run frontend: cd frontend && npm run dev
5. Access application at http://localhost:5173

🐳 Or use Docker:
1. Update API keys in docker-compose.yml
2. Run: docker-compose up -d
3. Access application at http://localhost:3000
"