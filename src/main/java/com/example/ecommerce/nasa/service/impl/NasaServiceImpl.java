package com.example.ecommerce.nasa.service.impl;

import com.example.ecommerce.exception.GeneralException;
import com.example.ecommerce.nasa.config.NasaConfig;
import com.example.ecommerce.nasa.dto.NasaResponse;
import com.example.ecommerce.nasa.service.NasaService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class NasaServiceImpl implements NasaService {
    private final NasaConfig nasaConfig;
    private final RestTemplate restTemplate;

    public NasaServiceImpl(NasaConfig nasaConfig, RestTemplate restTemplate) {
        this.nasaConfig = nasaConfig;
        this.restTemplate = restTemplate;
    }

    public NasaResponse getAstronomyPictureOfTheDay(LocalDate date){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = date.format(formatter);
            String url = "https://api.nasa.gov/planetary/apod?api_key=" + nasaConfig.getApiKey() + "&date="+ formattedDate;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            String img = root.path("url").asText();
            String title = root.path("title").asText();
            String explanation = root.path("explanation").asText();

            return new NasaResponse(title,explanation,img);
        }catch (IOException e){
            throw new GeneralException("Error getting astronomical photo: " + e.getMessage());
        }

    }
}
