package com.diadeandalucia.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diadeandalucia.R;
import com.diadeandalucia.activities.MainActivity;
import com.diadeandalucia.adapters.SonidosAdapter;
import com.diadeandalucia.models.Sonido;

import java.util.ArrayList;
import java.util.List;

@UnstableApi
public class SonidosFragment extends Fragment {

    private ExoPlayer sharedPlayer;
    private RecyclerView rv; // Variable de clase para que sea accesible en todo el Fragment

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sonidos, container, false);

        if (getActivity() instanceof MainActivity) {
            sharedPlayer = ((MainActivity) getActivity()).getGlobalPlayer();
        }

        rv = view.findViewById(R.id.rv);

        // Botones destacados (Arriba)
        view.findViewById(R.id.btnSonido1).setOnClickListener(v -> playAudio(R.raw.himno_andalucia));
        view.findViewById(R.id.btnSonido2).setOnClickListener(v -> playAudio(R.raw.risitas));
        view.findViewById(R.id.btnSonido3).setOnClickListener(v -> playAudio(R.raw.himnobetis));
        view.findViewById(R.id.btnSonido4).setOnClickListener(v -> playAudio(R.raw.macarena));

        // Botón Stop (Cállate ya, primo)
        view.findViewById(R.id.btnStop).setOnClickListener(v -> {
            if (sharedPlayer != null) {
                sharedPlayer.stop();
                sharedPlayer.clearMediaItems();
                // Forzamos a la lista a ocultar cualquier control activo
                if (rv != null && rv.getAdapter() != null) {
                    rv.getAdapter().notifyDataSetChanged();
                }
            }
        });

        // Configuración de la lista (Abajo)
        if (rv != null) {
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            rv.setNestedScrollingEnabled(false);

            List<Sonido> lista = new ArrayList<>();
            lista.add(new Sonido("Tómbola", R.raw.tombola));
            lista.add(new Sonido("Ave María", R.raw.ave_maria));
            lista.add(new Sonido("La leyenda del tiempo", R.raw.camaron));
            lista.add(new Sonido("Mandanga Style", R.raw.mandangastyle));

            SonidosAdapter adapter = new SonidosAdapter(lista, sharedPlayer);
            rv.setAdapter(adapter);
        }

        return view;
    }

    private void playAudio(int resId) {
        if (sharedPlayer != null) {
            sharedPlayer.stop();
            sharedPlayer.clearMediaItems();

            Uri uri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + resId);
            sharedPlayer.setMediaItem(MediaItem.fromUri(uri));
            sharedPlayer.prepare();
            sharedPlayer.play();

            // Sincronizamos la lista: si estaba sonando algo abajo, se limpia el control
            if (rv != null && rv.getAdapter() != null) {
                rv.getAdapter().notifyDataSetChanged();
            }
        }
    }
}