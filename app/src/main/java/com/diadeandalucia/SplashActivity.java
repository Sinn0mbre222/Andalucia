package com.diadeandalucia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        VideoView videoView = findViewById(R.id.videoViewSplash);

        // Configuramos la ruta del video que acabas de pegar en raw
        String path = "android.resource://" + getPackageName() + "/" + R.raw.splash_video;
        videoView.setVideoURI(Uri.parse(path));

        // Cuando el video termine, pasamos a la MainActivity
        videoView.setOnCompletionListener(mp -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Cerramos la splash para que no se pueda volver atrás
        });

        videoView.start();
    }
}