package com.diadeandalucia.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager; // Importante añadir esto

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.diadeandalucia.R;
import com.diadeandalucia.fragments.AnimacionesFragment;
import com.diadeandalucia.fragments.PersonajesFragment;
import com.diadeandalucia.fragments.SonidosFragment;
import com.diadeandalucia.fragments.VideosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. SOLO UN setContentView
        setContentView(R.layout.activity_main);

        // 2. CONFIGURAR BARRA DE ESTADO (STATUS BAR)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Limpiar flags que puedan estar forzando la transparencia
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // Indicar que la ventana se encarga de dibujar el fondo de las barras del sistema
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // Establecer el color verde que definiste
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.verdePrimario));
        }

        // 3. CONFIGURAR ICONOS (BLANCOS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            // Poniendo 0 nos aseguramos de que NO esté activo el modo "LightStatusBar" (iconos negros)
            // Por lo tanto, los iconos serán BLANCOS.
            decor.setSystemUiVisibility(0);
        }

        // 4. CONFIGURAR NAVEGACIÓN
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_personajes) {
                selectedFragment = new PersonajesFragment();
            } else if (id == R.id.nav_videos) {
                selectedFragment = new VideosFragment();
            } else if (id == R.id.nav_sonidos) {
                selectedFragment = new SonidosFragment();
            } else if (id == R.id.nav_animaciones) {
                selectedFragment = new AnimacionesFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PersonajesFragment())
                    .commit();
        }
    }
}