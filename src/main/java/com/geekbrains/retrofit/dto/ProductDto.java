package com.geekbrains.retrofit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@With
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private int price;

    @JsonProperty("categoryTitle")
    private String categoryTitle;

//    public ProductDto withId(Integer id) {
//        this.id = id;
//        return this;
//    }
//
//    public ProductDto withTitle(String title) {
//        this.title = title;
//        return this;
//    }
//
//    public ProductDto withPrice(Integer price) {
//        this.price = price;
//        return this;
//    }
//
//    public ProductDto withCategoryTitle(String categoryTitle) {
//        this.categoryTitle = categoryTitle;
//        return this;
//    }
}
