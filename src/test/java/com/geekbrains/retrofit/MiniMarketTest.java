package com.geekbrains.retrofit;

import com.geekbrains.retrofit.api.MiniMarketService;
import com.geekbrains.retrofit.dto.CategoryDto;
import com.geekbrains.retrofit.dto.ProductDto;
import com.geekbrains.retrofit.utils.RetrofitHelper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.github.reflectionassert.ReflectionAssertions.assertReflectiveThat;

public class MiniMarketTest {

    private static MiniMarketService service;
    ProductDto productDto;
    Faker faker = new Faker();

    @BeforeAll
    static void beforeAll() {
        service = RetrofitHelper.getService();
    }

    @Test
    @DisplayName("Получение продукта по ID")
    void testGetProductById() throws IOException {
        ProductDto actually = service.getProduct(1).execute().body();
        ProductDto expected = ProductDto.builder()
                .id(1)
                .title("Chives")
                .price(490)
                .categoryTitle("Food")
                .build();
        assertReflectiveThat(actually).isEqualTo(expected);
    }

//    @Test
//    void testGetProducts() throws IOException {
//        ProductDto productDto = service.getProducts().execute();
//    }

    @Test
    @DisplayName("Создание продукта позитивный")
    void testCreateProductPositive() throws IOException {
        productDto = new ProductDto()
                .withId(5)
                .withTitle("Cheese")
                .withCategoryTitle("Food")
                .withPrice(200);

        ProductDto actually = service.createProduct(productDto).execute().body();
        ProductDto expected = ProductDto.builder()
                .id(5)
                .title("Cheese")
                .price(200)
                .categoryTitle("Food")
                .build();
        assertReflectiveThat(actually).isEqualTo(expected);
    }

    @Test
    @DisplayName("Получить категорию по ID позитивный")
    void testGetCategoryByIdPositive() throws IOException {
        CategoryDto actually = service.getCategory(1).execute().body();

        CategoryDto expected = CategoryDto.builder()
                .id(1)
                .build();
        assertReflectiveThat(actually).isEqualTo(expected);


    }

}
