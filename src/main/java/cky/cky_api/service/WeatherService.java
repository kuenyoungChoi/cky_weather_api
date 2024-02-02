package cky.cky_api.service;

import cky.cky_api.entity.ApiResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {

    private final YoutubeService youtubeService;
    @Value("${openweathermap.key}")
    private String apiKey;

    public WeatherService(YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    public String getWeatherInfo(double latitude, double longitude) {
        System.out.println("apiKey = " + apiKey);
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;
        System.out.println("apiUrl = " + apiUrl);

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();  //  apiUrl을 HttpURL 형식으로 연결
            connection.setRequestMethod("GET"); // get 요청
            int responseCode = connection.getResponseCode();   // 요청을 보낸 다음에 받아온 응답 결과를 응답 코드(상태 코드)로 받아올 수 있다.

            BufferedReader br;
            if(responseCode == 200){    // 정상
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }else{
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();   // 결괏값을 response에 쌓는다.

            while((inputLine = br.readLine()) != null){
                response.append(inputLine);
            }
            br.close();
            System.out.println("response = " + response.toString());
//            parseWeather(response.toString());
            return response.toString();
        } catch (Exception e) {
            return "failed to get response!";
        }
    }

    public String parseWeather(String jsonString) {

        System.out.println("jsonString = " + jsonString);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        String main = null;

        try{
            jsonObject = (JSONObject) jsonParser.parse(jsonString);     // 파싱 결과
        }catch (ParseException e){
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();
        JSONObject mainData = (JSONObject) jsonObject.get("main");          // main의 value
        resultMap.put("temp", mainData.get("temp"));
        System.out.println("resultMap = " + resultMap);


        JSONArray jsonArray = (JSONArray) jsonObject.get("weather");
        System.out.println("jsonArray = " + jsonArray);

        for (int i =0; i < jsonArray.size(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            System.out.println("jo = " + jo);
            main = (String) jo.get("main");
        }

        resultMap.put("main", mainData.get("main"));
        resultMap.put("icon", mainData.get("icon"));


        return youtubeService.getVideoInfo(main);
    }
}
