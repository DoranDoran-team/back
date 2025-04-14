package com.korit.dorandoran.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.messaging.simp.config.MessageBrokerRegistry;
// import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
// import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
// import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// @Configuration
// @EnableWebSocketMessageBroker
// public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//     @Override
//     public void registerStompEndpoints(StompEndpointRegistry registry) {
//         registry.addEndpoint("/chat-websocket") // 클라이언트가 연결할 엔드포인트
//                 .setAllowedOrigins("http://localhost:3000")
//                 .addInterceptors(new AuthHandshakeInterceptor())
//                 .withSockJS(); // SockJS 지원
//     }

// @Override
// public void configureMessageBroker(MessageBrokerRegistry registry) {
//     registry.enableSimpleBroker("/topic"); // 구독자들에게 메시지 전송
//     registry.setApplicationDestinationPrefixes("/app"); // 클라이언트에서 서버로 보낼 경로
// }
// }

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 엔드포인트 (SockJS 지원)
        registry.addEndpoint("/chat-websocket")
                .setAllowedOrigins("http://localhost:3000")
                // 만약 토큰 검증이 필요하면 아래와 같이 HandshakeInterceptor 추가 가능
                // .addInterceptors(new AuthHandshakeInterceptor())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 백엔드가 메시지를 브로드캐스트할 때 사용할 목적지 프리픽스
        registry.enableSimpleBroker("/topic");
        // 클라이언트가 보내는 메시지를 처리할 때 사용할 프리픽스
        registry.setApplicationDestinationPrefixes("/app");
    }
}
