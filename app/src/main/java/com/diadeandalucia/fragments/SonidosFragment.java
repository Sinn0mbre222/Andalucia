package com.diadeandalucia.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.diadeandalucia.R;

public class SonidosFragment extends Fragment {

    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sonidos, container, false);

        // Configurar botones
        view.findViewById(R.id.btnSonido1).setOnClickListener(v -> playSound(R.raw.himno_andalucia));
        view.findViewById(R.id.btnSonido2).setOnClickListener(v -> playSound(R.raw.risitas));
        view.findViewById(R.id.btnSonido3).setOnClickListener(v -> playSound(R.raw.himnobetis));

        view.findViewById(R.id.btnStop).setOnClickListener(v -> stopSound());

        return view;
    }

    private void playSound(int resId) {
        stopSound(); // Detenemos cualquier audio previo
        mediaPlayer = MediaPlayer.create(getContext(), resId);
        mediaPlayer.start();
    }

    private void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopSound(); // Si el usuario sale del fragmento, la música para
    }
}