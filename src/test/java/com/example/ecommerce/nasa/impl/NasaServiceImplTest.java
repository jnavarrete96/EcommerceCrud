package com.example.ecommerce.nasa.impl;

import com.example.ecommerce.exception.GeneralException;
import com.example.ecommerce.nasa.config.NasaConfig;
import com.example.ecommerce.nasa.dto.NasaResponse;
import com.example.ecommerce.nasa.service.impl.NasaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class NasaServiceImplTest {
    @Mock
    private NasaConfig nasaConfig;

    @Mock private RestTemplate restTemplate;

    @InjectMocks
    private NasaServiceImpl nasaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(nasaConfig.getApiKey()).thenReturn("TEST_API_KEY");
    }

    @Test
    void getAstronomyPictureOfTheDaySuccess(){
        LocalDate date = LocalDate.of(2025, 1, 11);
        String jsonResponse = """ 
                { 
                    "url": "https://example.com/image.jpg", 
                    "title": "Example Title", "explanation": 
                    "Example Explanation" 
                } 
                """;
        when(restTemplate.getForEntity(anyString(),any(Class.class)))
                .thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));

        NasaResponse response = nasaService.getAstronomyPictureOfTheDay(date);
        assertNotNull(response);
    }

    @Test
    void getAstronomyPictureOfTheDayGeneralError(){
        LocalDate date = LocalDate.of(2025, 1, 11);
        when(restTemplate.getForEntity(anyString(), any(Class.class))).thenThrow(new GeneralException("Error"));

        assertThrows(GeneralException.class, () -> {
            nasaService.getAstronomyPictureOfTheDay(date);
        });
    }
}
