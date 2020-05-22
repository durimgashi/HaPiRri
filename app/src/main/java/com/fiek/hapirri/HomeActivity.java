package com.fiek.hapirri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;

import com.fiek.hapirri.adapters.RestaurantAdapter;
import com.fiek.hapirri.model.Item;
import com.fiek.hapirri.model.Restaurant;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements RestaurantAdapter.OnItemClickListener {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference restaurantRef = db.collection("restaurant");
    private RestaurantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu);
        return true;
    }

    private void setUpRecyclerView(){
        Query query = restaurantRef.orderBy("restName", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Restaurant> options = new FirestoreRecyclerOptions.Builder<Restaurant>()
                .setQuery(query, Restaurant.class)
                .build();
        adapter = new RestaurantAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.restaurantBrowseRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);
                //Gets ID of document from the firestore database
                String id = documentSnapshot.getId();

                Intent intent = new Intent(getApplicationContext(), RestaurantViewActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("restName", restaurant.getRestName());
                intent.putExtra("address", restaurant.getAddress());
                intent.putExtra("description", restaurant.getDescription());
                intent.putExtra("image", restaurant.getImage());

                List<Item> menu = restaurant.getMenu();
                intent.putParcelableArrayListExtra("menu", (ArrayList<? extends Parcelable>) menu);
                List<String> gallery = restaurant.getGallery();
                intent.putStringArrayListExtra("gallery", (ArrayList<String>) gallery);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

    }
}
