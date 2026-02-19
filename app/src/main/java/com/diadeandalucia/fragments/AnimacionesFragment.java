package com.diadeandalucia.fragments;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.diadeandalucia.R;

public class AnimacionesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animaciones, container, false);

        // Referencias de las imágenes
        ImageView anim1 = view.findViewById(R.id.anim1);
        ImageView anim2 = view.findViewById(R.id.anim2);
        ImageView anim3 = view.findViewById(R.id.anim3);
        ImageView anim4 = view.findViewById(R.id.anim4);
        ImageView anim5 = view.findViewById(R.id.anim5);

        // 1. Efecto: La peonza (Giro infinito)
        ObjectAnimator rotate = ObjectAnimator.ofFloat(anim1, "rotation", 0f, 360f);
        rotate.setDuration(2500);
        rotate.setRepeatCount(ObjectAnimator.INFINITE);
        rotate.setRepeatMode(ObjectAnimator.RESTART); //
        rotate.start();
        anim1.setOnClickListener(v -> Toast.makeText(getContext(), "¡Ojú, qué mareo!", Toast.LENGTH_SHORT).show());

        // 2. Efecto: El latido andaluz (Escalado)
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1.2f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1.2f);
        ObjectAnimator pulse = ObjectAnimator.ofPropertyValuesHolder(anim2, pvhX, pvhY);
        pulse.setDuration(800);
        pulse.setRepeatCount(ObjectAnimator.INFINITE);
        pulse.setRepeatMode(ObjectAnimator.REVERSE);
        pulse.start();
        anim2.setOnClickListener(v -> Toast.makeText(getContext(), "¡Qué coraje me da!", Toast.LENGTH_SHORT).show());

        // 3. Efecto: El fantasma de la Alhambra (Alpha/Transparencia)
        ObjectAnimator fade = ObjectAnimator.ofFloat(anim3, "alpha", 1f, 0.1f);
        fade.setDuration(1500);
        fade.setRepeatCount(ObjectAnimator.INFINITE);
        fade.setRepeatMode(ObjectAnimator.REVERSE);
        fade.start();
        anim3.setOnClickListener(v -> Toast.makeText(getContext(), "¿Me ves o no me ves, primo?", Toast.LENGTH_SHORT).show());

        // 4. Efecto: El baile de la Macarena (Movimiento lateral)
        ObjectAnimator dance = ObjectAnimator.ofFloat(anim4, "translationX", -60f, 60f);
        dance.setDuration(1000);
        dance.setRepeatCount(ObjectAnimator.INFINITE);
        dance.setRepeatMode(ObjectAnimator.REVERSE);
        dance.start();
        anim4.setOnClickListener(v -> Toast.makeText(getContext(), "¡Dale a tu cuerpo alegría!", Toast.LENGTH_SHORT).show());

        // 5. Efecto: El salto del ángel (Traslación vertical)
        ObjectAnimator jump = ObjectAnimator.ofFloat(anim5, "translationY", 0f, -120f);
        jump.setDuration(600);
        jump.setRepeatCount(ObjectAnimator.INFINITE);
        jump.setRepeatMode(ObjectAnimator.REVERSE);
        jump.start();
        anim5.setOnClickListener(v -> Toast.makeText(getContext(), "¡Arriba ese ánimo!", Toast.LENGTH_SHORT).show());

        return view;
    }
}