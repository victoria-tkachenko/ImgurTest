package com.geekbrains.retrofit.utils;

import com.geekbrains.retrofit.api.MiniMarketService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public static MiniMarketService getService() {

        return new Retrofit.Builder()
                .baseUrl("http://localhost:8189/market/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MiniMarketService.class);
    }
}
