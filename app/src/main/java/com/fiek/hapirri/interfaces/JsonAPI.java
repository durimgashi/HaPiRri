package com.fiek.hapirri.interfaces;

import com.fiek.hapirri.model.ReviewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonAPI {

    public String BASE_URL = "https://simplifiedcoding.net/demos/";

    @GET("marvel")
    Call<List<ReviewModel>> getReviews();

}
