package cky.cky_api.controller;

import cky.cky_api.service.WeatherService;
import jakarta.validation.Valid;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user/media")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    void getYouTubeInfo() {
        weatherService.getWeatherInfo();
    }

    @GetMapping(value = "/video")
    public String video() {
        return weatherService.parseWeather(weatherService.getWeatherInfo());
    }

    @GetMapping(value = "")
    public String temp() {

        return weatherService.getWeatherInfo();
    }
}
