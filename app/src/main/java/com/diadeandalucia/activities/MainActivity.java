package com.diadeandalucia.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.diadeandalucia.R;
import com.diadeandalucia.fragments.PersonajesFragment;
// Importa los demás fragments aquí...
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Configurar el listener para los clics
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_personajes) {
                selectedFragment = new PersonajesFragment();
            } else if (id == R.id.nav_videos) {
                // selectedFragment = new VideosFragment();
            } else if (id == R.id.nav_sonidos) {
                // selectedFragment = new SonidosFragment();
            } else if (id == R.id.nav_animaciones) {
                // selectedFragment = new AnimacionesFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });

        // Cargar el primero por defecto
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PersonajesFragment())
                    .commit();
        }
    }
}