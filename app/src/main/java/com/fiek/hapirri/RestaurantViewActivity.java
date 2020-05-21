package com.fiek.hapirri;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView restDetailsAddress;
    TextView restDetailsDescription;
    GridView galleryGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Restaurant added to favorites", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        restDetailsImage = findViewById(R.id.restDetailsImage);
        restDetailsAddress = findViewById(R.id.restDetailsAddress);
        restDetailsDescription = findViewById(R.id.restDetailsDescription);
        galleryGridView = findViewById(R.id.galleryGridView);
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            int id = extras.getInt("id");
            String name = extras.getString("restName");
            String description = extras.getString("description");
            String address = extras.getString("address");
            String image = extras.getString("image");
            List<Item> menu = extras.getParcelableArrayList("menu");
            List<String> gallery = extras.getStringArrayList("gallery");

            restDetailsDescription.setText(description);
            restDetailsAddress.setText(address);

            if (gallery != null){
                GalleryAdapter galleryAdapter = new GalleryAdapter(getApplicationContext(), gallery);
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
