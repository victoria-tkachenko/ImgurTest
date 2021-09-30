package com.geekbrains.retrofit.api;

import com.geekbrains.retrofit.dto.GetCategoryResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface CategoryService {

    @GET("market/api/v1/categories/{id}")
    Call<GetCategoryResponse> getCategory(@Path("id") int id);
}
