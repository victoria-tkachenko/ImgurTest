package com.geekbrains.retrofit.api;

import com.geekbrains.retrofit.dto.CategoryDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface CategoryService {

    @GET("market/api/v1/categories/{id}")
    Call<CategoryDto> getCategory(@Path("id") int id);
}
