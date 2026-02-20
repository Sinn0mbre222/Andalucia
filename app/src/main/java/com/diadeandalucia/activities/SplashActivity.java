package com.diadeandalucia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper; // Mejor práctica usar Looper
import androidx.appcompat.app.AppCompatActivity;
import com.diadeandalucia.R; // Asegúrate de que la ruta sea correcta

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Ya no necesitamos configurar el VideoView por código
        // porque Lottie se autoejecuta con 'app:lottie_autoPlay="true"'

        // Pasamos a MainActivity tras 3000 milisegundos (3 segundos)
        // He subido un poco el tiempo para que se luzca el Dragonite
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}