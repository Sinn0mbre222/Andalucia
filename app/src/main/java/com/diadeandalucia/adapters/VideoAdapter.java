package com.diadeandalucia.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.recyclerview.widget.RecyclerView;
import com.diadeandalucia.R;
import com.diadeandalucia.models.Video;
import java.util.List;

// Fíjate que ahora pasamos VideoViewHolder (la clase externa)
public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    private List<Video> listaVideos;
    private Context context;
    private static ExoPlayer activePlayer = null;

    public VideoAdapter(List<Video> listaVideos) {
        this.listaVideos = listaVideos;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(v); // Llamamos a la clase externa
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = listaVideos.get(position);
        holder.tvTitulo.setText(video.getTitulo());

        ExoPlayer player = new ExoPlayer.Builder(context).build();
        holder.playerView.setPlayer(player);

        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + video.getVideoResId());
        MediaItem mediaItem = MediaItem.fromUri(uri);

        player.setMediaItem(mediaItem);
        player.prepare();
        player.setVolume(1.0f);
        player.setPlayWhenReady(false);

        player.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (isPlaying) {
                    if (activePlayer != null && activePlayer != player) {
                        activePlayer.pause();
                    }
                    activePlayer = player;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaVideos.size();
    }

    @Override
    public void onViewRecycled(@NonNull VideoViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder.playerView.getPlayer() != null) {
            ExoPlayer player = (ExoPlayer) holder.playerView.getPlayer();
            if (activePlayer == player) {
                activePlayer = null;
            }
            player.release();
            holder.playerView.setPlayer(null);
        }
    }
}