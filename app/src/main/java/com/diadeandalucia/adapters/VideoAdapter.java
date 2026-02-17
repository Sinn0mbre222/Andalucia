package com.diadeandalucia.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.diadeandalucia.R;
import com.diadeandalucia.models.Video;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<Video> listaVideos;

    public VideoAdapter(List<Video> listaVideos) {
        this.listaVideos = listaVideos;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = listaVideos.get(position);
        holder.tvTitulo.setText(video.getTitulo());

        String path = "android.resource://" + holder.itemView.getContext().getPackageName() + "/" + video.getVideoResId();
        holder.videoView.setVideoURI(Uri.parse(path));

        MediaController mc = new MediaController(holder.itemView.getContext());
        mc.setAnchorView(holder.videoView);
        holder.videoView.setMediaController(mc);
    }

    @Override
    public int getItemCount() {
        return listaVideos.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo;
        VideoView videoView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTituloVideo);
            videoView = itemView.findViewById(R.id.videoViewItem);
        }
    }
}