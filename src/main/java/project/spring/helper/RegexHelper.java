package project.spring.helper;

import java.util.regex.Pattern;

/**
 * @fileName : RegexHelper.java
 * @author : 홍준성
 * @description : 정규표현식을 활용한 입력값의 형식 검사
 * @lastUpdate : 2019-04-15
 */
/**
 * # 정규표현식이란?
 * 
 * ## 정규표현식 소개
 * - 정규표현식(Regular expression)은
 *     특정한 규칙을 가진 문자열의 집합을 표현하는데 사용하는 "형식언어"이다.
 * - 정규표현식은 많은 텍스트 편집기와 프로그래밍 언어에서
 *     "문자열의 검색과 치환"을 위해 지원하고 있다.
 * - 자바에서도 정규표현식을 J2SE 1.4부터 지원하기 시작했으며,
 *     관련된 주요 클래스들은 java.util.regex 패키지에 포함되어 있다.
 * 
 * ## 정규표현식의 사용
 *     * boolean java.util.regex.Pattern.matches(String arg0, CharSequence arg1)
 * - arg0은 정규 표현식 문자열이다.
 * - arg1은 형식을 검사 받기 위한 내용이다.
 * - 즉 위의 기능은 arg1이 arg0의 형식에 맞는지를 검사하고
 *     결과를 boolean으로 리턴 한다.
 */
public class RegexHelper {
    /**
     * 주어진 문자열이 공백이거나 null인지를 검사
     * @param str - 검사할 문자열
     * @return boolean - 공백, null이 아닐 경우 true 리턴
     */
    public boolean isValue(String str) {
        boolean result = false;
        
        if(str != null) {
            result = !str.trim().equals("");
        }
        return result;
    }
    
    /**
     * 숫자 모양에 대한 형식 검사
     * @param str - 검사할 문자열
     * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
     */
    public boolean isNum(String str) {
        boolean result = false;
        
        if(isValue(str)) {
            result = Pattern.matches("^[0-9]*$", str);
        }
        return result;
    }
    
    /**
     * 영문으로만 구성되었는지에 대한 형식 검사
     * @param str - 검사할 문자열
     * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
     */
    public boolean isEng(String str) {
        boolean result = false;
        
        if(isValue(str)) {
            result = Pattern.matches("^[a-zA-Z]*$", str);
        }
        return result;
    }
    
    /**
     * 한글로만 구성되었는지에 대한 형식 검사
     * @param str - 검사할 문자열
     * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
     */
    public boolean isKor(String str) {
        boolean result = false;
        
        if(isValue(str)) {
            result = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", str);
        }
        return result;
    }
    
    /**
     * 영문과 숫자로만 구성되었는지에 대한 형식 검사
     * @param str - 검사할 문자열
     * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
     */
    public boolean isEngNum(String str) {
        boolean result = false;
        
        if(isValue(str)) {
            result = Pattern.matches("^[a-zA-Z0-9]*$", str);
        }
        return result;
    }
    
    /**
     * 한글과 숫자로만 구성되었는지에 대한 형식 검사
     * @param str - 검사할 문자열
     * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
     */
    public boolean isKorNum(String str) {
        boolean result = false;
        
        if(isValue(str)) {
            result = Pattern.matches("^[ㄱ-ㅎ가-힣0-9]*$", str);
        }
        return result;
    }
    
    /**
     * 이메일 형식인지에 대한 검사
     * @param str - 검사할 문자열
     * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
     */
    public boolean isEmail(String str) {
        boolean result = false;
        
        if(isValue(str)) {
            result = Pattern.matches(
                    "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", str
                    );
        }
        return result;
    }
    
    /**
     * "-"없이 핸드폰번호인지에 대한 형식검사
     * @param str - 검사할 문자열
     * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
     */
    public boolean isCellPhone(String str) {
        boolean result = false;
        
        if(isValue(str)) {
            result = Pattern.matches(
                    "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", str
                    );
        }
        return result;
    }
    
    /**
     * "-"없이 전화번호인지에 대한 형식검사
     * @param str - 검사할 문자열
     * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
     */
    public boolean isTel(String str) {
        boolean result = false;
        
        if(isValue(str)) {
            result = Pattern.matches(
                    "^\\d{2,3}\\d{3,4}\\d{4}$", str
                    );
        }
        return result;
    }
    
    /**
     * "-"없이 주민번호에 대한 형식검사
     * @param str - 검사할 문자열
     * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
     */
    public boolean isJumin(String str) {
        boolean result = false;
        
        if(isValue(str)) {
            result = Pattern.matches(
                    "^\\d{6}[1-4]\\d{6}", str
                    );
        }
        return result;
    }
    
    /**
     * 비밀번호에 대한 형식검사
     * @param str - 검사할 문자열
     * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
     */
    public boolean isPassword(String str) {
        boolean result = false;
        
        if(isValue(str)) {
            result = Pattern.matches(
                    "^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]*$", str
                    );
        }
        return result;
    }
    
    /**
     * 핸드폰번호 형식과 집전화 번호 형식 둘 중 하나를 충족하는지 검사한다.
     * @param str - 검사할 문자열
     * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
     */
    public boolean isPhone(String str) {
        boolean result = false;
        
        if(isValue(str)) {
            result = Pattern.matches(
                    "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", str
                    );
            if(!result) {
                result = Pattern.matches(
                        "^\\d{2,3}\\d{3,4}\\d{4}$", str
                        );
            }
        }
        return result;
    }
    
    /**
     * 비밀번호의 입력값에 아이디가 포함되어 있는지 검사한다.
     * @param origin - 원본 요소 (비밀번호)
     * @param compare - 검사 대상 요소 (아이디)
     * @return boolean - 값이 포함되지 않은 경우 true, 포함된 경우 false
     */
    public boolean isIndexCheck(String origin, String compare) {
        boolean result = false;
        
        if(isValue(origin) && isValue(compare)) {
            if(origin.indexOf(compare) == -1) {
                result = !result;
            }
        }
        
        return result;
    }
    
    /**
     * 입력 값이 지정된 글자수를 초과했는지 검사한다.
     * @param str - 검사할 문자열
     * @param len - 지정된 글자수
     * @return boolean - 지정된 글자수 초과하지 않은 경우 true, 초과한 경우 false
     */
    public boolean isMaxLength(String str, int len) {
        boolean result = false;
        
        if(isValue(str)) {
            if(str.length() <= len) {
                result = !result;
            }
        }
        
        return result;
    }
    
    /**
     * 입력 값이 지정된 글자수 미만인지 검사한다.
     * @param str - 검사할 문자열
     * @param len - 지정된 글자수
     * @return boolean - 지정된 글자수 이상인 경우 true, 미만인 경우 false
     */
    public boolean isMinLength(String str, int len) {
        boolean result = false;
        
        if(isValue(str)) {
            if(str.length() >= len) {
                result = !result;
            }
        }
        
        return result;
    }
    
    /**
     * 체크박스가 선택되어 있는지 검사한다.
     * @param str - 선택 요소의 문자 배열
     * @return boolean - 체크된 경우 true, 체크되지 않은 경우 false
     */
    public boolean isCheck(String[] str) {
        boolean result = false;
        
        if(str.length >= 1) {
            result = !result;
        }
        
        return result;
    }
    
    /**
     * 체크박스의 최소 선택 갯수를 검사한다.
     * @param str - 선택요소의 문자 배열
     * @param min - 최소 선택 갯수
     * @return boolean - 최소 수량 이상 체크된 경우 true, 그렇지 않은 경우 false
     */
    public boolean isCheckMin(String[] str, int min) {
        boolean result = false;
        
        if(str.length >= min) {
            result = !result;
        }
        
        return result;
    }
    
    /**
     * 체크박스의 최대 선택 갯수를 검사한다.
     * @param str - 선택요소의 문자 배열
     * @param max - 최대 선택 갯수
     * @return boolean - 최대 수량 이하 체크된 경우 true, 그렇지 않은 경우 false
     */
    public boolean isCheckMax(String[] str, int max) {
        boolean result = false;
        
        if(str.length <= max) {
            result = !result;
        }
        
        return result;
    }
    
    /**
     * 두 요소의 입력값이 동일한지 검사한다.
     * @param origin - 원본 요소의 문자열 (비밀번호)
     * @param compare - 검사 대상의 문자열 (비밀번호 확인)
     * @return boolean - 동일한 경우 true / 다른 경우 false
     */
    public boolean isCompareTo(String origin, String compare) {
        boolean result = false;
        
        if(isValue(origin) && isValue(compare)) {
            if(origin.equals(compare)) {
                result = !result;
            }
        }
        
        return result;
    }
    
}
