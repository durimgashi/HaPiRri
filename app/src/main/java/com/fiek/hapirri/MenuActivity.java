package com.fiek.hapirri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;
import com.fiek.hapirri.adapters.MenuAdapter;
import com.fiek.hapirri.model.Item;
import com.fiek.hapirri.model.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference restRef = db.collection("restaurant");

    RecyclerView menuRecyclerView;
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        String restId = extras.getString("restId");
        String restName = extras.getString("restName");

        getRest(restName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu);
        return true;
    }

    public void getRest(String restName){
        Query query = restRef.whereEqualTo("restName", restName);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<Restaurant> listRest = new ArrayList<>();
                    for (QueryDocumentSnapshot document: task.getResult()){
                        restaurant = document.toObject(Restaurant.class);
                        listRest.add(restaurant);
                    }
                    List<Item> itemList = listRest.get(0).getMenu();
                    MenuAdapter menuAdapter = new MenuAdapter(getApplicationContext(), itemList);
                    menuRecyclerView = findViewById(R.id.menuRecyclerView);
                    menuRecyclerView.setAdapter(menuAdapter);
                    menuRecyclerView.setHasFixedSize(true);
                    menuRecyclerView.setLayoutManager(new LinearLayoutManager(MenuActivity.this));
                }else{
                    Toast.makeText(getApplicationContext(), "Query failed", Toast.LENGTH_LONG ).show();
                }
            }
        });

    }
}
