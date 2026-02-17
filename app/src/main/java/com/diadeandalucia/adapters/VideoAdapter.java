package com.diadeandalucia.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;
import com.diadeandalucia.R;
import com.diadeandalucia.models.Video;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<Video> listaVideos;
    private Context context;

    public VideoAdapter(List<Video> listaVideos) {
        this.listaVideos = listaVideos;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = listaVideos.get(position);
        holder.tvTitulo.setText(video.getTitulo());

        // 1. Inicializar ExoPlayer para este item
        ExoPlayer player = new ExoPlayer.Builder(context).build();
        holder.playerView.setPlayer(player);

        // 2. Configurar la fuente de video (res/raw)
        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + video.getVideoResId());
        MediaItem mediaItem = MediaItem.fromUri(uri);

        player.setMediaItem(mediaItem);
        player.prepare();

        // 3. Forzar volumen al máximo (1.0f)
        player.setVolume(1.0f);

        // Evitar que el video se reproduzca solo al hacer scroll (opcional)
        player.setPlayWhenReady(false);
    }

    @Override
    public int getItemCount() {
        return listaVideos.size();
    }

    // Importante: Liberar el player al destruir el ViewHolder para no gastar memoria
    @Override
    public void onViewRecycled(@NonNull VideoViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder.playerView.getPlayer() != null) {
            holder.playerView.getPlayer().release();
            holder.playerView.setPlayer(null);
        }
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo;
        PlayerView playerView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTituloVideo);
            playerView = itemView.findViewById(R.id.exoPlayerView);
        }
    }
}