package com.geekbrains.retrofit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@With
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Integer id;
    private String title;
    @JsonProperty ("products")
    private List<ProductDto> products = new ArrayList<>();

    public String toString() {
        return products.toString();
    }



}
