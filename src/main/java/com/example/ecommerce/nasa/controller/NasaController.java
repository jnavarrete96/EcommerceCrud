package com.example.ecommerce.nasa.controller;

import com.example.ecommerce.nasa.dto.NasaResponse;
import com.example.ecommerce.nasa.service.NasaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/nasa")
public class NasaController {
    private final NasaService nasaService;

    public NasaController(NasaService nasaService) {
        this.nasaService = nasaService;
    }

    @GetMapping("/apod")
    public ResponseEntity<NasaResponse> getAstronomyPictureOfTheDay(@RequestParam("date") LocalDate date) {
        NasaResponse response = nasaService.getAstronomyPictureOfTheDay(date);
        return ResponseEntity.ok(response);
    }
}
