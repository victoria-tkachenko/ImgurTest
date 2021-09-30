package com.geekbrains.retrofit;

import com.geekbrains.retrofit.api.ProductService;
import com.geekbrains.retrofit.dto.ProductDto;
import com.geekbrains.retrofit.utils.RetrofitUtils;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;

public class ModifyProductTest {

    static ProductService productService;
    ProductDto productDto;
    Faker faker = new Faker();

    String title;

    @BeforeAll
    static void beforeAll () {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void modify () {
        productDto = new ProductDto()
                .withId(1)
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() *1000));
    }

    @Test
    @SneakyThrows
    void modifyProductInFoodCategoryTest () {
        Response<ProductDto> response = productService.modifyProduct(productDto)
                .execute();

        title = response.body().getTitle();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        System.out.println(response.body().getTitle());
    }

}
