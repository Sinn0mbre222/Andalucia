package com.diadeandalucia.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;

import com.diadeandalucia.R;
import com.diadeandalucia.fragments.AnimacionesFragment;
import com.diadeandalucia.fragments.PersonajesFragment;
import com.diadeandalucia.fragments.SonidosFragment;
import com.diadeandalucia.fragments.VideosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

@UnstableApi
public class MainActivity extends AppCompatActivity {

    // REPRODUCTOR GLOBAL
    private ExoPlayer globalPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos el reproductor que sonará en toda la app
        globalPlayer = new ExoPlayer.Builder(this).build();
        // Configuramos para que siempre repita la canción elegida (Bucle)
        globalPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);

        // Configuración visual de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.verdePrimario));
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

    // Mtodo para que los Fragments puedan obtener el reproductor de la Activity
    public ExoPlayer getGlobalPlayer() {
        return globalPlayer;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberamos el reproductor SOLO cuando la app se cierre por completo
        if (globalPlayer != null) {
            globalPlayer.release();
            globalPlayer = null;
        }
    }
}