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
import com.diadeandalucia.adapters.PersonajesAdapter;
import com.diadeandalucia.models.Personaje;

import java.util.ArrayList;
import java.util.List;

public class PersonajesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personajes, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerPersonajes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Personaje> lista = new ArrayList<>();
        // Añade aquí tus personajes (nombre, descripción, imagen)
        lista.add(new Personaje("Dragonite", "Dragonite del betis, icono de la aplicación es famoso por su increíble cola", R.drawable.dragonite_betis));
        lista.add(new Personaje("Juan y medio", "Presidente honorifíco de la República independiente de Andalucía", R.drawable.juan_y_medio2));
        lista.add(new Personaje("El Risitas", "Embajador mundial de la paellera perdida y CEO del cuñao. ", R.drawable.risitas));
        lista.add(new Personaje("Joaquin Sanchez", "Campeón mundial en Tenis y en sus ratos libres jugaba al fútbol", R.drawable.joaquin));
        lista.add(new Personaje("Chiquito de la calzada", "El hombre que caminaba de lado antes que Michael Jackson. ¡No puedor, no puedor!", R.drawable.chiquito));
        lista.add(new Personaje("La Pepi", "La crítica gastronómica más sincera de Andalucía. Su merienda con Nocilla es el mejor anuncio de la historia.", R.drawable.pepi));

        PersonajesAdapter adapter = new PersonajesAdapter(lista);
        recyclerView.setAdapter(adapter);

        return view;
    }
}