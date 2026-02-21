package com.diadeandalucia.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackParameters; // ¡Importante para la velocidad!
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.recyclerview.widget.RecyclerView;

import com.diadeandalucia.R;
import com.diadeandalucia.activities.SplashActivity;
import com.diadeandalucia.models.Sonido;

import java.util.List;

@UnstableApi
public class SonidosAdapter extends RecyclerView.Adapter<SonidoViewHolder> {

    private final List<Sonido> lista;
    private final ExoPlayer sharedPlayer;

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

        // 1. Limpieza de seguridad: desvinculamos el reproductor de la vista reciclada
        holder.controlView.setPlayer(null);

        // 2. Si el reproductor tiene algo cargado, verificamos si es este sonido
        if (sharedPlayer.getCurrentMediaItem() != null) {
            String currentUri = sharedPlayer.getCurrentMediaItem().localConfiguration.uri.toString();
            String itemUri = "android.resource://" + holder.itemView.getContext().getPackageName() + "/" + sonido.getResId();

            if (currentUri.equals(itemUri)) {
                holder.controlView.setPlayer(sharedPlayer);
            }
        }

        holder.itemView.setOnClickListener(v -> {

            // --- DETENER EL HIMNO DEL SPLASH SI SIGUE SONANDO ---
            if (SplashActivity.himnoPlayer != null) {
                if (SplashActivity.himnoPlayer.isPlaying()) {
                    SplashActivity.himnoPlayer.stop();
                }
                SplashActivity.himnoPlayer.release();
                SplashActivity.himnoPlayer = null;
            }

            if (sharedPlayer != null) {
                // 3. Parada total y limpieza de la cola de reproducción
                sharedPlayer.stop();
                sharedPlayer.clearMediaItems();

                // Desvinculamos el control actual para forzar el refresco visual
                holder.controlView.setPlayer(null);

                // 4. Construimos la URI y el MediaItem con ID para que Media3 no se líe
                Uri uri = Uri.parse("android.resource://" + v.getContext().getPackageName() + "/" + sonido.getResId());
                MediaItem mediaItem = new MediaItem.Builder()
                        .setUri(uri)
                        .setMediaId(String.valueOf(sonido.getResId())) // ID vital
                        .build();

                sharedPlayer.setMediaItem(mediaItem);
                sharedPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);

                // --- TRUCO CONTRA LAS ARDILLAS ---
                // Forzamos la velocidad a 1.0f (velocidad normal)
                sharedPlayer.setPlaybackParameters(new PlaybackParameters(1f));

                // 5. Preparamos y vinculamos el control JUSTO antes de sonar
                sharedPlayer.prepare();
                holder.controlView.setPlayer(sharedPlayer);
                sharedPlayer.play();

                // 6. Notificamos para que los otros items suelten el control visual
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }
}