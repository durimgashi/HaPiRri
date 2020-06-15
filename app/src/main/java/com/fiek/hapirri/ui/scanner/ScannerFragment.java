package com.fiek.hapirri.ui.scanner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.fiek.hapirri.MenuActivity;
import com.fiek.hapirri.R;
import com.fiek.hapirri.ScannerActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Scanner;

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



