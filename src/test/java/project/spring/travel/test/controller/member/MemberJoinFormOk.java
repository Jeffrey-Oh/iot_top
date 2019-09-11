package project.spring.travel.test.controller.member;
//package project.spring.travel.controller.member;
//
//import java.io.IOException;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.ibatis.session.SqlSession;
//import org.apache.logging.log4j.LogManager;
//
//import project.java.travel.dao.MyBatisConnectionFactory;
//import project.java.travel.model.Member;
//import project.java.travel.service.MemberService;
//import project.java.travel.service.impl.MemberServiceImpl;
//import project.spring.helper.BaseController;
//import project.spring.helper.FileInfo;
//import project.spring.helper.RegexHelper;
//import project.spring.helper.UploadHelper;
//import project.spring.helper.Util;
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : MemberJoinFormOk.java
// * @author      : 홍준성
// * @description : 회원가입 3단계 정보입력 액션 컨트롤러
// * @lastUpdate    : 2019. 3. 29.
// */
//@WebServlet("/member/member_join_form_ok.do")
//public class MemberJoinFormOk extends BaseController {
//    private static final long serialVersionUID = -9050498212701714089L;
//    
//    /**
//     * ===== 로그인 중에는 접근할 수 없는 페이지 =====
//     * - 로그인 페이지는 로그인 상태에서는 접근할 수 없도록 처리해야 하기 때문에
//     *     세션이 존재한다면 (null이 아니라면) 접근할 수 없도록 차단한다.
//     */
//    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
//    // Logger logger 는 BaseController에서 이미 객체 선언이 되어 있음으로 여기서는 또 선언할 필요가 없다.
//    // -> import org.apache.ibatis.session.SqlSession;
//    SqlSession sqlSession;
//    // -> import project.spring.helper.WebHelper
//    WebHelper web;
//    // -> import project.spring.helper.RegexHelper
//    RegexHelper regex;
//    // -> import project.spring.helper.UploadHelper
//    UploadHelper upload;
//    // -> import project.spring.helper.Util;
//    Util util;
//    Calendar cal;
//    // -> import project.java.travel.service.MemberService
//    MemberService memberService;
//
//    @Override
//    public String doRun(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//
//        /** (2) 사용하고자 하는 Helper + Service 객체 생성 */
//        // -> import org.apache.logging.log4j.LogManager;
//        logger = LogManager.getFormatterLogger(request.getRequestURI());
//        // -> import study.jsp.myschool.dao.MyBatisConnectionFactory;
//        sqlSession = MyBatisConnectionFactory.getSqlSession();
//        web = WebHelper.getInstance(request, response);
//        regex = RegexHelper.getInstance();
//        upload = UploadHelper.getInstance();
//        util = Util.getInstance();
//        cal = Calendar.getInstance();
//        // 회원가입 처리를 위한 Service객체
//        // -> import study.jsp.mysite.service.impl.MemberServiceImpl
//        memberService = new MemberServiceImpl(sqlSession, logger);
//        
//        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
//        String referer = request.getHeader("referer");
//        if(referer == null) {
//            // 정상적인 접근 방법이 아닐 경우
//            sqlSession.close();
//            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//            web.redirect(web.getRootPath() + "/error/error_page.do", null);
//            return null;
//        } else {
//            if(regex.isIndexCheck(referer, "member_join_form.do")) {
//                // 거쳐야할 절차를 건너뛴 경우
//                sqlSession.close();
//                logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//                web.redirect(web.getRootPath() + "/error/error_page.do", null);
//                return null;
//            }
//        }
//        
//        /** (3) 로그인 여부 검사 */
//        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
//        if(web.getSession("loginInfo") != null) {
//            sqlSession.close();
//            web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
//            return null;
//        }
//        
//        /** ===== (4) 파일이 포함된 POST 파라미터 받기 ===== */
//        // 입력된 이메일 인증 폼의 값을 받는다.
//        String cert_ok = (String) web.getSession("cert_ok");
//        String userName = (String) web.getSession("userName");
//        String gender = (String) web.getSession("gender");
//        String year = (String) web.getSession("year");
//        String month = (String) web.getSession("month");
//        String day = (String) web.getSession("day");
//        String email = (String) web.getSession("email");
//        String certNum = (String) web.getSession("certNum");
//        // member_join_agreement에서 보낸 파라미터를 추가한다.
//        String serviceCheck = (String) web.getSession("serviceCheck");
//        String infoCollectionCheck = (String) web.getSession("infoCollectionCheck");
//        String consignmentCheck = (String) web.getSession("consignmentCheck");
//        String marketingCheck = (String) web.getSession("marketingCheck");
//        String allCheck = (String) web.getSession("allCheck");
//        /**
//         * ===== 사용자의 입력값 받기 =====
//         * - 회원가입 페이지에 포함된 프로필 이미지 업로드로 인해
//         *     <form>태그에 ecntype="multipart/form-data"가 적용되어 있다.
//         * - 이 경우 WebHelper를 통한 파라미터 수신이 동작하지 않기 때문에,
//         *     UploadHelper를 통해 받아야 한다.
//         *     - WebHelper의 getString()|getInt() 메서드는 더 이상 사용할 수 없다.
//         */
//        try {
//            upload.multipartRequest(request);
//        } catch (Exception e) {
//            sqlSession.close();
//            web.redirect(null, "multipart 데이터가 아닙니다.");
//            return null;
//        }
//        // member_join_form에서 보낸 파라미터를 추가한다.
//        // UploadHelper에서 텍스트 형식의 파라미터를 분류한 Map을 리턴받아서 값을 추출한다.
//        /**
//         * ===== UploadHelper에 저장되어 있는 파라미터 추출하기 =====
//         * - UploadHelper는 내부적으로 텍스트 파라미터를 Map 객체로 분류해 둔다.
//         * - 이 변수를 추출하여 로그를 통해 값을 확인한다.
//         */
//        Map<String, String> paramMap = upload.getParamMap();
//        String userId_check_ok = paramMap.get("userId_check_ok");
//        String userId = paramMap.get("userId");
//        String userPw = paramMap.get("userPw");
//        String userPwCheck = paramMap.get("userPwCheck");
//        String phone = paramMap.get("phone");
//        String postcode = paramMap.get("postcode");
//        String addr1 = paramMap.get("addr1");
//        String addr2 = paramMap.get("addr2");
//        String emailCheck = paramMap.get("emailCheck");
//        String smsCheck = paramMap.get("smsCheck");
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
//        web.removeSession("serviceCheck");
//        web.removeSession("infoCollectionCheck");
//        web.removeSession("consignmentCheck");
//        web.removeSession("marketingCheck");
//        web.removeSession("allCheck");
//        
//        /** 우선 로그에 기록 */
//        // member_join에서 보낸 파라미터를 기록한다.
//        logger.debug("cert_ok = " + cert_ok);
//        logger.debug("userName = " + userName);
//        logger.debug("gender = " + gender);
//        logger.debug("year = " + year);
//        logger.debug("month = " + month);
//        logger.debug("day = " + day);
//        logger.debug("email = " + email);
//        logger.debug("certNum = " + certNum);
//        // member_join_agreement에서 보낸 파라미터를 기록한다.
//        logger.debug("serviceCheck = " + serviceCheck);
//        logger.debug("infoCollectionCheck = " + infoCollectionCheck);
//        logger.debug("consignmentCheck = " + consignmentCheck);
//        logger.debug("marketingCheck = " + marketingCheck);
//        logger.debug("allCheck = " + allCheck);
//        // member_join_form에서 보낸 파라미터를 기록한다.
//        logger.debug("userId_check_ok = " + userId_check_ok);
//        logger.debug("userId = " + userId);
//        logger.debug("userPw = " + userPw);
//        logger.debug("userPwCheck = " + userPwCheck);
//        logger.debug("phone = " + phone);
//        logger.debug("postcode = " + postcode);
//        logger.debug("addr1 = " + addr1);
//        logger.debug("addr2 = " + addr2);
//        logger.debug("emailCheck = " + emailCheck);
//        logger.debug("smsCheck = " + smsCheck);
//        
//        /**
//         * ===== 가입시에 입력한 내용에 대한 유효성 검사 =====
//         * - 회원가입시 입력되는 내용들은
//         *     데이터베이스 테이블에 저장될 항목들이기 때문에
//         *     데이터베이스의 제약조건에 위배되지 않도록
//         *     최대한 철저하게 검증해야 한다.
//         */
//        /** ===== (5) 입력값의 유효성 검사 ===== */
//        // member_join에서 보낸 파라미터를 검사한다.
//        // 이메일 인증 검사
//        if(!cert_ok.equals("true")) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "이메일 인증에 성공하지 못하였습니다.");
//            return null;
//        }
//        
//        // 이름 검사
//        if(!regex.isValue(userName)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "이름을 입력하세요.");
//            return null;
//        }
//        if(!regex.isKor(userName)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "이름은 한글만 가능합니다.");
//            return null;
//        }
//        if(!regex.isMinLength(userName, 2)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "이름은 최소 2자 이상 입력해야 합니다.");
//            return null;
//        }
//        if(!regex.isMaxLength(userName, 20)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "이름은 최대 20자 까지만 입력 가능합니다.");
//            return null;
//        }
//        
//        // 성별 검사
//        if(!regex.isValue(gender)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "성별을 선택해 주세요.");
//            return null;
//        }
//        if(!gender.equals("M") && !gender.equals("F")) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "성별이 잘못되었습니다.");
//            return null;
//        }
//        
//        // 생년월일 검사
//        if(!regex.isValue(year)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "생년월일을 선택해 주세요.");
//            return null;
//        }
//        if(!regex.isValue(month)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "생년월일을 선택해 주세요.");
//            return null;
//        }
//        if(!regex.isValue(day)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "생년월일을 선택해 주세요.");
//            return null;
//        }
//        
//        // 이메일 검사
//        if(!regex.isValue(email)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "이메일을 입력해 주세요.");
//            return null;
//        }
//        if(!regex.isEmail(email)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "이메일 주소의 형식이 잘못되었습니다.");
//            return null;
//        }
//        
//        // 이메일 인증번호 검사
//        if(!regex.isValue(certNum)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "이메일 인증번호를 입력해 주세요.");
//            return null;
//        }
//        
//        // member_join_agreement에서 보낸 파라미터를 검사한다.
//        // 서비스 이용약관 동의 검사
//        if(!regex.isValue(serviceCheck)) {
//            if(!serviceCheck.equals("on")) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "서비스 이용약관에 동의하셔야 합니다.");
//                return null;
//            }
//        }
//        // 서비스 이용약관 동의 검사
//        if(!regex.isValue(infoCollectionCheck)) {
//            if(!infoCollectionCheck.equals("on")) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "개인정보 수집 및 이용에 동의하셔야 합니다.");
//                return null;
//            }
//        }
//        // 서비스 이용약관 동의 검사
//        if(!regex.isValue(consignmentCheck)) {
//            if(!consignmentCheck.equals("on")) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "개인정보 처리 업무 위탁에 동의하셔야 합니다.");
//                return null;
//            }
//        }
//        // 마케팅 목적 이용에 관한 검사
//        if(marketingCheck != null) {
//            if(!marketingCheck.equals("on")) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "마케팅 목적 이용에 관한 동의가 잘못되었습니다.");
//                return null;
//            }
//        }
//        
//        // member_join_agreement에서 보낸 파라미터를 검사한다.
//        // 아이디 중복 검사
//        if(!userId_check_ok.equals("true")) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "사용할수 없는 아이디 입니다.");
//            return null;
//        }
//        
//        // 아이디 검사
//        if(!regex.isValue(userId)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "아이디를 입력하세요.");
//            return null;
//        }
//        if(!regex.isEngNum(userId)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "아이디는 영어와 숫자 조합만 입력 가능합니다.");
//            return null;
//        }
//        if(!regex.isMinLength(userId, 4)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "아이디는 최소 4자 이상 입력하셔야 합니다.");
//            return null;
//        }
//        if(!regex.isMaxLength(userId, 20)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "아이디는 최대 20자 까지만 입력 가능합니다.");
//            return null;
//        }
//        
//        // 비밀번호 값 검사
//        if(!regex.isValue(userPw)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "비밀번호를 입력해 주세요.");
//            return null;
//        }
//        if(!regex.isMinLength(userPw, 6)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "비밀번호는 최소 6자 이상 입력하셔야 합니다.");
//            return null;
//        }
//        if(!regex.isMaxLength(userPw, 20)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "비밀번호는 최대 20자 까지만 입력 가능합니다.");
//            return null;
//        }
//        if(!regex.isPassword(userPw)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "비밀번호는 적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함되어야 합니다.");
//            return null;
//        }
//        if(!regex.isIndexCheck(userPw, userId)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "비밀번호에는 아이디가 포함되면 안됩니다.");
//            return null;
//        }
//        // 비밀번호 확인 검사
//        if(!regex.isValue(userPwCheck)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "비밀번호 확인을 입력해 주세요.");
//            return null;
//        }
//        if(!regex.isCompareTo(userPw, userPwCheck)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "비밀번호 확인이 잘못되었습니다.");
//            return null;
//        }
//        
//        // 연락처 검사
//        if(!regex.isValue(phone)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "연락처를 입력해 주세요.");
//            return null;
//        }
//        if(!regex.isPhone(phone)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "연락처의 형식이 잘못되었습니다.");
//            return null;
//        }
//        
//        // 우편번호, 주소, 상세주소 검사
//        if(!regex.isValue(postcode)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "우편번호를 입력해 주세요.");
//            return null;
//        }
//        if(!regex.isValue(addr1)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "주소를 입력해 주세요.");
//            return null;
//        }
//        if(!regex.isValue(addr2)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "상세주소를 입력해 주세요.");
//            return null;
//        }
//        
//        // 이메일 수신여부 검사
//        if(!regex.isValue(emailCheck)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "이메일 수신여부를 선택해 주세요.");
//            return null;
//        }
//        // SMS 수신여부 검사
//        if(!regex.isValue(smsCheck)) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "SMS 수신여부를 선택해 주세요.");
//            return null;
//        }
//        
//        /**
//         * ===== 업로드 된 첨부파일이 존재할 경우의 처리 =====
//         * - 업로드 된 파일이 존재한다면 업로드 파일이 저장된 경로를 List 객체로 리턴받는다.
//         * - 멀티업로드가 아니기 때문에 List의 0번째 데이터만을 추출하여 처리할 수 있다.
//         */
//        /** (6) 업로드 된 파일 정보 추출 */
//        List<FileInfo> fileList = upload.getFileList();
//        // 업로드 된 프로필 사진 경로가 저장될 변수
//        String profileImg = null;
//        
//        // 업로드 된 파일이 존재할 경우만 변수값을 할당한다.
//        if(fileList.size() > 0) {
//            // 단일 업로드이므로 0번째 항목만 가져온다.
//            FileInfo info = fileList.get(0);
//            profileImg = info.getFileDir() + "/" + info.getFileName();
//        }
//        
//        // 파일 경로를 로그로 기록
//        logger.debug("profileImg = " + profileImg);
//        
//        /**
//         * ===== 준비된 모든 데이터를 JavaBeans로 묶은 후 Service에 전달 =====
//         * - Service를 통해 Mapper가 호출되고 Database에 Insert 된다.
//         */
//        /** (7) 전달받은 파라미터를 Beans 객체에 담는다. */
//        Member member = new Member();
//        member.setUserName(userName);
//        member.setGender(gender);
//        // 생년월일을 위해 문자 조합을 한다.
//        /** mm, dd 형식에 맞게끔 작업 */
//        if(Integer.parseInt(month) < 10) {
//            month = "0" + month;
//        }
//        if(Integer.parseInt(day) < 10) {
//            day = "0" + day;
//        }
//        String birthDate = year + "-" + month + "-" + day;
//        member.setBirthDate(birthDate);
//        member.setUserId(userId);
//        member.setUserPw(userPw);
//        member.setPhone(phone);
//        member.setEmail(email);
//        member.setPostcode(postcode);
//        member.setAddress1(addr1);
//        member.setAddress2(addr2);
//        // 오늘 날짜를 형식에 맞게 조합을 한다.
//        String nowDate = util.getPrintDate2(cal);
//        member.setMarketingCheckedDate(marketingCheck != null ? nowDate : null);
//        member.setToEmailCheckedDate(emailCheck.equals("agree") ? nowDate : null);
//        member.setToSmsCheckedDate(smsCheck.equals("agree") ? nowDate : null);
//        member.setProfileImg(profileImg);
//        
//        /** (8) Service를 통한 데이터베이스 저장 처리 */
//        int emailCheckResult = 0;
//        int userIdCheckResult = 0;
//        try {
//            emailCheckResult = memberService.emailCheck(member);
//            userIdCheckResult = memberService.userIdCheck(member);
//        } catch (Exception e) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, e.getLocalizedMessage());
//            // 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
//            return null;
//        }
//        
//        if(emailCheckResult > 0) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "이미 사용중인 이메일 입니다.");
//            // 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
//            return null;
//        }
//        if(userIdCheckResult > 0) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, "이미 사용중인 아이디 입니다.");
//            // 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
//            return null;
//        }
//        
//        try {
//            memberService.memberCheck(member);
//            memberService.addMember(member);
//        } catch (Exception e) {
//            upload.removeTempFile();
//            sqlSession.close();
//            web.redirect(null, e.getLocalizedMessage());
//            // 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
//            return null;
//        }
//        
//        // 가입 성공 페이지로 보낼 이름 세션에 저장
//        web.setSession("userName", userName);
//        /**
//         * ===== 가입 완료 후 페이지 이동 처리 =====
//         */
//        /** (9) 가입이 완료되었으므로 가입 성공 페이지로 이동 */
//        sqlSession.close();
//        web.redirect(web.getRootPath() + "/member/member_join_success.do", null);
//        
//        return null;
//    }
//
//}
