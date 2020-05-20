package com.fiek.hapirri;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.fiek.hapirri.model.Item;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RestaurantViewActivity extends AppCompatActivity {

    AppBarLayout restDetailsImage;
    TextView restDetailsAddress;
    TextView restDetailsDescription;

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

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            int id = extras.getInt("id");
            String name = extras.getString("restName");
            String description = extras.getString("description");
            String address = extras.getString("address");
            String image = extras.getString("image");

           List<Item> menu = extras.getParcelableArrayList("menu");

            assert menu != null;
            Toast.makeText(this, menu.get(1).getItemName() , Toast.LENGTH_SHORT).show();

            restDetailsDescription.setText(description);
            restDetailsAddress.setText(address);

            //YEAAAA BITCH, MAGNETSS
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
}
