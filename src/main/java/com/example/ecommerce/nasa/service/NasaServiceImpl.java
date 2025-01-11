package com.example.ecommerce.nasa.service;

import com.example.ecommerce.nasa.config.NasaConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class NasaServiceImpl {
    private final NasaConfig nasaConfig;
    private final RestTemplate restTemplate;

    public NasaServiceImpl(NasaConfig nasaConfig, RestTemplate restTemplate) {
        this.nasaConfig = nasaConfig;
        this.restTemplate = restTemplate;
    }

    public String getAstronomyPictureOfTheDay(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        String url = "https://api.nasa.gov/planetary/apod?api_key=" + nasaConfig.getApiKey() + "&date="+ formattedDate;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
