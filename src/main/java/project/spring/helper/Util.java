package project.spring.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

/**
 * @fileName    : Util.java
 * @author      : 홍준성
 * @description : 공통 기능을 모아 놓기 위한 Util 클래스
 * @lastDate    : 2019. 4. 15.
 */
/**
 * study.java.helper 패키지 추가하기
 * 
 * 앞으로의 남은 단원들에서는 다른 곳에서 활용 가능한 기능들을
 * helper 패키지에 작성합니다.
 * 이 기능들은 안드로이드나 JSP에서 활용 가능합니다.
 * 
 * 패키지에 클래스 추가하기
 * 
 * 싱글톤 형식으로 준비한다.
 * 
 * 기본적인 공통 기능들을 묶어 놓은 클래스
 */
public class Util {
    /**
     * ===== 지정된 범위의 랜덤값을 리턴하기 위한 메서드 추가하기 =====
     * - Java의 Meth.random() 메서드는 0~1 범위의 실수형 값을 리턴하기 때문에,
     *     자연수 형식의 범위를 갖기 위해서는 아래와 같이 수식을 적용해야 합니다.
     */
    /**
     * 범위를 갖는 랜덤값을 생성하여 리턴하는 메서드
     * @param min - 범위 안에서의 최소값
     * @param max - 범위 안에서의 최대값
     * @return min~max 안에서의 랜덤값
     */
    public int getRandom(int min, int max) {
        int num = (int) ((Math.random() * (max - min + 1)) + min);
        return num;
    } // End getRandom Method
    
    /**
     * 랜덤으로 인증번호를 생성하여 리턴하는 메서드
     * @param len - 생성할 인증번호 갯수
     * @param dupCd - 중복허용(1),중복방지(2) 설정
     * @return String - 문자열로 랜덤값을 리턴
     */
    public String getCertification(int len, int dupCd ) {
        String numStr = ""; //난수가 저장될 변수
        
        for(int i=0;i<len;i++) {
            
            //0~9 까지 난수 생성
            String ran = "" + this.getRandom(0, 9);
            
            if(dupCd==1) {
                //중복 허용시 numStr에 append
                numStr += ran;
            }else if(dupCd==2) {
                //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if(numStr.indexOf(ran) == -1) {
                    //중복된 값이 없으면 numStr에 append
                    numStr += ran;
                }else {
                    //생성된 난수가 중복되면 루틴을 다시 실행한다
                    i-=1;
                }
            }
        }
        return numStr;
    } // End getCertification Method
    
    /**
     * ===== 임시 비밀번호 발급 기능을 구현하기 =====
     * - 모든 글자를 나열한 문자열에서 랜덤한 위치에 있는 한 글자를 추출한다.
     * - 이 과정을 8회 반복하고, 이 과정에서 추출된 글자를 빈 문자열에 누적해서 리턴한다.
     */
    /**
     * 랜덤한 비밀번호를 생성하여 리턴한다.
     * @return String
     */
    public String getRandomPassword() {
        // 리턴할 문자열
        String password = "";
        
        // A~Z, a~z, 1~0
        String words = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        // 글자길이
        int words_len = words.length();
        
        for(int i = 0; i < 8; i++) {
            // 랜덤한 위치에서 한 글자를 추출한다.
            int random = getRandom(0, words_len - 1);
            String c = words.substring(random, random + 1);
            
            // 추출한 글자를 미리 준비한 변수에 추가한다.
            password += c;
        }
        
        return password;
    } // End getRandomPassword Method
    
    /**
     * 캘린더 클래스를 파리미터로 전달받아, 그 객체가 포함하고 있는
     * 시간을 출력하는 메서드
     */
    public void getPrintDateTime(Calendar cal) {
        int yy = cal.get(Calendar.YEAR);
        int mm = cal.get(Calendar.MONTH) + 1;
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        int hh = cal.get(Calendar.HOUR_OF_DAY);
        int mi = cal.get(Calendar.MINUTE);
        int ss = cal.get(Calendar.SECOND);
        
        System.out.printf(
                "%04d년 %02d월 %02d일 %02d시 %02d분 %02d초\n",
                yy, mm, dd, hh, mi, ss
                );
        
        // Calendar cal = Calendar.getInstance();
        // DatePrinter.printDateTime(cal);
        // 이것으로 바로 같은 format 형식으로 출력 가능.
    } // End getPrintDateTime Method
    
    public String getPrintDate(Calendar cal) {
        int yy = cal.get(Calendar.YEAR);
        int mm = cal.get(Calendar.MONTH) + 1;
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        String format = "%04d%02d%02d";
        String searchDate = String.format(format, yy, mm, dd);
        
        return searchDate;
    }
    
    public String getPrintDate2(Calendar cal) {
        int yy = cal.get(Calendar.YEAR);
        int mm = cal.get(Calendar.MONTH) + 1;
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        String format2 = "%04d-%02d-%02d";
        String searchDate2 = String.format(format2, yy, mm, dd);
        
        return searchDate2;
    }
    
    public String getStringFromIs(InputStream is, String encType) {
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
        
        return source;
    } // End getStringFromIs Method
    
    /**
     * getUrlEncodeUTF8 Method
     * URL에 붙일 String(s)를 UTF8로 Encoding을 해준다.
     * @param s - Encoding을 하고자하는 문자열
     * @return String - Encoding 된 값을 리턴한다.
     */
    public String getUrlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        } // End try~catch
    } // End getUrlEncodeUTF8 Method
    
    /**
     * 특정 날짜의 요일 구하기
     * @MethodName - getDayOfWeek
     * @param year
     * @param month
     * @param day
     * @return
     * @author     - JeffreyOh
     * @lastUpdate - 2019. 7. 8.
     */
    public String getDayOfWeek(int year, int month, int day) {
    	String dow = "";
    	
    	Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, day);
		
		int dowNum = cal.get(Calendar.DAY_OF_WEEK)-1;
		String[] dowArr = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        dow = dowArr[dowNum];
    	
    	return dow;
    }
}
