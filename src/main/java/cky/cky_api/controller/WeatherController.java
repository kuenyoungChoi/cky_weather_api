package cky.cky_api.controller;

import cky.cky_api.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user/media")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping(value = "/video")
    public String video(@RequestParam double latitude, @RequestParam double longitude) {
        return weatherService.parseWeather(weatherService.getWeatherInfo(latitude, longitude));
    }

    @GetMapping(value = "")
    public String weather(@RequestParam double latitude, @RequestParam double longitude) {
        System.out.println("latitude = " + latitude);
        System.out.println("longitude = " + longitude);
        return weatherService.getWeatherInfo(latitude, longitude);
    }

}
