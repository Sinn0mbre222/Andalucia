package com.diadeandalucia.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.diadeandalucia.R;

public class VideosFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);

        setupVideo(view.findViewById(R.id.videoView1), R.raw.splash_video);
        setupVideo(view.findViewById(R.id.videoView2), R.raw.quemequedosincomer);
        setupVideo(view.findViewById(R.id.videoView3), R.raw.elriounamierda);

        return view;
    }

    private void setupVideo(VideoView videoView, int videoResId) {
        // Ruta del recurso
        String path = "android.resource://" + requireContext().getPackageName() + "/" + videoResId;
        videoView.setVideoURI(Uri.parse(path));

        // Añadir controles multimedia (Play, Pause, etc.) como pide el PDF
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }
}