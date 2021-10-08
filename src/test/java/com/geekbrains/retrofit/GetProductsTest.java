package com.geekbrains.retrofit;

import com.geekbrains.retrofit.api.ProductService;
import com.geekbrains.retrofit.dto.ProductDto;
import com.geekbrains.retrofit.utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;

public class GetProductsTest {

    static ProductService productService;


    @BeforeAll
    static void beforeAll () {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @Test
    @SneakyThrows
    void getProductsInFoodCategoryTest () {
        Response<ProductDto> response = productService.getProducts()
                .execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));

//        System.out.println(response.body());
    }
}
