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
        lista.add(new Personaje("Dragonite", "Dragonite del betis es famoso por su increible cola", R.drawable.dragonite_betis));
        lista.add(new Personaje("Juan y medio", "Presidente honorifico de la Republica independiente de Andalucia", R.drawable.juan_y_medio));

        PersonajesAdapter adapter = new PersonajesAdapter(lista);
        recyclerView.setAdapter(adapter);

        return view;
    }
}