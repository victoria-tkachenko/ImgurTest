package com.geekbrains.retrofit;

import com.geekbrains.retrofit.api.ProductService;
import com.geekbrains.retrofit.dto.ProductDto;
import com.geekbrains.retrofit.utils.RetrofitUtils;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetProductTest {

    static ProductService productService;

    String title;

    @BeforeAll
    static void beforeAll () {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @Test
    @SneakyThrows
    void getProductInFoodCategoryTest () {
        Response<ProductDto> response = productService.getProduct(1)
                .execute();

        title = response.body().getTitle();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        assertThat(response.body().getTitle(), equalTo("Milk"));

        System.out.println(response.body().getTitle());
    }

}
