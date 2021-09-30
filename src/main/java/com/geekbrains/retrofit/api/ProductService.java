package com.geekbrains.retrofit.api;

import com.geekbrains.retrofit.dto.ProductDto;
import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.*;

public interface ProductService {

    @GET("market/api/v1/products")
    Call<ProductDto> getProducts();

    @POST("market/api/v1/products")
    Call<ProductDto> createProduct (@Body ProductDto createProductRequest);

    @PUT("market/api/v1/products")
    Call<ProductDto> modifyProduct();

    @GET("market/api/v1/products/{id}")
    Call<ProductDto> getProduct(@Path("id") int id);

    @DELETE("market/api/v1/products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);
}
