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
public class ServiceareaPlaceApi {
    @Autowired
    private HttpHelper httpHelper;
    @Autowired
    private JsonHelper jsonHelper;
    
    @Test
    public void testFactory() {
        Boolean is_end = false;
        String query = ("대학로 D:BASE").trim().replace(" ", "");
//        String queryX = "127.45529353";
//        String queryY = "36.6537154909";
        Map<String, String> header = new HashMap<String, String>();
        header.put("Authorization", "KakaoAK 917780780abfbb0009a7f44cfb307f98");
        int i = 1;
        
        while(!is_end) {
//            String url = "https://dapi.kakao.com/v2/local/search/keyword.json?page=" + i + "&query=" + query + "&x=" + queryX + "&y=" + queryY + "&radius=20000";
            String url = "https://dapi.kakao.com/v2/local/search/keyword.json?page=" + i + "&query=" + query;
            InputStream is = httpHelper.getWebData(url, "utf-8", header);
            JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
            JSONObject meta = json.getJSONObject("meta");
            is_end = meta.getBoolean("is_end");
            
            // json 배열까지 접근한다.
            JSONArray documents = json.getJSONArray("documents");
            
            // 배열 데이터이므로 반복문 안에서 처리해야 한다.
            // 배열의 길이만큼 반복한다.
            for(int k = 0; k < documents.length(); k++) {
                // 배열의 j번째 JSON을 꺼낸다.
                JSONObject temp = documents.getJSONObject(k);
                // 데이터를 추출   
                String category_name = temp.getString("category_name");
                if(category_name.equals("교통,수송 > 휴게소 > 고속도로휴게소") || category_name.equals("교통,수송 > 휴게소")) {
                    System.out.println("-----------------------temp--------" + k);
                    System.out.println(temp);
                }
            }
            
            System.out.println("------json-------------------------" + i);
            System.out.println(json);
            
            i++;
        }
        
        System.out.println("--------------------------- fin.");
    }
}