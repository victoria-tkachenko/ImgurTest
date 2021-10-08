package com.geekbrains.retrofit;

import com.geekbrains.retrofit.api.CategoryService;
import com.geekbrains.retrofit.dto.CategoryDto;
import com.geekbrains.retrofit.utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetCategoryTest {
    static CategoryService categoryService;

    @BeforeAll
    static void beforeAll() {
        categoryService =
                RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @Test
    void getCategoryByIdPositiveTest () {
        Response<CategoryDto> response = categoryService.getCategory(1).execute();

        System.out.println(response.body());

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(1));
        assertThat(response.body().getTitle(), equalTo("Food"));

    }

    @SneakyThrows
    @Test
    void getCategoryByIdNegativeTest () {
        Response<CategoryDto> response = categoryService.getCategory(9).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(false));

    }
}
