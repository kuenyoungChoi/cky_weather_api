package cky.cky_api.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class YoutubeService {

    public String getVideoInfo(String weather) {
        System.out.println("weather = " + weather);
        String apiUrl = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyD_ZXEyYc2ko2X__MeH7jBt1DSUulvmZ9I&q=" + weather + "%20music&maxDuration=180&type=video&part=snippet";

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
            return response.toString();
        } catch (Exception e) {
            return "failed to get response!";
        }
    }
}
