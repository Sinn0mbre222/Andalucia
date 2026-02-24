package com.diadeandalucia.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerControlView;
import androidx.recyclerview.widget.RecyclerView;

import com.diadeandalucia.R;
import com.diadeandalucia.activities.SplashActivity;
import com.diadeandalucia.models.Sonido;

import java.util.List;

@UnstableApi
public class SonidosAdapter extends RecyclerView.Adapter<SonidosAdapter.SonidoViewHolder> {

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

        // Lógica de la capa invisible para atrapar el clic sobre el botón de play
        if (isPlayingThisItem) {
            holder.capaClic.setVisibility(View.GONE);
        } else {
            holder.capaClic.setVisibility(View.VISIBLE);
        }

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

        View.OnClickListener playClickListener = v -> {
            // Detener himno del Splash si existe
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

                // Refrescamos la lista para mover el reproductor visual al ítem correcto
                notifyDataSetChanged();
            }
        };

        holder.itemView.setOnClickListener(playClickListener);
        holder.capaClic.setOnClickListener(playClickListener);
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    public static class SonidoViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitulo;
        public PlayerControlView controlView;
        public SeekBar sbVolumen;
        public View capaClic;

        public SonidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTituloSonido);
            controlView = itemView.findViewById(R.id.exoController);
            sbVolumen = itemView.findViewById(R.id.seekBarVolumen);
            capaClic = itemView.findViewById(R.id.capaClic);
        }
    }
}