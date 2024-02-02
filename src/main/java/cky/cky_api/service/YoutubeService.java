package cky.cky_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class YoutubeService {

    @Value("${youtubeapi.key}")
    private String apiKey;
    Map<String, List<Map<String, Object>>> weatherData = new HashMap<>();
    List<Map<String, Object>> itemList = new ArrayList<>();
    public String getVideoInfo(String keyword) {

        String apiUrl = "https://www.googleapis.com/youtube/v3/search?key=" + apiKey + "&q=" + keyword + "%20music&videoDuration=medium&type=video&part=snippet";

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

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject;
            jsonObject = (JSONObject) jsonParser.parse(response.toString());

            JSONArray jsonArray = (JSONArray) jsonObject.get("items");

            for (int i =0; i < jsonArray.size(); i++) {
                JSONObject jo = (JSONObject) jsonArray.get(i);
                System.out.println("jo = " + jo);
                JSONObject snippet = (JSONObject) jo.get("snippet");
                JSONObject thumbnails = (JSONObject) snippet.get("thumbnails");
                JSONObject defaults = (JSONObject) thumbnails.get("medium");
                JSONObject id = (JSONObject) jo.get("id");
                Map<String, Object> item = new HashMap<>();
                item.put("title", (String) snippet.get("title"));
                item.put("url", (String) defaults.get("url"));
                item.put("videoId", (String) id.get("videoId"));

                // 리스트에 아이템 추가
                itemList.add(item);
            }

            weatherData.put("item", itemList);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResult = objectMapper.writeValueAsString(weatherData);

            return jsonResult;
        } catch (Exception e) {
            return "failed to get response!";
        }
    }
}
