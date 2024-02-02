package cky.cky_api.service;

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

@Service
public class WeatherService {

    private final YoutubeService youtubeService;
    @Value("${openweathermap.key}")
    private String apiKey;
    JSONObject res = new JSONObject();

    public WeatherService(YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    public String getWeatherInfo(double latitude, double longitude) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;

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

            //기온 값 변환
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject;
            jsonObject = (JSONObject) jsonParser.parse(response.toString());

            JSONObject m = (JSONObject) jsonObject.get("main");
            Double tempRes = (Double) m.get("temp");
            long tempRound = Math.round(tempRes);

            Integer temp = (int) (tempRound - 273.15);
            /////////

            res.put("temp", temp);

            JSONArray jsonArray = (JSONArray) jsonObject.get("weather");

            for (int i =0; i < jsonArray.size(); i++) {
                JSONObject jo = (JSONObject) jsonArray.get(i);
                String weather = (String) jo.get("main");
                res.put("weather", weather);
            }

            return res.toString();
        } catch (Exception e) {
            return "failed to get response!";
        }
    }
}
