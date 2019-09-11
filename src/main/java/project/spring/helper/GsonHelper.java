package project.spring.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
/**
 * @fileName    : GsonHelper.java
 * @author      : JoonsungHong
 * @description : Gson을 위한 Helper 클래스 만들기
 * @lastUpdate  : 2019-01-04
 */
public class GsonHelper {
   /**
    * InputStream 객체를 Gson 객체로 변환하여 리턴한다.
    * @param  is - InputStream 객체
    * @param  encType - InputStream이 읽어들인 Xml의 인코딩 형식
    * @return Gson
    */
   public String getGsonString(InputStream is, String encType) {
      
      // InputStream의 내용을 저장할 문자열
      String source = null;
      
      BufferedReader reader = null;
      try {
         String line = null;
         StringBuilder sb = new StringBuilder();
         reader = new BufferedReader(new InputStreamReader(is, encType));
         while ((line = reader.readLine()) != null) {
            sb.append(line +"\n");
         }
         source = sb.toString();
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if (reader != null) {
            try {
               reader.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
         
         if (is != null) {
            try {
               is.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }
      
      return source;
   }
}