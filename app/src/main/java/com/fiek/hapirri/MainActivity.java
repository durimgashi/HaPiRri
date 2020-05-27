package com.fiek.hapirri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fiek.hapirri.model.User;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
<<<<<<< Updated upstream

    TextView redirectLogin;

    public static final String KEY_FIRSTNAME = "firstName";
    public static final String KEY_LASTNAME = "lastName";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_AGE = "age";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    EditText firstNameReg;
    EditText lastNameReg;
    EditText ageReg;
    EditText usernameReg;
    EditText emailReg;
    EditText passwordReg;

    ImageView registerImageView;

=======
    private TextView redirectLogin;
    private EditText firstNameReg;
    private EditText lastNameReg;
    private EditText ageReg;
    private EditText usernameReg;
    private EditText emailReg;
    private EditText passwordReg;
    private SignInButton googleButton;
>>>>>>> Stashed changes
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< Updated upstream
//        registerImageView = findViewById(R.id.registerImageView);
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/hapirri-6970c.appspot.com/o/restImages%2Fgizigrill.jpg?alt=media&token=d9ebe396-904c-4714-bda3-0b8fac763890").into(registerImageView);
=======
        initializeGoogleButton();
>>>>>>> Stashed changes

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

//        Map<String, Object> user = new HashMap<>();

//        user.put(KEY_FIRSTNAME, firstName);
//        user.put(KEY_LASTNAME, lastName);
//        user.put(KEY_AGE, age);
//        user.put(KEY_USERNAME, username);
//        user.put(KEY_EMAIL, email);
//        user.put(KEY_PASSWORD, password);

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
