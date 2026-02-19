package com.diadeandalucia.adapters;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;
import com.diadeandalucia.R;

public class VideoViewHolder extends RecyclerView.ViewHolder {

    // Hacemos los atributos públicos o creamos getters para que el Adapter los vea
    public TextView tvTitulo;
    public PlayerView playerView;

    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        // Vinculamos los componentes del XML item_video
        tvTitulo = itemView.findViewById(R.id.tvTituloVideo);
        playerView = itemView.findViewById(R.id.exoPlayerView);
    }
}