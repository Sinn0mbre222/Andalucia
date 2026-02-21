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
        View view = inflater.inflate(R.layout.fragment_videos, container, false);

        RecyclerView rv = view.findViewById(R.id.recyclerPersonajes);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Video> lista = new ArrayList<>();
        lista.add(new Video("Viva Andalucía", R.raw.himno_andalucia_video));
        lista.add(new Video("Pasando hambre", R.raw.quemequedosincomer));
        lista.add(new Video("Guapa, guapa y guapaaa!!", R.raw.guapa));
        lista.add(new Video("El río?", R.raw.elriounamierda));
        lista.add(new Video("Betis libre", R.raw.lopera));
        lista.add(new Video("Sa mato Paco", R.raw.sa_matao_paco));
        lista.add(new Video("Y modabaaa!!", R.raw.modaba));
        lista.add(new Video("Nocillaaa!!", R.raw.nocilla));

        VideoAdapter adapter = new VideoAdapter(lista);
        rv.setAdapter(adapter);

        return view;
    }
}