package com.example.ecommerce.nasa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NasaResponse {
    private String title;
    private String description;
    private String photo;
}
