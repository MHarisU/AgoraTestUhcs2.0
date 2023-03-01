package com.haris.agora;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EchoWebSocketClient webSocketClient;

    int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.RECORD_AUDIO
                ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()){
                            //startActivity(new Intent(MainActivity.this, VideoActivity.class));
                        }
                        /* ... */

                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                }).check();

        websocket();

        // Subscribe to a channel
        if (webSocketClient != null && webSocketClient.isOpen()) {
            String message = "{\"op\":\"unconfirmed_sub\"}";
            webSocketClient.send(message);
        }

    }

    private void websocket() {
        // Connect to the WebSocket server
        try {
            URI serverUri = new URI("wss://ws.blockchain.info/inv");
            webSocketClient = new EchoWebSocketClient(serverUri);
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Disconnect from the WebSocket server
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }

    public void SendMessage(View view) {

        // Subscribe to a channel
        if (webSocketClient != null && webSocketClient.isOpen()) {
            String message = "{\n" +
                    "  \"op\": \"ping\"\n" +
                    "}";
            webSocketClient.send(message);
        }
    }
}