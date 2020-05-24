package com.fiek.hapirri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import com.fiek.hapirri.adapters.MenuAdapter;
import com.fiek.hapirri.model.Item;
import com.fiek.hapirri.model.Restaurant;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    RecyclerView menuRecyclerView;
    MenuAdapter menuAdapter;
    Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        restaurant = getIntent().getParcelableExtra("restaurant");

        assert restaurant != null;
        getRest(restaurant.getMenu());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu);
        return true;
    }

    public void getRest(List<Item> menu){
        menuAdapter = new MenuAdapter(getApplicationContext(), menu);
        menuRecyclerView = findViewById(R.id.menuRecyclerView);
        menuRecyclerView.setAdapter(menuAdapter);
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(MenuActivity.this));
    }
}