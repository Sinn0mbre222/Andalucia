package com.diadeandalucia.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackParameters;
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

        holder.controlView.setPlayer(null);

        // Comprobamos si este item es el que está sonando actualmente
        boolean isPlayingThisItem = false;

        if (sharedPlayer.getCurrentMediaItem() != null) {
            String currentUri = sharedPlayer.getCurrentMediaItem().localConfiguration.uri.toString();
            String itemUri = "android.resource://" + holder.itemView.getContext().getPackageName() + "/" + sonido.getResId();

            if (currentUri.equals(itemUri)) {
                isPlayingThisItem = true;
                holder.controlView.setPlayer(sharedPlayer);
            }
        }

        // MAGIA DE LA CAPA INVISIBLE
        if (isPlayingThisItem) {
            // Si está sonando, quitamos el cristal para que pueda usar el botón de pause y la barra
            holder.capaClic.setVisibility(View.GONE);
        } else {
            // Si no está sonando, ponemos el cristal para que atrape su clic en el botón de play apagado
            holder.capaClic.setVisibility(View.VISIBLE);
        }

        // CONTROL DE VOLUMEN
        if (sharedPlayer != null) {
            holder.sbVolumen.setProgress((int) (sharedPlayer.getVolume() * 100));

            holder.sbVolumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        sharedPlayer.setVolume(progress / 100f);
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }

        // HEMOS SACADO LA LÓGICA DEL CLIC A UNA VARIABLE PARA PODER ASIGNÁRSELA A LOS DOS SITIOS
        View.OnClickListener playClickListener = v -> {
            if (SplashActivity.himnoPlayer != null) {
                if (SplashActivity.himnoPlayer.isPlaying()) {
                    SplashActivity.himnoPlayer.stop();
                }
                SplashActivity.himnoPlayer.release();
                SplashActivity.himnoPlayer = null;
            }

            if (sharedPlayer != null) {
                sharedPlayer.stop();
                sharedPlayer.clearMediaItems();

                holder.controlView.setPlayer(null);

                Uri uri = Uri.parse("android.resource://" + v.getContext().getPackageName() + "/" + sonido.getResId());
                MediaItem mediaItem = new MediaItem.Builder()
                        .setUri(uri)
                        .setMediaId(String.valueOf(sonido.getResId()))
                        .build();

                sharedPlayer.setMediaItem(mediaItem);
                sharedPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
                sharedPlayer.setPlaybackParameters(new PlaybackParameters(1f));

                sharedPlayer.prepare();
                holder.controlView.setPlayer(sharedPlayer);
                sharedPlayer.play();

                // Notificamos para que la lista se repinte, quitando el cristal al nuevo y poniéndoselo al viejo
                notifyDataSetChanged();
            }
        };

        // Asignamos el clic tanto a la fila entera como a la capa invisible que hay sobre el botón
        holder.itemView.setOnClickListener(playClickListener);
        holder.capaClic.setOnClickListener(playClickListener);
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }
}