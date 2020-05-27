package com.fiek.hapirri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
<<<<<<< Updated upstream
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
=======
import com.google.android.gms.common.SignInButton;
>>>>>>> Stashed changes

public class LoginActivity extends AppCompatActivity {

    private TextView redirectRegister;
    private SignInButton googleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

<<<<<<< Updated upstream
        loggedInAs = findViewById(R.id.loggedInAs);
=======
        initializeGoogleButton();
>>>>>>> Stashed changes

        redirectRegister = findViewById(R.id.redirectRegister);
        redirectRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            }
        });
    }

    public void initializeGoogleButton(){
        googleButton = findViewById(R.id.google_button);

        for (int i = 0; i < googleButton.getChildCount(); i++) {
            View v = googleButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setTextSize(15);
                tv.setTypeface(null, Typeface.NORMAL);
                tv.setText("LOG IN WITH GOOGLE");
                tv.setSingleLine(true);
                tv.setPadding(15, 15, 15, 15);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


}
