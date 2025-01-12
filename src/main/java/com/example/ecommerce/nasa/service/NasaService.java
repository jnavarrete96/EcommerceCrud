package com.example.ecommerce.nasa.service;

import com.example.ecommerce.nasa.dto.NasaResponse;

import java.time.LocalDate;

public interface NasaService {
    NasaResponse getAstronomyPictureOfTheDay(LocalDate date);
}
