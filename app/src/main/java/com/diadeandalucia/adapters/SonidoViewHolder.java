package com.diadeandalucia.adapters;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.ui.PlayerControlView;
import androidx.recyclerview.widget.RecyclerView;
import com.diadeandalucia.R;

@UnstableApi
public class SonidoViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTitulo;
    public PlayerControlView controlView;

    public SonidoViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitulo = itemView.findViewById(R.id.tvTituloSonido);
        controlView = itemView.findViewById(R.id.exoController);
    }
}