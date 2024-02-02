package cky.cky_api.controller;

import cky.cky_api.service.WeatherService;
import cky.cky_api.service.YoutubeService;
import cky.cky_api.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user/media")
public class WeatherController {

    private final ResponseService responseService;
    private final WeatherService weatherService;
    private final YoutubeService youtubeService;

    @GetMapping(value = "/video")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> video(@RequestParam String keyword) throws ParseException {
        return responseService.ok(youtubeService.getVideoInfo(keyword));
    }

    @GetMapping(value = "/weather")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> weather(@RequestParam double latitude, @RequestParam double longitude) throws ParseException {
        System.out.println("latitude = " + latitude);
        System.out.println("longitude = " + longitude);
        return responseService.ok(weatherService.getWeatherInfo(latitude, longitude));
    }
}
