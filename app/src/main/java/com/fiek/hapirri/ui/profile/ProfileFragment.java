package com.fiek.hapirri.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fiek.hapirri.R;
import com.fiek.hapirri.adapters.FavoritesAdapter;
import com.fiek.hapirri.constants.Constants;
import com.fiek.hapirri.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private CircleImageView profileImage;
    private TextView fullName, username, email;
    private User loggedUser;
    private RecyclerView favsRecyclerView;
    private FavoritesAdapter favoritesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = root.findViewById(R.id.profileImage);
        fullName = root.findViewById(R.id.fullName);
        username = root.findViewById(R.id.profileUsername);
        email = root.findViewById(R.id.profileEmail);
        favsRecyclerView = root.findViewById(R.id.favsRecyclerView);

        setUpProfileInfo();

        return root;
    }

    private void setUpProfileInfo(){

        final GoogleSignInAccount accountGoogle = GoogleSignIn.getLastSignedInAccount(requireActivity().getApplicationContext());
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(Constants.COLLECTION_USER).document(firebaseAuth.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        loggedUser = document.toObject(User.class);

                        String firstLastName = loggedUser.getFirstName() + " " + loggedUser.getLastName();

                        if (accountGoogle != null){
                            Picasso.get().load(accountGoogle.getPhotoUrl()).into(profileImage);
                        }

                        if (loggedUser.getFavs() != null){
                            getFavRestaurants();
                        }

                        fullName.setText(firstLastName);
                        username.setText(loggedUser.getEmail().substring(0, loggedUser.getEmail().indexOf("@")));
                        email.setText(loggedUser.getEmail());

                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    private void getFavRestaurants() {
        List<String> favsList = loggedUser.getFavs();

        favoritesAdapter = new FavoritesAdapter(requireActivity().getApplicationContext(), favsList);
        favsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
        favsRecyclerView.setHasFixedSize(true);
        favsRecyclerView.setAdapter(favoritesAdapter);
    }
}
