package project.spring.travel.test.api;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.IOException;
// 6NaEahSKsPs4w3y2I3KaPnfnJgNFErEryjFY%2BSprboVD%2BcQ0IWpWhE9dr5El3uEEkRuNztlR3OMtGXFJkFVu%2BA%3D%3D
// %2FjScMOzMIX%2FZogYSNYCp951DXnY6OW9XLerAqh4rVFbywmEGFrmOBtcm6pxVzf6sH7nCDN1FbpPd8ZqiTK5IhQ%3D%3D
public class ApiExplorer {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword"); /*URL*/
//        urlBuilder.append("?" + URLEncoder.encode("key","UTF-8") + "=2017172116"); /*Service Key*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=%zw5VKU%2BBkSJZrmBRlY4pYEGAVByswSeHDmGCE5H%2BahTu39%2BDKCQwO7O%2FmHBKtAj5XP9z9ceYj7KcEmVIRcjoQw%3D%3D"); /*Service Key*/
//        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /**/
//        urlBuilder.append("&" + URLEncoder.encode("routeNo","UTF-8") + "=" + URLEncoder.encode("0100", "UTF-8")); /*0010 경부선 0100 남해선 0101 남해선(영암-순천) 0120 88올림픽선 0121 무안광주선 0140 고창담양선 0150 서해안선 0153 평택시흥선 0160 울산선 0170 평택화성선 0200 대구포항선 0201 익산장수선 0251 호남선 0252 천안논산선 0270 순천완주선 0300 청원상주선 0301 당진대전선 0351 중부선(대전통영) 0352 중부선 0370 제2중부선 0400 평택제천선 0450 중부내륙선 0500 영동선 0550 */
//        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10000", "UTF-8")); /**/
//        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /**/
        URL url = new URL(urlBuilder.toString());
        System.out.println(url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        JSONObject json = XML.toJSONObject(sb.toString());
        System.out.println(json);
    }
}