package com.fiek.hapirri;

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
import com.fiek.hapirri.adapters.GalleryAdapter;
import com.fiek.hapirri.model.Item;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.List;

public class RestaurantViewActivity extends AppCompatActivity {

    AppBarLayout restDetailsImage;
    TextView restDetailsAddress, restDetailsDescription;
    GridView galleryGridView;
    Button goToMenu;
    FloatingActionButton fab;
    String restId, restName;

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

        //Gets bundle extras from intent and sets images to gallery
        manageExtras();
        goToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra("restId", restId);
                intent.putExtra("restName", restName);
                startActivity(intent);
            }
        });
    }

    public void manageExtras(){
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            restId = extras.getString("id");
            restName = extras.getString("restName");
            String description = extras.getString("description");
            String address = extras.getString("address");
            String image = extras.getString("image");
            List<Item> menu = extras.getParcelableArrayList("menu");
            List<String> gallery = extras.getStringArrayList("gallery");

            restDetailsDescription.setText(description);
            restDetailsAddress.setText(address);
            GalleryAdapter galleryAdapter = new GalleryAdapter(RestaurantViewActivity.this, gallery);
            if (gallery != null){
                galleryGridView.setAdapter(galleryAdapter);
            }else{
                galleryGridView.setVisibility(View.GONE);
            }
            setImage(image);
        }


    }
    public void setImage(String image){
        Picasso.get().load(Uri.parse(image)).into(new Target(){
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                restDetailsImage.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.d("TAG", "Prepare Load");
            }
        });
    }
}
