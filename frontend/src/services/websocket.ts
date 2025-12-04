// WebSocket service for realtime weather updates

class WeatherWebSocket {
  private ws: WebSocket | null = null;
  private reconnectAttempts = 0;
  private maxReconnectAttempts = 5;
  private reconnectInterval = 3000;
  private listeners: ((data: any) => void)[] = [];
  private cityId: string | null = null;

  connect(cityId: string) {
    this.cityId = cityId;
    
    // Close existing connection if any
    if (this.ws) {
      this.ws.close();
    }
    
    // Create new WebSocket connection
    const wsUrl = `ws://localhost:8080/ws/weather/${cityId}`;
    this.ws = new WebSocket(wsUrl);
    
    this.ws.onopen = () => {
      console.log('WebSocket connected');
      this.reconnectAttempts = 0;
    };
    
    this.ws.onmessage = (event) => {
      try {
        const data = JSON.parse(event.data);
        this.notifyListeners(data);
      } catch (error) {
        console.error('Failed to parse WebSocket message:', error);
      }
    };
    
    this.ws.onclose = () => {
      console.log('WebSocket disconnected');
      this.handleReconnect();
    };
    
    this.ws.onerror = (error) => {
      console.error('WebSocket error:', error);
      this.ws?.close();
    };
  }
  
  disconnect() {
    if (this.ws) {
      this.ws.close();
      this.ws = null;
    }
    this.listeners = [];
  }
  
  subscribe(listener: (data: any) => void) {
    this.listeners.push(listener);
  }
  
  unsubscribe(listener: (data: any) => void) {
    this.listeners = this.listeners.filter(l => l !== listener);
  }
  
  private notifyListeners(data: any) {
    this.listeners.forEach(listener => listener(data));
  }
  
  private handleReconnect() {
    if (this.reconnectAttempts < this.maxReconnectAttempts && this.cityId) {
      this.reconnectAttempts++;
      console.log(`Attempting to reconnect (${this.reconnectAttempts}/${this.maxReconnectAttempts})...`);
      
      setTimeout(() => {
        this.connect(this.cityId!);
      }, this.reconnectInterval);
    }
  }
}

export default new WeatherWebSocket();