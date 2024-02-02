package cky.cky_api.dto;

public class WeatherDto {
    private String weather;
    private int temp;

    public WeatherDto(String weather, int temp) {
        this.weather = weather;
        this.temp = temp;
    }
}
