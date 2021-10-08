package com.geekbrains.retrofit.api;

import com.geekbrains.retrofit.dto.CategoryDto;
import com.geekbrains.retrofit.dto.ProductDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MiniMarketService {
    @GET("api/v1/products/{id}")
    Call<ProductDto> getProduct(@Path("id") int id);

    @GET("api/v1/products")
    Call<List<ProductDto>> getProducts();

    @POST("api/v1/products")
    Call<ProductDto> createProduct(@Body ProductDto dto);

    @PUT("market/api/v1/products")
    Call<ProductDto> modifyProduct(@Body ProductDto modifyProductRequest);

    @DELETE("market/api/v1/products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

    @GET("market/api/v1/categories/{id}")
    Call<CategoryDto> getCategory(@Path("id") int id);
}
