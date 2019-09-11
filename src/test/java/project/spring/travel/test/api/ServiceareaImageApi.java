package project.spring.travel.test.api;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
public class ServiceareaImageApi {
    @Autowired
    private HttpHelper httpHelper;
    @Autowired
    private JsonHelper jsonHelper;
    
    @Test
    public void testFactory() {
        String query = ("전주비빔밥").trim().replace(" ", "");
        /** Google API */
//        String url = "https://www.googleapis.com/customsearch/v1?key=AIzaSyDLYHNwq4WPNyata4DZVJ5EiXub8Pr2XwI&cx=013708405310549574036:f3hr2q6zgvu&alt=json&searchType=image&num=1&q=" + query;
//        InputStream is = httpHelper.getWebData(url, "utf-8");
//        JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
        /** Kakao API */
        String url = "https://dapi.kakao.com/v2/search/image?sort=accuracy&size=1&page=1&query=" + query;
        Map<String, String> header = new HashMap<String, String>();
        header.put("Authorization", "KakaoAK 917780780abfbb0009a7f44cfb307f98");
        InputStream is = httpHelper.getWebData(url, "utf-8", header);
        JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
        JSONObject meta = json.getJSONObject("meta");
        int total_count = meta.getInt("total_count");
        
        if(total_count == 0) {
            System.out.println("검색 결과가 없습니다.");
        }
        
        System.out.println("------------------------------- 1");
        System.out.println(json);
        
        System.out.println("--------------------------- fin.");
    }
}