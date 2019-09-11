package project.spring.travel.test.api;
import java.io.InputStream;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import project.spring.helper.HttpHelper;
import project.spring.helper.XmltoJsonHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class ServiceareaCsApi {
    @Autowired
    private HttpHelper httpHelper;
    @Autowired
    private XmltoJsonHelper xmltoJsonHelper;
    
    @Test
    public void testFactory() {
        boolean bool = true;
        while(bool) {
            String url = "http://open.ev.or.kr:8080/openapi/services/rest/EvChargerService?serviceKey=%2FjScMOzMIX%2FZogYSNYCp951DXnY6OW9XLerAqh4rVFbywmEGFrmOBtcm6pxVzf6sH7nCDN1FbpPd8ZqiTK5IhQ%3D%3D";
            InputStream is = httpHelper.getWebData(url, "utf-8");
            JSONObject json = xmltoJsonHelper.getJSONObject(is, "utf-8");
            JSONObject response = json.getJSONObject("response");
            JSONObject header = response.getJSONObject("header");
            String resultCode = header.getString("resultCode");
            
            if(resultCode.equals("00")) {
                bool = false;
            }
            System.out.println("------------------------------- 1");
            System.out.println(json);
        } // End while
        
        System.out.println("--------------------------- fin.");
    }
}