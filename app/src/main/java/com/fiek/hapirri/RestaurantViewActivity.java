package com.fiek.hapirri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.fiek.hapirri.adapters.GalleryAdapter;
import com.fiek.hapirri.constants.Constants;
import com.fiek.hapirri.model.Restaurant;
import com.fiek.hapirri.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.List;

public class RestaurantViewActivity extends AppCompatActivity {

    private AppBarLayout restDetailsImage;
    private TextView restDetailsAddress, restDetailsDescription;
    private GridView galleryGridView;
    private Button goToMenu, goToLocationButton, generateQR;
    private FloatingActionButton fab;
    private Restaurant restaurant;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private DocumentReference ref = rootRef.collection(Constants.COLLECTION_USER).document(firebaseUser.getUid());

    private boolean isFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_view);

        restDetailsImage = findViewById(R.id.restDetailsImage);
        restDetailsAddress = findViewById(R.id.restDetailsAddress);
        restDetailsDescription = findViewById(R.id.restDetailsDescription);
        galleryGridView = findViewById(R.id.galleryGridView);
        goToMenu = findViewById(R.id.goToMenuButton);

        //Checks if restaurant is in the favorites of the user and sets the right icon
        checkFavs();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFavorite(view);
            }
        });

        //Gets restaurant object from intent
        manageExtras();

        goToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra("restaurant", restaurant);
            startActivity(intent);
            }
        });

        generateQR = findViewById(R.id.gotoQR);
        generateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GenerateQR.class);
                startActivity(intent);
            }
        });


        goToLocationButton = findViewById(R.id.goToLocationButton);
        goToLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            intent.putExtra("restaurant", restaurant);
            startActivity(intent);
            }
        });
    }

    public void manageExtras(){
        restaurant = getIntent().getParcelableExtra("restaurant");

        restDetailsDescription.setText(restaurant.getDescription());
        restDetailsAddress.setText(restaurant.getAddress());
        if (restaurant.getGallery() != null){
            GalleryAdapter galleryAdapter = new GalleryAdapter(RestaurantViewActivity.this, restaurant.getGallery());
            galleryGridView.setAdapter(galleryAdapter);
        }else{
            galleryGridView.setVisibility(View.GONE);
        }
        setImage(restaurant.getImage());
    }

    //Sets the restaurant logo image as a BACKGROUND image(less code for ImageView)
    public void setImage(String image){
        Picasso.get().load(Uri.parse(image)).into(new Target(){
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                restDetailsImage.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//                Toast.makeText(RestaurantViewActivity.this, "Could not load image!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
//                Toast.makeText(RestaurantViewActivity.this, "Loading image!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void toggleFavorite(View view){
        if(isFav){
            ref.update("favs", FieldValue.arrayRemove(getIntent().getExtras().getString("restId")));
            Snackbar.make(view, "Restaurant removed from favorites", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            fab.setImageResource(R.drawable.ic_favorite_empty);
        }else{
            ref.update("favs", FieldValue.arrayUnion(getIntent().getExtras().getString("restId")));
            Snackbar.make(view, "Restaurant added to favorites", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
        }

        isFav = !isFav;
    }

    public void checkFavs(){
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        User user = document.toObject(User.class);
                        List<String> favs = user.getFavs();
                        if (favs != null && favs.contains(getIntent().getExtras().getString("restId"))){
                            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
                            isFav = true;
                        }else{
                            fab.setImageResource(R.drawable.ic_favorite_empty);
                        }
                    } else {
                        Log.d("LOGGER", "Document null", task.getException());
                    }
                } else {
                    Log.d("LOGGER", "Task failed", task.getException());
                }
            }
        });
    }
}
