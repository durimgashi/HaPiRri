package com.fiek.hapirri.ui.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.fiek.hapirri.R;
import com.fiek.hapirri.ScannerActivity;

public class ScannerFragment extends Fragment {

    private ScannerViewModel scannerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scannerViewModel =
                ViewModelProviders.of(this).get(ScannerViewModel.class);
        View root = inflater.inflate(R.layout.activity_scanner, container, false);

        Intent intent = new Intent(requireActivity(), ScannerActivity.class);
        startActivity(intent);


        return root;
    }


    }



