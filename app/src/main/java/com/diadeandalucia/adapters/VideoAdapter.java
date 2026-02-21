package com.diadeandalucia.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;
import com.diadeandalucia.R;
import com.diadeandalucia.activities.SplashActivity;
import com.diadeandalucia.models.Video;
import java.util.ArrayList;
import java.util.List;

@UnstableApi
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<Video> lista;
    private final ExoPlayer backgroundMusicPlayer;

    private boolean musicaPausadaPorVideo = false;
    private ExoPlayer activeVideoPlayer = null;

    // Lista para tener controlados todos los reproductores VIVOS
    private final List<ExoPlayer> reproductoresCreados = new ArrayList<>();

    public VideoAdapter(List<Video> lista, ExoPlayer backgroundMusicPlayer) {
        this.lista = lista;
        this.backgroundMusicPlayer = backgroundMusicPlayer;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    // 1. BIND SOLO TEXTO (Súper rápido y no gasta RAM)
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = lista.get(position);
        holder.tvTitulo.setText(video.getTitulo());

        // Solo guardamos qué vídeo le toca a esta fila, pero NO lo cargamos todavía
        holder.videoAsignado = video;
    }

    // 2. MAGIA: EL VÍDEO ENTRA EN PANTALLA
    @Override
    public void onViewAttachedToWindow(@NonNull VideoViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        // El usuario acaba de hacer scroll y este vídeo ha aparecido en pantalla.
        // ¡Ahora sí lo cargamos!
        if (holder.videoAsignado != null) {

            if (holder.videoPlayer == null) {
                holder.videoPlayer = new ExoPlayer.Builder(holder.itemView.getContext()).build();
                reproductoresCreados.add(holder.videoPlayer);
            }

            holder.playerView.setPlayer(holder.videoPlayer);

            Uri uri = Uri.parse("android.resource://" + holder.itemView.getContext().getPackageName() + "/" + holder.videoAsignado.getVideoResId());
            MediaItem mediaItem = MediaItem.fromUri(uri);
            holder.videoPlayer.setMediaItem(mediaItem);

            holder.videoPlayer.setPlayWhenReady(false);
            holder.videoPlayer.prepare(); // Carga la miniatura consumiendo RAM solo de los visibles

            holder.videoPlayer.addListener(new Player.Listener() {
                @Override
                public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
                    if (playWhenReady) {
                        if (activeVideoPlayer != null && activeVideoPlayer != holder.videoPlayer) {
                            ExoPlayer reproductorViejo = activeVideoPlayer;
                            activeVideoPlayer = holder.videoPlayer;
                            reproductorViejo.pause();
                        } else {
                            activeVideoPlayer = holder.videoPlayer;
                        }
                        pausarMusicaDeFondo();
                    } else {
                        if (activeVideoPlayer == holder.videoPlayer) {
                            reanudarMusicaDeFondo();
                        }
                    }
                }

                @Override
                public void onPlaybackStateChanged(int playbackState) {
                    if (playbackState == Player.STATE_ENDED) {
                        if (activeVideoPlayer == holder.videoPlayer) {
                            reanudarMusicaDeFondo();
                        }
                    }
                }

                // Por si el emulador sigue tosiendo, capturamos el error para no crashear
                @Override
                public void onPlayerError(@NonNull PlaybackException error) {
                    if (activeVideoPlayer == holder.videoPlayer) {
                        activeVideoPlayer = null;
                        reanudarMusicaDeFondo();
                    }
                }
            });
        }
    }

    // 3. MAGIA: EL VÍDEO SALE DE LA PANTALLA
    @Override
    public void onViewDetachedFromWindow(@NonNull VideoViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        // El usuario ha hecho scroll y el vídeo ya no se ve.
        // ¡Lo destruimos al instante para liberar la RAM de vídeo!
        if (holder.videoPlayer != null) {
            if (holder.videoPlayer == activeVideoPlayer) {
                reanudarMusicaDeFondo();
                activeVideoPlayer = null;
            }

            reproductoresCreados.remove(holder.videoPlayer);
            holder.videoPlayer.release(); // Libera el decodificador de hardware
            holder.videoPlayer = null;
            holder.playerView.setPlayer(null);
        }
    }

    @Override
    public void onViewRecycled(@NonNull VideoViewHolder holder) {
        super.onViewRecycled(holder);
        // Por seguridad, aseguramos la limpieza
        if (holder.videoPlayer != null) {
            holder.videoPlayer.release();
            holder.videoPlayer = null;
            holder.playerView.setPlayer(null);
        }
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    public void detenerVideoActivo() {
        if (activeVideoPlayer != null && activeVideoPlayer.isPlaying()) {
            activeVideoPlayer.pause();
        }
    }

    public void liberarTodosLosReproductores() {
        for (ExoPlayer player : reproductoresCreados) {
            if (player != null) {
                player.release();
            }
        }
        reproductoresCreados.clear();
        activeVideoPlayer = null;
    }

    // --- MÉTODOS AUXILIARES ---

    private void pausarMusicaDeFondo() {
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.isPlaying()) {
            backgroundMusicPlayer.pause();
            musicaPausadaPorVideo = true;
        }
        if (SplashActivity.himnoPlayer != null && SplashActivity.himnoPlayer.isPlaying()) {
            SplashActivity.himnoPlayer.pause();
            musicaPausadaPorVideo = true;
        }
    }

    private void reanudarMusicaDeFondo() {
        if (musicaPausadaPorVideo) {
            if (backgroundMusicPlayer != null) {
                backgroundMusicPlayer.play();
            }
            if (SplashActivity.himnoPlayer != null) {
                SplashActivity.himnoPlayer.start();
            }
            musicaPausadaPorVideo = false;
        }
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo;
        PlayerView playerView;
        ExoPlayer videoPlayer;
        Video videoAsignado; // NUEVO: Guardamos qué vídeo le toca

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTituloVideo);
            playerView = itemView.findViewById(R.id.exoPlayerView);
        }
    }
}