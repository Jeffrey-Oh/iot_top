package project.spring.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * 통신 결과를 JSONObject 형태로 리턴하는 Helper 만들기
 * @author JoonsungHong
 */
public class XmltoJsonHelper {
    /**
     * 구현하고자 하는 기능의 정의
     * HttpHelper는 InputStream 객체를 리턴하도록 되어 있으며,
     * InputStream의 내용은 문자열로 추출 가능하다.
     * JSONObject 클래스는 JSON 형식의 문자열을 생성자 파라미터로 요구한다.
     * InputStream -> String 변환 -> JSONObject 생성 후 리턴의 기능을
     * 구현해 둔다면 코드 재 사용면에서 편리하다.
     */
    public JSONObject getJSONObject(InputStream is, String encType) {
        JSONObject json = null;
        
        /**
         * 대용량 Stream에서 문자열 추출하기
         * 대용량 처리를 위하여 BufferedReader 객체를 선언한다.
         */
        String source = null;
        
        BufferedReader reader = null;
        
        try {
            /**
             * 문자열 추출하기
             * 대용량 InputStream에서 한 라인씩 문자열을 추출하여
             * StringBuilder에 쌓은 후, 하나의 문장으로 추출한다.
             */
            String line = null;
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(is, encType));
            
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            source = sb.toString();
        // 상황에 따른 예외처리 구문 추가    
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 사용한 Stream의 close 처리
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } // End try~catch~finally
        
        /**
         * 추출된 문자열을 JSONObject 객체로 변환
         * BufferedReader를 통하여 추출된 최종 결과문자열을
         * JSONObject 클래스의 생성자 파리미터로 전달하여 변환하고
         * 이 객체를 리턴한다.
         */
        try {
            /** XML을 JSON으로 변환 */
            json = XML.toJSONObject(source);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return json;
    } // End getJSONObject Method
}
