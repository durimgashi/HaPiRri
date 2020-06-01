package com.fiek.hapirri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.GridView;
import com.fiek.hapirri.adapters.GridMenuAdapter;
import com.fiek.hapirri.model.Item;
import com.fiek.hapirri.model.Restaurant;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private GridView menuGridView;
    private GridMenuAdapter gridMenuAdapter ;
    private Restaurant restaurant;
    private List<Item> itemList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        menuGridView = findViewById(R.id.menuGridView);

        restaurant = getIntent().getParcelableExtra("restaurant");
        assert restaurant != null;

        itemList =  restaurant.getMenu();

        gridMenuAdapter = new GridMenuAdapter(this, itemList);
        menuGridView.setAdapter(gridMenuAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_menu);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                gridMenuAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }
}