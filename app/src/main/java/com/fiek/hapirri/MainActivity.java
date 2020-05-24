package com.fiek.hapirri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.fiek.hapirri.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    TextView redirectLogin;
    EditText firstNameReg;
    EditText lastNameReg;
    EditText ageReg;
    EditText usernameReg;
    EditText emailReg;
    EditText passwordReg;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
