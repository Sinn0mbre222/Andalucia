package com.diadeandalucia.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.diadeandalucia.R;
import com.diadeandalucia.models.Personaje;
import java.util.List;

public class PersonajesAdapter extends RecyclerView.Adapter<PersonajesAdapter.ViewHolder> {

    private List<Personaje> personajes;

    public PersonajesAdapter(List<Personaje> personajes) {
        this.personajes = personajes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personaje, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Personaje personaje = personajes.get(position);
        holder.txtNombre.setText(personaje.getNombre());
        holder.txtDescripcion.setText(personaje.getDescripcion());
        holder.imgPersonaje.setImageResource(personaje.getImagenResId());

        holder.itemView.setOnClickListener(v -> {
            String mensaje = "";
            // Usamos toLowerCase() para que no falle si escribes en mayúsculas/minúsculas
            String nombre = personaje.getNombre().toLowerCase();

            if (nombre.contains("joaquin")) {
                mensaje = "¡No he cogido una raqueta en mi vida, Hulio!";
            } else if (nombre.contains("risitas")) {
                mensaje = "¡Cuñaaooooo!";
            } else if (nombre.contains("dragonite")) {
                mensaje = "El rey es mi padre";
            } else if (nombre.contains("juan y medio")) {
                mensaje = "¡Más bueno que el pan con aceite!";
            } else if (nombre.contains("chiquito")) {
                mensaje = "¡Fistro pecador!, ¿Te da cuén?, Hasta luego Lucarrr";
            } else if (nombre.contains("pepi")) {
                mensaje = "¡Nociiillaaa que merendillaaa!";
            } else {
                mensaje = "¡Ole con ole y olé!";
            }

            Toast.makeText(v.getContext(), mensaje, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return personajes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPersonaje;
        TextView txtNombre, txtDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ahora los IDs coinciden exactamente con el XML de arriba
            imgPersonaje = itemView.findViewById(R.id.imgPersonaje);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
        }
    }
}