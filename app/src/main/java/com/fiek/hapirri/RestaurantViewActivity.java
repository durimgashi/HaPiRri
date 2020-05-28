package com.fiek.hapirri;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.fiek.hapirri.adapters.GalleryAdapter;
import com.fiek.hapirri.model.Restaurant;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class RestaurantViewActivity extends AppCompatActivity {

    private AppBarLayout restDetailsImage;
    private TextView restDetailsAddress, restDetailsDescription;
    private GridView galleryGridView;
    private Button goToMenu;
    private Button goToLocationButton;
    private FloatingActionButton fab;

    Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);

        restDetailsImage = findViewById(R.id.restDetailsImage);
        restDetailsAddress = findViewById(R.id.restDetailsAddress);
        restDetailsDescription = findViewById(R.id.restDetailsDescription);
        galleryGridView = findViewById(R.id.galleryGridView);
        goToMenu = findViewById(R.id.goToMenuButton);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Restaurant added to favorites", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
                Toast.makeText(RestaurantViewActivity.this, "Could not load image!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Toast.makeText(RestaurantViewActivity.this, "Loading image!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
