package com.haris.agora;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class EchoWebSocketClient extends WebSocketClient {

    public EchoWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("websocketCheck", "WebSocket connection opened");
        System.out.println("WebSocket connection opened");
    }

    @Override
    public void onMessage(String message) {
        Log.d("websocketCheck", "Received message: " + message);

        System.out.println("Received message: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d("websocketCheck", "WebSocket connection closed with code " + code + " and reason " + reason);

        System.out.println("WebSocket connection closed with code " + code + " and reason " + reason);
    }

    @Override
    public void onError(Exception ex) {
        Log.d("websocketCheck", "WebSocket error: " + ex.getMessage().toString());

        System.out.println("WebSocket error: " + ex.getMessage());
    }
}