package project.spring.travel.controller.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.spring.helper.MailHelper;
import project.spring.helper.RegexHelper;
import project.spring.helper.Util;
import project.spring.helper.WebHelper;
import project.spring.travel.model.Member;
import project.spring.travel.model.MemberCertification;
import project.spring.travel.service.MemberCertificationService;
import project.spring.travel.service.MemberService;

/**
 * @fileName    : MemberCheckController.java
 * @author      : 홍준성
 * @description : 인증번호 발송 및 본인확인 및 아이디 중복 확인 처리까지 컨트롤러 모음
 * @lastUpdate  : 2019. 4. 23.
 */
/** 컨트롤러 선언 */
@Controller
public class MemberCheckController {
    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    Logger logger = LoggerFactory.getLogger(MemberCheckController.class); // Log4j 객체 직접 생성
    // -> import org.apache.ibatis.session.SqlSession;
    @Autowired
    SqlSession sqlSession;
    // -> import project.spring.helper.WebHelper
    @Autowired
    WebHelper web;
    // -> import project.spring.helper.MailHelper
    @Autowired
    MailHelper mail;
    // -> import project.spring.helper.RegexHelper
    @Autowired
    RegexHelper regex;
    // -> import project.spring.helper.Util
    @Autowired
    Util util;
    // -> import project.java.travel.service.MemberService
    @Autowired
    MemberService memberService;
    // -> import project.spring.travel.service.MemberCertificationService;
    @Autowired
    MemberCertificationService memberCertificationService;
    
    /**
     * 이메일 인증번호 발송 컨트롤러 기본 구성
     */
    @RequestMapping(value = "/member/certification_ok.do", method = RequestMethod.POST)
    public String certificationOk(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** 컨텐츠 타입 명시 */
        response.setContentType("application/json");
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_join.do") && regex.isIndexCheck(referer, "member_userId_search.do") && regex.isIndexCheck(referer, "member_userPw_search.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            try {
                response.sendRedirect(web.getRootPath() + "/error/error_page.do");
            } catch (IOException e) {
                logger.debug(e.getLocalizedMessage());
                return null;
            }
            return null;
        }
        
        /** 로그인 여부 검사 */
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        if(web.getSession("loginInfo") != null) {
            web.printJsonRt("LOGIN_OK");
            return null;
        }
        
        /** 인증번호를 생성한다. */
        String getCertification = util.getCertification(6, 2);
        
        /** 파라미터 받기 */
        // 입력된 메일 주소를 받는다.
        String email = web.getString("email");
        String userName = web.getString("userName");
        logger.debug("email = " + email);
        logger.debug("userName = " + userName);
        
        /**
         * ===== 이메일 발송 전에 이메일 존재유무 검사 =====
         * - emailCheck Method를 호출하여 이곳에 들어온 페이지에 따라 분기한다.
         */
        Member member = new Member();
        member.setEmail(email);
        int result = 0;
        if(!regex.isIndexCheck(referer, "member_join.do")) {
            try {
                result = memberService.emailCheck(member);
            } catch (Exception e) {
                web.printJsonRt(e.getLocalizedMessage());
                return null;
            }
            // 기존에 가입된 회원일 경우
            if(result > 0) {
                web.printJsonRt("이미 가입된 이메일 입니다.");
                return null;
            }
        } else if(!regex.isIndexCheck(referer, "member_userId_search.do") || !regex.isIndexCheck(referer, "member_userPw_search.do")) {
            try {
                result = memberService.emailCheck(member);
            } catch (Exception e) {
                web.printJsonRt(e.getLocalizedMessage());
                return null;
            }
            // 존재하지 않는 회원일 경우
            if(result == 0) {
                web.printJsonRt("가입되지 않은 이메일 입니다.");
                return null;
            }
        }
        
        /** 발급된 인증번호를 메일로 발송하기 */
        String sender = "ghdwnstjd13@gmail.com";
        String subject = userName + "님께 완벽한 여행 회원가입을 위한 인증번호가 도착했습니다.";
        String content = "<span style='font-size: 20px;'>" + userName + "님의 인증번호는 <strong>" + getCertification + "</strong>입니다.</span>";
        
        /** 메일 발송 처리 */
        try {
            // sendMail() 메서드 선언시 throws를 정의했기 때문에 예외처리가 요구된다.
            mail.sendMail(sender, email, subject, content);
        } catch(MessagingException e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("메일 발송에 실패했습니다. 관리자에게 문의 바랍니다.");
            return null;
        } catch(Exception e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("알 수 없는 이유로 메일 발송에 실패했습니다.");
            return null;
        }
        
        /** 인증번호를 저장할 Beans 묶음 */
        MemberCertification memberCertification = new MemberCertification();
        memberCertification.setEmail(email);
        memberCertification.setCertificationNum(getCertification);
        /** 발송된 인증번호를 저장 처리 */
        try {
            memberCertificationService.insertMemberCertification(memberCertification);
        } catch (Exception e) {
            web.printJsonRt(e.getLocalizedMessage());
            return null;
        }
        
        /** 처리 결과를 JSON으로 출력하기 */
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("rt", "OK");
        logger.debug(userName + "님의 인증번호 = ", getCertification);
        System.out.println(userName + "님의 인증번호 = " + getCertification);
        
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(response.getWriter(), data);
        } catch (JsonGenerationException e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
            return null;
        } catch (JsonMappingException e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
            return null;
        } catch (IOException e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
            return null;
        }
        
        return null;
    } // End certificationOk Method
    
    /**
     * 이메일 인증번호 확인 액션 컨트롤러
     */
    @RequestMapping(value = "/member/certification_check_ok.do", method = RequestMethod.POST)
    public String certificationCheckOk(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** 컨텐츠 타입 명시 */
        response.setContentType("application/json");
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_join.do") && regex.isIndexCheck(referer, "member_userId_search.do") && regex.isIndexCheck(referer, "member_userPw_search.do") && regex.isIndexCheck(referer, "member_edit_form.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            try {
                response.sendRedirect(web.getRootPath() + "/error/error_page.do");
            } catch (IOException e) {
                logger.debug(e.getLocalizedMessage());
                return null;
            }
            return null;
        }
        
        /** 로그인 여부 검사 */
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!regex.isIndexCheck(referer, "member_edit_form.do")) {
            // 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
            if(loginInfo == null) {
                web.printJsonRt("X-LOGIN");
                return null;
            }
        } else {
            // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
            if(loginInfo != null) {
                web.printJsonRt("LOGIN_OK");
                return null;
            }
        }
        
        /** 파라미터 받기 */
        // 입력된 메일 주소와 인증번호를 받는다.
        String email = web.getString("email");
        String certNum = web.getString("certNum");
        logger.debug("email = " + email);
        logger.debug("certNum = " + certNum);
        
        /** 유효성 검사 */
        if(!regex.isValue(email)) {
            web.printJsonRt("이메일을 입력해주세요.");
            return null;
        }
        if(!regex.isValue(certNum)) {
            web.printJsonRt("이메일 인증번호를 입력해주세요.");
            return null;
        }
        
        /** 본인 인증을 위해 Beans로 묶는다. */
        MemberCertification memberCertification = new MemberCertification();
        memberCertification.setEmail(email);
        memberCertification.setCertificationNum(certNum);
        
        /** 본인 인증 진행 */
        try {
            int result = memberCertificationService.selectMemberCertification(memberCertification);
            
            // 본인인증에 실패한 경우
            if(result < 1) {
                web.printJsonRt("인증에 실패했습니다.");
                return null;
            }
        } catch (Exception e) {
            web.printJsonRt(e.getLocalizedMessage());
            return null;
        }
        
        /** 처리 결과를 JSON으로 출력하기 */
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("rt", "OK");
        
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(response.getWriter(), data);
        } catch (JsonGenerationException e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
            return null;
        } catch (JsonMappingException e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
            return null;
        } catch (IOException e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
            return null;
        }
        
        return null;
    } // End certificationCheckOk Method
    
    /**
     * 회원정보 수정 시 이메일 중복확인 및 인증번호 발송 처리
     */
    @RequestMapping(value = "/member/email_check_ok.do", method = RequestMethod.POST)
    public String emailCheckOk(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** 컨텐츠 타입 명시 */
        response.setContentType("application/json");
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_edit_form.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            sqlSession.close();
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            try {
                response.sendRedirect(web.getRootPath() + "/error/error_page.do");
            } catch (IOException e) {
                logger.debug(e.getLocalizedMessage());
                return null;
            }
            return null;
        }

        /** 로그인 여부 검사 */
        // 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(loginInfo == null) {
            web.printJsonRt("X-LOGIN");
            return null;
        }
        
        /** 인증번호를 생성한다. */
        String getCertification = util.getCertification(6, 2);
        
        /** 파라미터 받기 */
        // 입력된 메일 주소를 받는다.
        String email = web.getString("email");
        logger.debug("email = " + email);

        /**
         * ===== 이메일 발송 전에 이메일 존재유무 검사 =====
         */
        Member member = new Member();
        member.setEmail(email);
        int result = 0;
        try {
            result = memberService.emailCheck(member);
        } catch (Exception e) {
            web.printJsonRt(e.getLocalizedMessage());
            return null;
        }
        // 기존에 가입된 회원일 경우
        if(result > 0) {
            web.printJsonRt("이미 가입된 이메일 입니다.");
            return null;
        }
        
        /** 발급된 인증번호를 메일로 발송하기 */
        String sender = "ghdwnstjd13@gmail.com";
        String subject = loginInfo.getUserName() + "님께 완벽한 여행 회원정보 수정을 위한 인증번호가 도착했습니다.";
        String content = "<span style='font-size: 20px;'>" + loginInfo.getUserName() + "님의 인증번호는 <strong>" + getCertification + "</strong>입니다.</span>";
        
        /** 메일 발송 처리 */
        try {
            // sendMail() 메서드 선언시 throws를 정의했기 때문에 예외처리가 요구된다.
            mail.sendMail(sender, email, subject, content);
        } catch(MessagingException e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("메일 발송에 실패했습니다. 관리자에게 문의 바랍니다.");
            return null;
        } catch(Exception e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("알 수 없는 이유로 메일 발송에 실패했습니다.");
            return null;
        }
        
        /** 인증번호를 저장할 Beans 묶음 */
        MemberCertification memberCertification = new MemberCertification();
        memberCertification.setEmail(email);
        memberCertification.setCertificationNum(getCertification);
        /** 발송된 인증번호를 저장 처리 */
        try {
            memberCertificationService.insertMemberCertification(memberCertification);
        } catch (Exception e) {
            web.printJsonRt(e.getLocalizedMessage());
            return null;
        }
        
        /** 처리 결과를 JSON으로 출력하기 */
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("rt", "OK");
        logger.debug(loginInfo.getUserName() + "님의 인증번호 = ", getCertification);
        System.out.println(loginInfo.getUserName() + "님의 인증번호 = " + getCertification);
        
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(response.getWriter(), data);
        } catch (JsonGenerationException e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
            return null;
        } catch (JsonMappingException e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
            return null;
        } catch (IOException e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
            return null;
        }
        
        return null;
    } // End emailCheckOk Method
    
    /**
     * 아이디 중복검사를 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/member/user_id_check_ok.do", method = RequestMethod.POST)
    public String userIdCheckOk(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** 컨텐츠 타입 명시 */
        response.setContentType("application/json");
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_join_form.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            try {
                response.sendRedirect(web.getRootPath() + "/error/error_page.do");
            } catch (IOException e) {
                logger.debug(e.getLocalizedMessage());
                return null;
            }
            return null;
        }
        
        /** (3) 로그인 여부 검사 */
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        if(web.getSession("loginInfo") != null) {
            web.printJsonRt("LOGIN_OK");
            return null;
        }
        
        /** (4) 사용자 입력값 받기 */
        String userId = web.getString("userId");
        // 사용자 입력값 기록
        logger.debug("userId = " + userId);
        
        /** (5) 필수 값의 존재여부 검사 */
        // 아이디 검사
        if(!regex.isValue(userId)) {
            web.printJsonRt("아이디를 입력해 주세요.");
            return null;
        }
        if(!regex.isEngNum(userId)) {
            web.printJsonRt("아이디는 영어와 숫자 조합만 입력 가능합니다.");
            return null;
        }
        if(!regex.isMinLength(userId, 4)) {
            web.printJsonRt("아이디는 최소 4자 이상 입력하셔야 합니다.");
            return null;
        }
        if(!regex.isMaxLength(userId, 20)) {
            web.printJsonRt("아이디는 최대 20자 까지만 입력 가능합니다.");
            return null;
        }
        
        /** (6) 전달받은 파라미터를 Beans에 설정한다. */
        Member member = new Member();
        member.setUserId(userId);
        
        /**
         * ===== Service 계층의 기능을 통한 사용자 인증 =====
         * - 파라미터가 저장된 Beans를 Service에게 전달한다.
         * - Service는 MyBatis의 Mapper를 호출하고 조회 결과를 리턴할 것이다.
         */
        /** (7) Service를 통한 아이디 중복 검사 */
        int result = 0;
        try {
            result = memberService.userIdCheck(member);
        } catch (Exception e) {
            web.printJsonRt(e.getLocalizedMessage());
            return null;
        }
        
        /** (8) 처리 결과를 JSON으로 출력하기 */
        if(result > 0) {
            web.printJsonRt("사용 불가능한 아이디 입니다.");
        } else {
            web.printJsonRt("OK");
        }
        
        return null;
    } // End userIdCheckOk Method
    
}
