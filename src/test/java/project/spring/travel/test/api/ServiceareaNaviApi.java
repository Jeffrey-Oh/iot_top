package project.spring.travel.test.api;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import project.spring.helper.HttpHelper;
import project.spring.helper.JsonHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class ServiceareaNaviApi {
    @Autowired
    private HttpHelper httpHelper;
    @Autowired
    private JsonHelper jsonHelper;
    
    @Test
    public void testFactory() {
        boolean code = false;
        String start = "127.041906866031,37.4601848719507";
        String goal = "127.180928848988,36.8555526026702";
        Map<String, String> header = new HashMap<String, String>();
        header.put("X-NCP-APIGW-API-KEY-ID", "xt0wdpixgv");
        header.put("X-NCP-APIGW-API-KEY", "Lt8VinB2ui4bDNnZDAT8BQNfi1ryjyTWG3uEwE1y");
        
        String url = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?start=" + start + "&goal=" + goal;
        InputStream is = httpHelper.getWebData(url, "utf-8", header);
        JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
        // 응답 결과 코드 (0 = 정상)
        String message = json.getString("message"); // 응답결과 메시지 추출
        if(json.getInt("code") == 0) {
            code = !code;
            System.out.println("-----------------json----------------");
            System.out.println(json);
            System.out.println("응답결과 메시지= " + message);
            
            // json 배열까지 접근한다.
            JSONObject route = json.getJSONObject("route");
            JSONArray traoptimal = route.getJSONArray("traoptimal");
            
            // 배열의 j번째 JSON을 꺼낸다.
            JSONObject traoptimalTemp = traoptimal.getJSONObject(0);
            // temp 안의 JSON을 꺼낸다.
            JSONObject summary = traoptimalTemp.getJSONObject("summary");
            JSONArray path = traoptimalTemp.getJSONArray("path");
            JSONArray section = traoptimalTemp.getJSONArray("section");
            JSONArray guide = traoptimalTemp.getJSONArray("guide");
            // 데이터를 추출
            int summaryDistance = summary.getInt("distance"); // 전체 경로 거리(meters)
            int summaryDuration = summary.getInt("duration"); // 전체 경로 소요 시간(millisecond(1/1000초))
            System.out.println("전체 경로 거리(meters) = " + summaryDistance);
            System.out.println("전체 경로 소요 시간(millisecond(1/1000초)) = " + summaryDuration);
            for(int i = 0; i < path.length(); i++) {
                JSONArray pathTemp = path.getJSONArray(i);
                // 주행 경로 X,Y 좌표
                Double pathX = pathTemp.getDouble(0);
                Double pathY = pathTemp.getDouble(1);
                System.out.println("주행 경로 X,Y 좌표" + i + " = " + pathX + "," + pathY);
            }
            for(int i = 0; i < section.length(); i++) {
                JSONObject sectionTemp = section.getJSONObject(i);
                int sectionDistance =  sectionTemp.getInt("distance"); // 거리(meters)
                String name = sectionTemp.getString("name"); // 도로명
                System.out.println("거리(meters)" + i + " = " + sectionDistance);
                System.out.println("도로명" + i + " = " + name);
            }
            for(int i = 0; i < guide.length(); i++) {
                JSONObject guideTemp = guide.getJSONObject(i);
                String instructions = guideTemp.getString("instructions"); // 안내 문구
                int guideDistance = guideTemp.getInt("distance"); // 이전 경로로부터 거리(meters)
                int guideDuration = guideTemp.getInt("duration"); // 이전 경로로부터 소요 시간(1/1000초))
                System.out.println("안내 문구" + i + " = " + instructions);
                System.out.println("이전 경로로부터 거리(meters)" + i + " = " + guideDistance);
                System.out.println("이전 경로로부터 소요 시간(1/1000초))" + i + " = " + guideDuration);
            }
        } else {
            System.out.println("응답결과 메시지= " + message);
        } // End if~else
        System.out.println("--------------------------- fin.");
    }
}