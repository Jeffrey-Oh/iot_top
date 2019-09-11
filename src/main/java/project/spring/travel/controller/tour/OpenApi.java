package project.spring.travel.controller.tour;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class OpenApi {
    public static void main(String[] args) {
    	
    	
        BufferedReader br = null;
        

		String addr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword?serviceKey=";
		String servicekey = "jlqwmvLJg1mfY2FrnI2dWSq624frmaOFZYLixlo7hXAGug%2B0dvGO%2B517BZKAJJ3Deq8nVKkVjOLCwVmOmg8Cqg%3D%3D";
		String parameter = "";
        

		parameter = parameter +  "&keyword=";
		try {
			parameter = parameter + URLEncoder.encode("서울", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		parameter = parameter + "&" + "MobileOS=ETC";
		parameter = parameter + "&" + "MobileApp=AppTest";
		parameter = parameter + "&" + "numOfRows=10";
//		parameter = parameter + "&" + "listYN=Y";
//		parameter = parameter + "&" + "arrange=A";
		parameter = parameter + "&" + "_type=json";
		
		addr = addr + servicekey + parameter;
		
        try{            
        	System.out.println(addr);
        	System.out.println(addr);
        		
            URL url = new URL(addr);
            
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
            String result = "";
            String line;
            while((line = br.readLine()) != null) {
                result = result + line + "\n";
            }
            System.out.println(result);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}


