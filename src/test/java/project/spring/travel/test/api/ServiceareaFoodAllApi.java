package project.spring.travel.test.api;
import java.io.InputStream;

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
public class ServiceareaFoodAllApi {
    @Autowired
    private HttpHelper httpHelper;
    @Autowired
    private JsonHelper jsonHelper;
    
    @Test
    public void testFactory() {
//        String now_page = "1";
//        String url = "http://data.ex.co.kr/openapi/restinfo/restBestfoodList?key=2017172116&type=json&numOfRows=10000&pageNo=" + now_page;
//        InputStream is = httpHelper.getWebData(url, "utf-8");
//        JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
//        String pageSize = "" + json.get("pageSize");
//        String code = "" + json.get("code");
//        
//        boolean bool = true;
//        
//        if(code.equals("ERROR")) {
//            while(bool) {
//                url = "http://data.ex.co.kr/openapi/restinfo/restBestfoodList?key=2017172116&type=json&numOfRows=10000&pageNo=" + now_page;
//                is = httpHelper.getWebData(url, "utf-8");
//                json = jsonHelper.getJSONObject(is, "utf-8");
//                pageSize = "" + json.get("pageSize");
//                code = "" + json.get("code");
//                
//                if(code.equals("SUCCESS")) {
//                    bool = false;
//                }
//            }
//        }
//        System.out.println("------------------------------- 1");
//        System.out.println(json);
//        
//        for(int i = Integer.parseInt(now_page) + 1; i <= Integer.parseInt(pageSize); i++) {
//            url = "http://data.ex.co.kr/openapi/restinfo/restBestfoodList?key=2017172116&type=json&numOfRows=10000&pageNo=" + i;
//            is = httpHelper.getWebData(url, "utf-8");
//            json = jsonHelper.getJSONObject(is, "utf-8");
//            code = "" + json.get("code");
//            
//            if(code.equals("ERROR")) {
//                bool = true;
//                
//                while(bool) {
//                    url = "http://data.ex.co.kr/openapi/restinfo/restBestfoodList?key=2017172116&type=json&numOfRows=10000&pageNo=" + i;
//                    is = httpHelper.getWebData(url, "utf-8");
//                    json = jsonHelper.getJSONObject(is, "utf-8");
//                    code = "" + json.get("code");
//                    
//                    if(code.equals("SUCCESS")) {
//                        bool = false;
//                    }
//                }
//            }
//            
//            System.out.println("-------------------------------" + i);
//            System.out.println(json);
//        }
//        
//        System.out.println("--------------------------- fin.");
        
        int now_page = 1;
        String pageSize = "1";
        
        for(int i = now_page; i <= Integer.parseInt(pageSize); i++) {
            String code = "ERROR";
            
            if(code.equals("ERROR")) {
                boolean bool = true;
                
                while(bool) {
                    String url = "http://data.ex.co.kr/openapi/restinfo/restBestfoodList?key=2017172116&type=json&numOfRows=10000&pageNo=" + i;
                    InputStream is = httpHelper.getWebData(url, "utf-8");
                    JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
                    pageSize = "" + json.get("pageSize");
                    code = "" + json.get("code");
                    
                    if(code.equals("SUCCESS")) {
                        bool = false;
                    }
                    System.out.println("-------------------------------" + i);
                    System.out.println(json);
                } // End while
            } // End if
        } // End for
        
        System.out.println("--------------------------- fin.");
    }
}