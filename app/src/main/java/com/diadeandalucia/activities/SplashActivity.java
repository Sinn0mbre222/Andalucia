package com.diadeandalucia.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler; // Importante añadir esto
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import com.diadeandalucia.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        VideoView videoView = findViewById(R.id.videoViewSplash);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.splash_video;
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();

        // ESTO BAJA EL TIEMPO:
        // Pasamos a MainActivity tras 2000 milisegundos (2 segundos)
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}