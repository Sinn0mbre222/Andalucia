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
import com.diadeandalucia.adapters.SonidosAdapter;
import com.diadeandalucia.models.Sonido;

import java.util.ArrayList;
import java.util.List;

@UnstableApi
public class SonidosFragment extends Fragment {

    private ExoPlayer sharedPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el layout que contiene el ScrollView, los botones y el RecyclerView
        View view = inflater.inflate(R.layout.fragment_sonidos, container, false);

        // Inicializamos el reproductor único para todo el fragmento
        sharedPlayer = new ExoPlayer.Builder(requireContext()).build();

        // Configuración de clics en los botones destacados (IDs del XML)
        view.findViewById(R.id.btnSonido1).setOnClickListener(v -> playAudio(R.raw.himno_andalucia));
        view.findViewById(R.id.btnSonido2).setOnClickListener(v -> playAudio(R.raw.risitas));
        view.findViewById(R.id.btnSonido3).setOnClickListener(v -> playAudio(R.raw.himnobetis));
        view.findViewById(R.id.btnSonido4).setOnClickListener(v -> playAudio(R.raw.macarena));

        // Botón Stop (¡Cállate ya, primo!)
        view.findViewById(R.id.btnStop).setOnClickListener(v -> sharedPlayer.stop());

        // Configuración del RecyclerView para la lista "Más sonidos"
        RecyclerView rv = view.findViewById(R.id.rv);
        if (rv != null) {
            rv.setLayoutManager(new LinearLayoutManager(getContext()));

            // Importante: Al estar dentro de un ScrollView, desactivamos el scroll propio del RV
            rv.setNestedScrollingEnabled(false);

            // Creamos la lista de sonidos adicionales
            List<Sonido> lista = new ArrayList<>();
            lista.add(new Sonido("Mandanga Style", R.raw.mandangastyle));
            lista.add(new Sonido("Tómbola", R.raw.tombola));

            // Inicializamos el adaptador pasándole la lista y el reproductor compartido
            SonidosAdapter adapter = new SonidosAdapter(lista, sharedPlayer);
            rv.setAdapter(adapter);
        }

        return view;
    }

    /**
     * Método para reproducir un audio desde la carpeta res/raw
     * @param resId ID del recurso (ej: R.raw.nombre_audio)
     */
    private void playAudio(int resId) {
        if (sharedPlayer != null) {
            sharedPlayer.stop();
            sharedPlayer.clearMediaItems();

            // Construimos la URI del recurso
            Uri uri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + resId);
            MediaItem mediaItem = MediaItem.fromUri(uri);

            sharedPlayer.setMediaItem(mediaItem);
            sharedPlayer.prepare();
            sharedPlayer.play();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Liberamos el reproductor al cerrar el fragmento para evitar fugas de memoria
        if (sharedPlayer != null) {
            sharedPlayer.release();
            sharedPlayer = null;
        }
    }
}