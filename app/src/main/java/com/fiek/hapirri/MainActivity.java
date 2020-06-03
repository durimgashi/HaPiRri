package com.fiek.hapirri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.fiek.hapirri.constants.Constants;
import com.fiek.hapirri.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private TextView redirectLogin;
    private EditText firstNameReg, lastNameReg, ageReg, usernameReg, emailReg, passwordReg;
    private SignInButton googleButton;
    private GoogleSignInClient googleSignInClient;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private int RC_SIGN_IN = 1;
    private FirebaseFirestore db;
    String userId;

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

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        progressBar = findViewById(R.id.progressBar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authWithGoogle();
            }
        });

        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, WelcActivity.class));
            finish();
        }

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailReg.getText().toString();
                String password = passwordReg.getText().toString();
                final String firstName = firstNameReg.getText().toString();
                final String lastName = lastNameReg.getText().toString();
                final String username = usernameReg.getText().toString();
                final Integer age = Integer.parseInt(ageReg.getText().toString());


                if (TextUtils.isEmpty(email)){
                    emailReg.setError("Email is empty!");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    passwordReg.setError("Email is empty!");
                }
                if(password.length() < 6){
                    passwordReg.setError("Password must be at least 6 characters long.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register the user
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    //Saving user to collection
                                    userId = firebaseAuth.getCurrentUser().getUid();
                                    User user = new User(firstName, lastName, age,  username, firebaseAuth.getCurrentUser().getEmail(), null);
                                    saveUserToFirestore(user, userId);

                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                }else{
                                    Toast.makeText(MainActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                });
            }
        });


        redirectLogin = findViewById(R.id.redirectLogin);
        redirectLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
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
                tv.setText("SIGN UP WITH GOOGLE");
                tv.setSingleLine(true);
                tv.setPadding(15, 15, 15, 15);
            }
        }
    }

    public void authWithGoogle(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(getApplicationContext(), "Google sign in succesful", Toast.LENGTH_SHORT).show();
            assert acc != null;
            firebaseGoogleAuth(acc);
        }catch (ApiException e){
            Toast.makeText(getApplicationContext(), "Google sign in failed " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("FUCK", "handleSignInResult: " + completedTask.getException());
        }
    }

    public void firebaseGoogleAuth(GoogleSignInAccount account){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

                    User googleUser = new User(account.getGivenName(), account.getFamilyName(), null, account.getDisplayName(),account.getEmail(), null);
                    saveUserToFirestore(googleUser, user.getUid());

                    startActivity(new Intent(getApplicationContext(), WelcActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveUserToFirestore(User user, String userId){
        DocumentReference documentReference = db.collection(Constants.COLLECTION_USER).document(userId);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "User created", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
