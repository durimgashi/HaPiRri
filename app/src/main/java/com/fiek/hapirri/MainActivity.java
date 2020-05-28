package com.fiek.hapirri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.fiek.hapirri.model.User;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private TextView redirectLogin;
    private EditText firstNameReg;
    private EditText lastNameReg;
    private EditText ageReg;
    private EditText usernameReg;
    private EditText emailReg;
    private EditText passwordReg;
    private SignInButton googleButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeGoogleButton();

        firstNameReg = findViewById(R.id.firstNameReg);
        lastNameReg = findViewById(R.id.lastNameReg);
        ageReg = findViewById(R.id.ageReg);
        usernameReg = findViewById(R.id.usernameReg);
        emailReg = findViewById(R.id.emailReg);
        passwordReg = findViewById(R.id.passwordReg);

        Button registerButton = findViewById(R.id.registerButton);

        redirectLogin = findViewById(R.id.redirectLogin);
        redirectLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        Button skipBtn = findViewById(R.id.skipBtn);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });
    }

    public void saveUser(View v){
        String firstName = firstNameReg.getText().toString();
        String lastName = lastNameReg.getText().toString();
        String age = ageReg.getText().toString();
        String username = usernameReg.getText().toString();
        String email = emailReg.getText().toString();
        String password = passwordReg.getText().toString();

        User user = new User(firstName, lastName, Integer.parseInt(age), username, email, password);

        db.collection("user").document().set(user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(MainActivity.this, "User saved", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
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
                tv.setText("SIGN UP WITH GOOGLE");
                tv.setSingleLine(true);
                tv.setPadding(15, 15, 15, 15);
            }
        }
    }
}
