package com.diadeandalucia.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.recyclerview.widget.RecyclerView;
import com.diadeandalucia.R;
import com.diadeandalucia.models.Sonido;
import java.util.List;

@UnstableApi
public class SonidosAdapter extends RecyclerView.Adapter<SonidoViewHolder> {

    private List<Sonido> lista;
    private ExoPlayer sharedPlayer;

    public SonidosAdapter(List<Sonido> lista, ExoPlayer player) {
        this.lista = lista;
        this.sharedPlayer = player;
    }

    @NonNull
    @Override
    public SonidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sonido, parent, false);
        return new SonidoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SonidoViewHolder holder, int position) {
        Sonido sonido = lista.get(position);
        holder.tvTitulo.setText(sonido.getTitulo());

        holder.itemView.setOnClickListener(v -> {
            sharedPlayer.stop();
            sharedPlayer.clearMediaItems();

            Uri uri = Uri.parse("android.resource://" + v.getContext().getPackageName() + "/" + sonido.getResId());
            MediaItem mediaItem = MediaItem.fromUri(uri);
            sharedPlayer.setMediaItem(mediaItem);
            sharedPlayer.prepare();
            sharedPlayer.play();
        });
    }

    @Override
    public int getItemCount() { return lista.size(); }
}