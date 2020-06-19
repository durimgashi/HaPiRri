package com.fiek.hapirri.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.fiek.hapirri.R;
import com.fiek.hapirri.model.ReviewModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitJSON extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_j_s_o_n);

        final ListView listView = findViewById(R.id.viewReviews);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JsonAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonAPI api = retrofit.create(JsonAPI.class);

        Call<List<ReviewModel>> call = api.getReviews();

        call.enqueue(new Callback<List<ReviewModel>>() {
            @Override
            public void onResponse(Call<List<ReviewModel>> call, Response<List<ReviewModel>> response) {
                List<ReviewModel> review = response.body();

                String [] reviewList = new String[review.size()];
                for(int i=0; i<review.size(); i++) {
                    reviewList[i] = review.get(i).getName();
                }

                listView.setAdapter(
                    new ArrayAdapter<>(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            reviewList
                    )
                );
            }

            @Override
            public void onFailure(Call<List<ReviewModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
