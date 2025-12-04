package com.weatherforecast.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherforecast.dto.WeatherResponseDTO;
import com.weatherforecast.model.WeatherData;
import com.weatherforecast.service.impl.WeatherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WeatherWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private WeatherServiceImpl weatherService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // Store active sessions by city ID
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    
    // Store city ID for each session
    private final Map<String, String> sessionCityMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String cityId = extractCityId(session);
        if (cityId != null) {
            sessions.put(cityId, session);
            sessionCityMap.put(session.getId(), cityId);
            
            // Send initial weather data
            sendWeatherData(session, cityId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages if needed
        // For now, we'll just echo back any messages
        session.sendMessage(new TextMessage("Echo: " + message.getPayload()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String cityId = sessionCityMap.remove(session.getId());
        if (cityId != null) {
            sessions.remove(cityId);
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

    // Method to broadcast weather updates to all connected clients
    public void broadcastWeatherUpdate(String cityId, WeatherData weatherData) {
        WebSocketSession session = sessions.get(cityId);
        if (session != null && session.isOpen()) {
            try {
                WeatherResponseDTO responseDTO = new WeatherResponseDTO(weatherData);
                String json = objectMapper.writeValueAsString(responseDTO);
                session.sendMessage(new TextMessage(json));
            } catch (Exception e) {
                System.err.println("Error broadcasting weather update: " + e.getMessage());
            }
        }
    }
}