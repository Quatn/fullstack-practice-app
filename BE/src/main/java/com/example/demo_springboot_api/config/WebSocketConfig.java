package com.example.demo_springboot_api.config;

import com.example.demo_springboot_api.config.constants.WebSocketConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
  @Value("${WEB_URL}")
  private String WEB_URL;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    // Domain for messages from the server to the client
    config.enableSimpleBroker(WebSocketConstants.BROKER_ENDPOINT);

    // "Domain" for messages from the client to the server
    config.setApplicationDestinationPrefixes(WebSocketConstants.APPLICATION_DESTINATION_PREFIX);
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry
        .addEndpoint(WebSocketConstants.SOCKET_URL_ENDPOINT)
        .setAllowedOrigins(WEB_URL)
        .withSockJS();
  }
}
