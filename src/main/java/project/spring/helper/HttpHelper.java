package project.spring.helper;
// Import 처리 결과 확인, 일괄 추가 확인, 구문 자동 추가 확인

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * 통신 기능을 위한 Helper 클래스 만들기
 * @author JoonsungHong
 * Helper 클래스를 위한 싱글톤 패턴 준비하기
 */
// 라이브러리 뭘까?
public class HttpHelper {
    /**
     * 구현해야 할 기능 정의하기
     * 리턴형에 대한 Import 처리
     * 
     * HttpHelper 개선 작업 (1) - HttpHeader 데이터의 추가
     * Header 데이터는 클라이언트(프로그램)과 Open ApI(서버)간에
     * 오가는 부가 정보로서 key-value의 쌍으로 구성된다.
     * Kakao API 에서는 개발자 사이트에서 발급받은 REST Key를
     * Header 데이터로 전달하는 것을 요구하기 때문에
     * HttpHelper의 개선이 필요하다.
     * Key와 Value의 쌍으로 데이터를 표현하는 형태는
     * Map 계열 객체로 구현할 수 있기 때문에
     * getWebData() 메서드에 Map 객체를 파라미터로 추가한다.
     */
    public InputStream getWebData(String url, String encType, Map<String, String> header) {
        /**
         * 접속 설정
         * 웹 브라우저에 해당하는 통신 객체
         * (HttpClient)를 선언하고,
         * 인터넷 옵션값들을 저장하는 기본 환경설정 객체
         * (HttpParams, HttpConnectionParams, HttpProtocolParams)를 생성한다.
         * 통신 객체를 할당할 때 옵션값을 생성자
         * (DefaultHttpClient)에게 넘겨준다.
         */
        // 접속 대기에 대한 제한시간 -> 밀리세컨드 단위
        int timeout = 30000;
        
        // 통신객체
        HttpClient client = null;
        // 접속을 하기 위한 기본 환경설정 객체
        HttpParams params = new BasicHttpParams();
        // 프로토콜 버전 설정
        params.setParameter(
                CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1
                );
        // 요청 제한 시간
        HttpConnectionParams.setConnectionTimeout(params, timeout);
        // 통신에 사용할 인코딩 값 설정
        HttpProtocolParams.setContentCharset(params, encType);
        // 접속 기능을 하는 객체 생성
        client = new DefaultHttpClient(params);
        
        /**
         * HttpHelper 개선 (2) - HTTPS 형태의 URL에 접근하기 위한 인증 처리
         * 보안 프로토콜: https 형태의 URL은 443번 포트를 통해
         * 모든 요청과 응답이 암호화 되어 전송된다.
         * 이 형태의 URL에 접근하기 위한 요청에는 SSL 인증이 필요하다.
         * 다음의 코드는 모든 URL에 대해서 SSL 인증을 허용하는 처리이다.
         */
        // HTTPS 프로토콜에 접근하기 위한 인증 처리
        try {
            /**
             * SS: 인증을 수행할 수 있는 객체를 생성하고 초기화 한다.
             * -> import javax.net.ssl.SSLContext;
             * 예외처리 NoSuchAlgorithmException
             * 예외처리 KeyManagementException
             */
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);
            
            /**
             * SSL 인증 객체에서 모든 도메인에 대해서 접근을 허용
             * (ALLOW_ALL_HOSTNAME_VERIFIER)하도록 지정한다.
             * -> import org.apache.http.conn.ssl.SSLSoketFactory;
             */
            SSLSocketFactory sf = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme sch = new Scheme("https", 443, sf);
            
            // 접속 기능을 하는 객체에게 SSL인증 내용을 적용한다.
            client.getConnectionManager().getSchemeRegistry().register(sch);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("특정의 암호화 알고리즘이 요구되었지만, 환경에서는 이용 가능하지 않은 경우에 Throw됩니다.");
            e.printStackTrace();
        } catch (KeyManagementException e) {
            System.out.println("키 관리를 다루는 모든 조작에 대한 일반적인 키 관리 예외");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("SSL 인증 실패");
            e.printStackTrace();
        }
        
        /**
         * 접속 과정 준비하기
         * 접속처리를 수행한다.
         * 통신이 완료되면 서버의 응답 결과가 HttpResponse 객체로 리턴된다.
         * 
         * 각 클래스에 대한 Import 처리
         * InputStream 클래스는 동일한 이름의 다른 클래스가 존재하므로,
         * 직접 내용을 확인하여 java.io 패키지 안에 포함된 클래스를 import 한다.
         * 나머지 클래스들은 고유한 이름을 갖고 있기 때문에 일광 처리 가능하다.
         */
        // 응답 결과가 저장될 객체
        InputStream is = null;
        
        // 통신에 필요한 요청 정보 설정 -> URL을 주소표시물에 입력하기
        HttpGet httpget = new HttpGet(url);
        
        /**
         * HttpHelper 개선 작업(1) - HttpHeader 데이터의 추가
         * 파라미터로 전달된 header 객체가 null이 아닐 경우
         * header 데이터를 스캔하여, 각 요소를 통신에 필요한
         * 요청 정보에 추가한다.
         * Java 1.5부터 추가된 향상된 for문이 사용된다.
         */
        // HTTP header가 전달된 경우 요청정보에 Header 데이터를 추가한다.
        if(header != null) {
            /**
             * Map 객체로 전달된 Header의 모든 키에 대한 스캔
             * (향상된 for문 사용)
             * -> 각 키를 for문에 선언된 key 변수에 저장한다.
             */
            for(String key : header.keySet()) {
                // key - value 형식으로 http-header 데이터를 추가한다.
                httpget.addHeader(key, header.get(key));
            }
        }
        
        /**
         * 통신 과정에 대한 예외처리
         * 네트워크가 연결되지 않거나
         * 지정한 시간 안에 통신이 완료되지 않을 경우를 대비하여
         * 예외처리가 강제적으로 요구된다.
         * 예외처리 결과 확인
         */
        try {
            // 요청을 보낸 후, 응답 받기
            HttpResponse response = client.execute(httpget);
            
            /**
             * 수신된 응답결과에 따른 데이터 추출처리
             * 웹 서버의 응답 결과 코드에 따라 정상 여부를 확인한다.
             * 응답 결과가 정상이라면 InputStream으로 변환한다.
             * 
             * 웹 서버의 응답 결과 코드 받기
             * 404: Page Not Found (주소 오타, 파일 없음)
             * 500: Server Error,   200: OK
             */
            int resultCode = response.getStatusLine().getStatusCode();
            
            // 서버 응답이 정상일 경우에만 처리
            if(resultCode == HttpURLConnection.HTTP_OK) {
                // 수신된 응답에서 실 데이터 추출
                HttpEntity entity = response.getEntity();
                BufferedHttpEntity buffer = new BufferedHttpEntity(entity);
                
                // 추출한 데이터를 InputStream으로 변환
                is = buffer.getContent();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /**
             * 통신 해제하기
             * 파일 입출력과 마찬가지로 통신의 성공/실패 여부와 상광 없이
             * 항상 통신 해제 처리가 수행되어야 재 접속이 가능하다.
             */
            // 통신 해제
            client.getConnectionManager().shutdown();
        } // End try~catch~finally
        
        // 통신 과정에서 생성된 InputStream 객체(is)를 리턴하기
        // 통신결과 리턴
        return is;
    } // End getWebData Method
    
    /**
     * getWebData Overload Method
     * @param url - 접속하고자 하는 url
     * @param encType - 인코딩 형태
     * @return InputStream - is로 리턴
     */
    public InputStream getWebData(String url, String encType) {
        return getWebData(url, encType, null);
    } // End getWebData Method
}
