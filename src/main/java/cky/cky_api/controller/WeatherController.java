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

        if (!keyword.equals(null)) {
            return responseService.ok(youtubeService.getVideoInfo(keyword));
        } else {
            return responseService.fail("FAIL");
        }
    }

    @GetMapping(value = "/weather")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> weather(@RequestParam(required = false) Double latitude, @RequestParam(required = false) Double longitude) throws ParseException {
        System.out.println("latitude = " + latitude);
        System.out.println("longitude = " + longitude);

        if (latitude == null || longitude == null) {
            // 처리할 로직 추가 (예: 유효하지 않은 값으로 응답하거나 예외를 throw)
            return responseService.fail("FAIL");
        } else {
            return responseService.ok(weatherService.getWeatherInfo(latitude, longitude));
        }
    }
}
