package project.spring.travel.test.controller.member;
//package project.spring.travel.controller.member;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.logging.log4j.LogManager;
//
//import project.spring.helper.BaseController;
//import project.spring.helper.RegexHelper;
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : MemberUserIdSearchSuccess.java
// * @author      : 홍준성
// * @description : 아이디 찾기 성공 View를 위한 컨트롤러
// * @lastUpdate  : 2019. 4. 1.
// */
//@WebServlet("/member/member_userId_search_success.do")
//public class MemberUserIdSearchSuccess extends BaseController {
//    private static final long serialVersionUID = 5391766864750746823L;
//    
//    /** ===== (1) 사용하고자 하는 Helper + Service 객체 선언 ===== */
//    // Logger logger 는 BaseController에서 이미 객체 선언이 되어 있음으로 여기서는 또 선언할 필요가 없다.
//    // -> import project.spring.helper.WebHelper
//    WebHelper web;
//    // -> import project.spring.helper.RegexHelper
//    RegexHelper regex;
//
//    @Override
//    public String doRun(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        /** ===== (2) 사용하고자 하는 Helper + Service 객체 생성 ===== */
//        // -> import org.apache.logging.log4j.LogManager;
//        logger = LogManager.getFormatterLogger(request.getRequestURI());
//        web = WebHelper.getInstance(request, response);
//        regex = RegexHelper.getInstance();
//        
//        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
//        String referer = request.getHeader("referer");
//        if(referer == null) {
//            // 정상적인 접근 방법이 아닐 경우
//            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//            web.redirect(web.getRootPath() + "/error/error_page.do", null);
//            return null;
//        } else {
//            if(regex.isIndexCheck(referer, "member_join_or_userId_userPw_search_ok.do")) {
//                // 거쳐야할 절차를 건너뛴 경우
//                logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//                web.redirect(web.getRootPath() + "/error/error_page.do", null);
//                return null;
//            }
//        }
//        
//        /** ===== (3) 로그인 여부 검사 ===== */
//        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
//        if(web.getSession("loginInfo") != null) {
//            web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
//            return null;
//        }
//        
//        /** ===== (4) 파라미터 받기 ===== */
//        // 입력된 이메일 인증 폼의 값을 받는다.
//        String cert_ok = (String) web.getSession("cert_ok");
//        String userName = (String) web.getSession("userName");
//        String gender = (String) web.getSession("gender");
//        String year = (String) web.getSession("year");
//        String month = (String) web.getSession("month");
//        String day = (String) web.getSession("day");
//        String email = (String) web.getSession("email");
//        String certNum = (String) web.getSession("certNum");
//        // 찾아온 회원의 정보를 받는다.
//        String userId = (String) web.getSession("userId");
//        String regDate = (String) web.getSession("regDate");
//        int memberId = (int) web.getSession("memberId");
//        
//        // 모든 세션 삭제
//        web.removeSession("cert_ok");
//        web.removeSession("userName");
//        web.removeSession("gender");
//        web.removeSession("year");
//        web.removeSession("month");
//        web.removeSession("day");
//        web.removeSession("email");
//        web.removeSession("certNum");
//        web.removeSession("userId");
//        web.removeSession("regDate");
//        web.removeSession("memberId");
//        
//        // 우선 로그에 기록
//        logger.debug("cert_ok = " + cert_ok);
//        logger.debug("userName = " + userName);
//        logger.debug("gender = " + gender);
//        logger.debug("year = " + year);
//        logger.debug("month = " + month);
//        logger.debug("day = " + day);
//        logger.debug("email = " + email);
//        logger.debug("certNum = " + certNum);
//        logger.debug("userId = " + userId);
//        logger.debug("regDate = " + regDate);
//        logger.debug("memberId = " + memberId);
//        
//        /** ===== (5) 입력값의 유효성 검사 ===== */
//        // 이메일 인증 검사
//        if(!cert_ok.equals("true")) {
//            web.redirect(null, "이메일 인증에 성공하지 못하였습니다.");
//            return null;
//        }
//        
//        // 이름 검사
//        if(!regex.isValue(userName)) {
//            web.redirect(null, "이름을 입력하세요.");
//            return null;
//        }
//        if(!regex.isKor(userName)) {
//            web.redirect(null, "이름은 한글만 가능합니다.");
//            return null;
//        }
//        if(!regex.isMinLength(userName, 2)) {
//            web.redirect(null, "이름은 최소 2자 이상 입력해야 합니다.");
//            return null;
//        }
//        if(!regex.isMaxLength(userName, 20)) {
//            web.redirect(null, "이름은 최대 20자 까지만 입력 가능합니다.");
//            return null;
//        }
//        
//        // 성별 검사
//        if(!regex.isValue(gender)) {
//            web.redirect(null, "성별을 선택해 주세요.");
//            return null;
//        }
//        if(!gender.equals("M") && !gender.equals("F")) {
//            web.redirect(null, "성별이 잘못되었습니다.");
//            return null;
//        }
//        
//        // 생년월일 검사
//        if(!regex.isValue(year)) {
//            web.redirect(null, "생년월일을 선택해 주세요.");
//            return null;
//        }
//        if(!regex.isValue(month)) {
//            web.redirect(null, "생년월일을 선택해 주세요.");
//            return null;
//        }
//        if(!regex.isValue(day)) {
//            web.redirect(null, "생년월일을 선택해 주세요.");
//            return null;
//        }
//        
//        // 이메일 검사
//        if(!regex.isValue(email)) {
//            web.redirect(null, "이메일을 입력해 주세요.");
//            return null;
//        }
//        if(!regex.isEmail(email)) {
//            web.redirect(null, "이메일 주소의 형식이 잘못되었습니다.");
//            return null;
//        }
//        
//        // 이메일 인증번호 검사
//        if(!regex.isValue(certNum)) {
//            web.redirect(null, "이메일 인증번호를 입력해 주세요.");
//            return null;
//        }
//        
//        // 들고온 아이디 검사
//        if(!regex.isValue(userId) || !regex.isValue(regDate) || memberId == 0) {
//            web.redirect(null, "회원님의 정보를 찾을 수 없습니다.");
//            return null;
//        }
//        
//        // View에 출력할 아이디와 생성일을 JSTL변수에 담는다.
//        request.setAttribute("userId", userId);
//        String[] regDateSplit = regDate.split("-");
//        request.setAttribute("year", regDateSplit[0]);
//        request.setAttribute("month", regDateSplit[1]);
//        request.setAttribute("day", regDateSplit[2]);
//        request.setAttribute("memberId", memberId);
//        
//        return "member/member_userId_search_success";
//    }
//
//}
