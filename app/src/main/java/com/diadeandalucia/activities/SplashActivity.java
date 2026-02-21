package com.diadeandalucia.activities;

import android.content.Intent;
import android.media.MediaPlayer; // Importante
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.UnstableApi;

import com.diadeandalucia.R;

@UnstableApi
public class SplashActivity extends AppCompatActivity {

    // Lo hacemos estático para que la música NO se corte al pasar al MainActivity
    public static MediaPlayer himnoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // ¡A jierro! Iniciamos el himno nada más aparecer el Splash
        if (himnoPlayer == null) {
            himnoPlayer = MediaPlayer.create(this, R.raw.himno_andalucia);
            himnoPlayer.setLooping(true); // Para que esté en bucle
            himnoPlayer.start();
        }

        // Pasamos a MainActivity tras 3000 milisegundos
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}