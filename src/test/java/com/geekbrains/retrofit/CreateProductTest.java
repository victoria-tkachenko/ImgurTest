package com.geekbrains.retrofit;


import com.geekbrains.retrofit.api.ProductService;
import com.geekbrains.retrofit.dto.ProductDto;
import com.geekbrains.retrofit.utils.RetrofitUtils;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;


public class CreateProductTest {
    static ProductService productService;
    ProductDto productDto;
    Faker faker = new Faker();

    int id;

    @BeforeAll
    static void beforeAll () {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp () {
        productDto = new ProductDto()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() *1000));
    }

    @Test
    @SneakyThrows
    void createProductInFoodCategoryTest () {
        Response<ProductDto> response = productService.createProduct(productDto)
                .execute();

        id = response.body().getId();
        assertThat(response.isSuccessful(),CoreMatchers.is(true));
    }

    @SneakyThrows
    @AfterEach
    void tearDown () {
        Response<ProductDto> response = productService.deleteProduct(id)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
}
