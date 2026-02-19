package com.diadeandalucia.adapters;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.diadeandalucia.R;

public class SonidoViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTitulo;

    public SonidoViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitulo = itemView.findViewById(R.id.tvTituloSonido);
    }
}