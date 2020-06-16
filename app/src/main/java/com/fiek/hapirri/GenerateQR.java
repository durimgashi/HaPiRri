package com.fiek.hapirri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;




public class GenerateQR extends AppCompatActivity {

    //Context mContext = this;
    EditText qrvalue;
    Button generateQRcode, saveQR;
    ImageView qrImage;
    Bitmap bitmap;
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    private AppCompatActivity activity = this;
    private static final int PERMISSION_ALL = 1;
    private static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_q_r);

        qrvalue = findViewById(R.id.qrInput);
        generateQRcode = findViewById(R.id.generateBtn);
        saveQR = findViewById(R.id.saveQR);
        qrImage = findViewById(R.id.qrPlaceHolder);


        generateQRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = qrvalue.getText().toString();
                if(data.isEmpty()){
                    qrvalue.setError("Value Required.");
                }else {
                    QRGEncoder qrgEncoder = new QRGEncoder(data,null, QRGContents.Type.TEXT,500);
                    bitmap = qrgEncoder.getBitmap();
                    qrImage.setImageBitmap(bitmap);
                }
            }
        });

        findViewById(R.id.saveQR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


                    String root = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
                    File myDir = new File(root);
                    myDir.mkdirs();
                    Random generator = new Random();
                    int n = 10000;
                    n = generator.nextInt(n);
                    String fname = "Image-" + n + ".jpg";
                    File file = new File(myDir, fname);
                    if (file.exists()) file.delete();
                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        Toast.makeText(activity, "QR Code saved to Gallery", Toast.LENGTH_LONG).show();
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        String rezultati = e.getMessage();
                        Toast.makeText(activity, rezultati, Toast.LENGTH_LONG).show();
                    }
                } else {
                    ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
                }
            }
        });


    }
}
