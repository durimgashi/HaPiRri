package com.fiek.hapirri.retrofit;

import com.fiek.hapirri.model.ReviewModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonAPI {

    String BASE_URL = "https://simplifiedcoding.net/demos/";

    @GET("marvel")
    Call<List<ReviewModel>> getReviews();

}
