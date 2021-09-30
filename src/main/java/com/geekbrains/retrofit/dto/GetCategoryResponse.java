package com.geekbrains.retrofit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetCategoryResponse {

    private Integer id;
    private String title;
    @JsonProperty ("products")
    private List<ProductDto> products = new ArrayList<>();

}
