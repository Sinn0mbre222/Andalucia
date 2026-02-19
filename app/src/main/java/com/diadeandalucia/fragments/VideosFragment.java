package com.diadeandalucia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.diadeandalucia.R;
import com.diadeandalucia.adapters.VideoAdapter;
import com.diadeandalucia.models.Video;
import java.util.ArrayList;
import java.util.List;

public class VideosFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Asegúrate de inflar el layout de vídeos
        View view = inflater.inflate(R.layout.fragment_videos, container, false);

        // CORRECCIÓN: Usamos el ID que pusimos en fragment_videos.xml
        RecyclerView rv = view.findViewById(R.id.rvVideos);

        if (rv != null) {
            rv.setLayoutManager(new LinearLayoutManager(getContext()));

            List<Video> lista = new ArrayList<>();
            lista.add(new Video("Viva Andalucía", R.raw.splash_video));
            lista.add(new Video("Problemas de Andalucía", R.raw.quemequedosincomer));
            lista.add(new Video("Lugares de Andalucía", R.raw.elriounamierda));

            VideoAdapter adapter = new VideoAdapter(lista);
            rv.setAdapter(adapter);
        }

        return view;
    }
}