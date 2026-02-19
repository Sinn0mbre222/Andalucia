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
        setContentView(R.layout.activity_main);

        // Color de la barra y texto blanco
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.verdePrimario));
            getWindow().getDecorView().setSystemUiVisibility(0);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(0);
        }


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