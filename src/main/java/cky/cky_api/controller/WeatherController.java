package cky.cky_api.controller;

import cky.cky_api.entity.ApiResponse;
import cky.cky_api.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user/media")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping(value = "/video")
    public String video(@RequestParam double latitude, @RequestParam double longitude) {
        return weatherService.parseWeather(weatherService.getWeatherInfo(latitude, longitude));
    }

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> weather(@RequestParam double latitude, @RequestParam double longitude) {
        System.out.println("latitude = " + latitude);
        System.out.println("longitude = " + longitude);
        return ApiResponse.createSuccess(weatherService.getWeatherInfo(latitude, longitude));
    }

}
