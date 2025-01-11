package com.example.ecommerce.nasa.controller;

import com.example.ecommerce.nasa.service.NasaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/nasa")
public class NasaController {
    private final NasaServiceImpl nasaService;

    public NasaController(NasaServiceImpl nasaService) {
        this.nasaService = nasaService;
    }

    @GetMapping("/apod")
    public ResponseEntity<String> getAstronomyPictureOfTheDay(@RequestParam("date") LocalDate date) {
        String response = nasaService.getAstronomyPictureOfTheDay(date);
        return ResponseEntity.ok(response);
    }
}
