package com.weatherforecast.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherforecast.dto.WeatherResponseDTO;
import com.weatherforecast.model.WeatherData;
import com.weatherforecast.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WeatherWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private WeatherService weatherService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // Store active sessions by city ID
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    
    // Store city ID for each session
    private final Map<String, String> sessionCityMap = new ConcurrentHashMap<>();
    
    // Store session IDs for each city (to support multiple clients per city)
    private final Map<String, ConcurrentHashMap<String, WebSocketSession>> citySessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String cityId = extractCityId(session);
        if (cityId != null) {
            // Add session to the city's session map
            citySessions.computeIfAbsent(cityId, k -> new ConcurrentHashMap<>()).put(session.getId(), session);
            sessionCityMap.put(session.getId(), cityId);
            
            // Send initial weather data
            sendWeatherData(session, cityId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            // Parse incoming message to see if it's a subscription change
            String payload = message.getPayload();
            if (payload.startsWith("{\"subscribe\":\"")) {
                // Handle subscription change
                handleSubscriptionChange(session, payload);
            } else {
                // Echo back any other messages
                session.sendMessage(new TextMessage("Echo: " + payload));
            }
        } catch (Exception e) {
            session.sendMessage(new TextMessage("Error processing message: " + e.getMessage()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        String cityId = sessionCityMap.remove(sessionId);
        
        if (cityId != null) {
            // Remove session from the city's session map
            ConcurrentHashMap<String, WebSocketSession> citySessionMap = citySessions.get(cityId);
            if (citySessionMap != null) {
                citySessionMap.remove(sessionId);
                if (citySessionMap.isEmpty()) {
                    citySessions.remove(cityId);
                }
            }
        }
    }

    private String extractCityId(WebSocketSession session) {
        String uri = session.getUri().getPath();
        String[] parts = uri.split("/");
        if (parts.length > 3) {
            return parts[3]; // Extract cityId from /ws/weather/{cityId}
        }
        return null;
    }
    
    private void handleSubscriptionChange(WebSocketSession session, String payload) throws IOException {
        // Parse the subscription change message
        // For simplicity, we'll assume it's a JSON with a "subscribe" field
        // In a real implementation, you'd use a proper JSON parser
        String newCityId = payload.substring(payload.indexOf("\"subscribe\":\"") + 13, payload.lastIndexOf("\""));
        
        // Unsubscribe from current city
        String oldCityId = sessionCityMap.get(session.getId());
        if (oldCityId != null) {
            ConcurrentHashMap<String, WebSocketSession> oldCitySessionMap = citySessions.get(oldCityId);
            if (oldCitySessionMap != null) {
                oldCitySessionMap.remove(session.getId());
                if (oldCitySessionMap.isEmpty()) {
                    citySessions.remove(oldCityId);
                }
            }
        }
        
        // Subscribe to new city
        sessionCityMap.put(session.getId(), newCityId);
        citySessions.computeIfAbsent(newCityId, k -> new ConcurrentHashMap<>()).put(session.getId(), session);
        
        // Send initial weather data for the new city
        sendWeatherData(session, newCityId);
    }

    private void sendWeatherData(WebSocketSession session, String cityId) throws IOException {
        if (weatherService != null) {
            // Get current weather data
            weatherService.getCurrentWeather(cityId).ifPresent(weatherData -> {
                try {
                    WeatherResponseDTO responseDTO = new WeatherResponseDTO(weatherData);
                    String json = objectMapper.writeValueAsString(responseDTO);
                    session.sendMessage(new TextMessage(json));
                } catch (Exception e) {
                    System.err.println("Error sending weather data: " + e.getMessage());
                }
            });
        }
    }

    // Method to broadcast weather updates to all connected clients for a specific city
    public void broadcastWeatherUpdate(String cityId, WeatherData weatherData) {
        ConcurrentHashMap<String, WebSocketSession> citySessionMap = citySessions.get(cityId);
        if (citySessionMap != null) {
            try {
                WeatherResponseDTO responseDTO = new WeatherResponseDTO(weatherData);
                String json = objectMapper.writeValueAsString(responseDTO);
                
                // Send to all sessions for this city
                for (WebSocketSession session : citySessionMap.values()) {
                    if (session.isOpen()) {
                        try {
                            session.sendMessage(new TextMessage(json));
                        } catch (IOException e) {
                            System.err.println("Error sending weather update to session: " + e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error broadcasting weather update: " + e.getMessage());
            }
        }
    }
    
    // Method to broadcast weather updates to all connected clients
    public void broadcastWeatherUpdateToAll(WeatherData weatherData) {
        String cityId = weatherData.getCityId();
        if (cityId != null) {
            broadcastWeatherUpdate(cityId, weatherData);
        }
    }
}