package com.fiek.hapirri.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.fiek.hapirri.HomeActivity;
import com.fiek.hapirri.R;

public class WelcomeFragment extends Fragment {

    private WelcomeViewModel welcomeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) {
        welcomeViewModel = ViewModelProviders.of(this).get(WelcomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_welcome, container, false);

        Button goHome = root.findViewById(R.id.goHome);
        ImageView welcomeImage = root.findViewById(R.id.welcomeIcon);

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity().getApplicationContext(), HomeActivity.class));
            }
        });

        welcomeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(requireActivity().getApplicationContext(), R.animator.shake));
            }
        });
        return root;
    }
}
