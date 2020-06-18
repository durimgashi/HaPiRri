package com.fiek.hapirri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fiek.hapirri.adapters.CommentAdapter;
import com.fiek.hapirri.model.Comment;
import com.fiek.hapirri.model.Restaurant;
import com.fiek.hapirri.sqllite.DatabaseHelper;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    private EditText ed2;
    private DatabaseHelper databaseHelper;
    private ListView listView1;
    private ArrayList<Comment> arrayList;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        ed2 = findViewById(R.id.editText2);
        listView1 = (ListView) findViewById(R.id.listView1);
        databaseHelper = new DatabaseHelper(this);
        arrayList = new ArrayList<>();
        loadDataInListView();
    }
    public void loadDataInListView(){
        arrayList = databaseHelper.getAllData();
        adapter = new CommentAdapter(this,arrayList);
        listView1.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void insert(View v)
    {
        Restaurant restaurant = getIntent().getParcelableExtra("restaurant");

        assert restaurant != null;
        boolean result = databaseHelper.insertData(restaurant.getRestName(),ed2.getText().toString());

        if (result){
            Toast.makeText(getApplicationContext(),"Comment added",Toast.LENGTH_SHORT).show();
            loadDataInListView();
        }
        else
            Toast.makeText(getApplicationContext(),"Failed to save comment",Toast.LENGTH_SHORT).show();
    }
}