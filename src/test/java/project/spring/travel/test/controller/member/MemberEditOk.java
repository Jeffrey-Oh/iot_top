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
// * @fileName    : MemberEditOk.java
// * @author      : 홍준성
// * @description : 회원정보 수정 처리를 위한 입력에 따른 액션 컨트롤러
// * @lastUpdate  : 2019. 4. 6.
// */
//@WebServlet("/member/member_edit_ok.do")
//public class MemberEditOk extends BaseController {
//    private static final long serialVersionUID = -7945575849297450617L;
//    
//    /** 사용하고자 하는 Helper + Service 객체 선언 */
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
//        // 회원정보 수정 처리를 위한 Service객체
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
//            if(regex.isIndexCheck(referer, "member_edit_form.do")) {
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
//        Member loginInfo = (Member) web.getSession("loginInfo");
//        if(loginInfo == null) {
//            sqlSession.close();
//            web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
//            return null;
//        }
//        
//        /** member_edit_door에서 전달한 세션 받기 */
//        String edit_door_userPw = (String) web.getSession("edit_door_userPw");
//        // 로그에 기록
//        logger.debug("edit_door_userPw = " + edit_door_userPw);
//        // 사용한 세션 삭제
//        web.removeSession("edit_door_userPw");
//        
//        /** 본인 검사 */
//        if(!regex.isValue(edit_door_userPw)) {
//            sqlSession.close();
//            web.redirect(null, "비밀번호가 잘못되었습니다.");
//            return null;
//        }
//        
//        /** (4) 파일이 포함된 POST 파라미터 받기 */
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
//        
//        // UploadHelper에서 텍스트 형식의 파라미터를 분류한 Map을 리턴받아서 값을 추출한다.
//        Map<String, String> paramMap = upload.getParamMap();
//        String email_check_ok = paramMap.get("email_check_ok");
//        String cert_ok = paramMap.get("cert_ok");
//        String userPw = paramMap.get("userPw");
//        String userPwCheck = paramMap.get("userPwCheck");
//        String phone = paramMap.get("phone");
//        String email = paramMap.get("email");
//        String certNum = paramMap.get("certNum");
//        String postcode = paramMap.get("postcode");
//        String addr1 = paramMap.get("addr1");
//        String addr2 = paramMap.get("addr2");
//        String emailCheck = paramMap.get("emailCheck");
//        String smsCheck = paramMap.get("smsCheck");
//        // 추가 - 이미지 삭제 여부에 대한 체크박스
//        String imgDel = paramMap.get("img_del");
//        
//        /** 전달받은 데이터에 대한 로그 확인 */
//        // member_edit_form에서 보낸 파라미터를 기록한다.
//        logger.debug("email_check_ok = " + email_check_ok);
//        logger.debug("cert_ok = " + cert_ok);
//        logger.debug("userPw = " + userPw);
//        logger.debug("userPwCheck = " + userPwCheck);
//        logger.debug("phone = " + phone);
//        logger.debug("email = " + email);
//        logger.debug("certNum = " + certNum);
//        logger.debug("postcode = " + postcode);
//        logger.debug("addr1 = " + addr1);
//        logger.debug("addr2 = " + addr2);
//        logger.debug("emailCheck = " + emailCheck);
//        logger.debug("smsCheck = " + smsCheck);
//        logger.debug("img_del = " + imgDel);
//        
//        /** (5) 입력값의 유효성 검사 (아이디 검사 수행안함) */
//        if(!email.equals(loginInfo.getEmail())) {
//            // 이메일 중복 검사
//            if(!email_check_ok.equals("true")) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "사용할수 없는 이메일 입니다.");
//                return null;
//            }
//            
//            // 이메일 인증 검사
//            if(!cert_ok.equals("true")) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "이메일 인증에 성공하지 못하였습니다.");
//                return null;
//            }
//            
//            // 이메일 인증번호 검사
//            if(!regex.isValue(certNum)) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "이메일 인증번호를 입력해 주세요.");
//                return null;
//            }
//        }
//        
//        /**
//         * ===== 비밀번호 변경에 대한 유효성 검사 =====
//         * - 신규 비밀번호가 입력된 경우는 변경으로 간주하고,
//         *     입력하지 않은 경우는 변경하지 않도록 처리한다.
//         *     그러므로 입력된 경우만 검사해야 한다.
//         */
//        if(regex.isValue(userPw)) {
//            // 비밀번호 값 검사
//            if(!regex.isMinLength(userPw, 6)) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "비밀번호는 최소 6자 이상 입력하셔야 합니다.");
//                return null;
//            }
//            if(!regex.isMaxLength(userPw, 20)) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "비밀번호는 최대 20자 까지만 입력 가능합니다.");
//                return null;
//            }
//            if(!regex.isPassword(userPw)) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "비밀번호는 적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함되어야 합니다.");
//                return null;
//            }
//            if(!regex.isIndexCheck(userPw, loginInfo.getUserId())) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "비밀번호에는 아이디가 포함되면 안됩니다.");
//                return null;
//            }
//            // 비밀번호 확인 검사
//            if(!regex.isValue(userPwCheck)) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "비밀번호 확인을 입력해 주세요.");
//                return null;
//            }
//            if(!regex.isCompareTo(userPw, userPwCheck)) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "비밀번호 확인이 잘못되었습니다.");
//                return null;
//            }
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
//        // 이미지 삭제여부 검사
//        if(regex.isValue(imgDel)) {
//            if(!imgDel.endsWith("Y")) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "이미지 삭제 선택이 잘못되었습니다.");
//                return null;
//            }
//        }
//        
//        /** (6) 업로드 된 파일 정보 추출 */
//        // -> 이미지 수정을 원하지 않는 경우, 삭제만 원하는 경우
//        //     데이터 없음
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
//        // 파일경로를 로그로 기록
//        logger.debug("profileImg = " + profileImg);
//        
//        /** (7) 전달받은 파라미터를 Beans 객체에 담는다. */
//        // 이름, 성별, 생년월일, 아이디는 변경할 수 없으므로 제외한다.
//        Member member = new Member();
//        // Where절에 사용할 회원번호는 세션에서 취득
//        member.setMemberId(loginInfo.getMemberId());
//        member.setPhone(phone);
//        member.setEmail(email);
//        member.setPostcode(postcode);
//        member.setAddress1(addr2);
//        member.setAddress2(addr2);
//        // 오늘 날짜를 형식에 맞게 조합을 한다.
//        String nowDate = util.getPrintDate2(cal);
//        member.setToEmailCheckedDate(emailCheck.equals("agree") ? nowDate : null);
//        member.setToSmsCheckedDate(smsCheck.equals("agree") ? nowDate : null);
//        member.setUserPw(userPw);
//        /**
//         * ===== 프로필 사진에 대한 데이터 처리 =====
//         * - else if : 이미지 파일이 업로드 되지 않은 경우
//         *     - if : 이미지 삭제가 체크된 경우
//         *         - 이 두개를 동시에 충족하는 경우
//         *             Mapper에 공백 문자열을 전달하여
//         *             이미지에 대한 항목을 변경하지 않는 경우와 구분해야 한다.
//         */
//        if(profileImg != null) {
//            // 이미지가 업로드 되었다면?
//            // -> 이미지 교체를 위해서 업로드 된 파일의 정보를 Beans에 등록
//            member.setProfileImg(profileImg);
//        } else if(profileImg == null) {
//            // 이미지가 업로드 되지 않았다면?
//            // -> 삭제만 체크했을 경우
//            if(imgDel != null && imgDel.equals("Y")) {
//                // SQL에서는 공백일 경우 null로 업데이트 하도록 분기하고 있다.
//                member.setProfileImg("");
//            }
//        }
//        
//        /** (8) Service를 통한 데이터베이스 저장 처리 */
//        /**
//         * ===== Service 기능을 호출하여 데이터 갱신 처리 =====
//         * 이메일 중복검사를 한다. 중복될 경우 즉시 중단.
//         * 비밀번호를 검사하여 본인인지 검사한다. 비밀번호가 잘못된 경우 catch 블록으로 이동한다.
//         * 
//         */
//        if(!email.equals(loginInfo.getEmail())) {
//            int emailCheckResult = 0;
//            try {
//                emailCheckResult = memberService.emailCheck(member);
//            } catch (Exception e) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, e.getLocalizedMessage());
//                // 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
//                return null;
//            }
//            
//            if(emailCheckResult > 0) {
//                upload.removeTempFile();
//                sqlSession.close();
//                web.redirect(null, "이미 사용중인 이메일 입니다.");
//                // 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
//                return null;
//            }
//        }
//        
//        // 본인이 맞는지 검사할 Beans
//        Member userPwMember = new Member();
//        userPwMember.setMemberId(loginInfo.getMemberId());
//        userPwMember.setUserPw(edit_door_userPw);
//        
//        // 변경된 정보를 저장하기 위한 객체
//        Member editInfo = null;
//        try {
//            memberService.selectMemberPasswordCount(userPwMember);
//            memberService.editMember(member);
//            editInfo = memberService.selectMember(member);
//        } catch (Exception e) {
//            upload.removeTempFile();
//            web.redirect(null, e.getLocalizedMessage());
//            return null;
//        } finally {
//            sqlSession.close();
//        }
//        
//        /**
//         * ===== 프로필 사진에 대한 파일 처리 =====
//         * - upload.removeFile :
//         *     파일 삭제 체크박스가 선택된 경우 실제 이미지 파일을 삭제한다.
//         */
//        /** (9) 프로필 사진의 삭제가 요청된 경우 */
//        if(regex.isValue(imgDel) && imgDel.equals("Y")) {
//            // 세션에 보관되어 있는 이미지 경로를 취득
//            upload.removeFile(loginInfo.getProfileImg());
//        }
//        
//        /** (10) 세션, 쿠키 갱신 */
//        // 일단 쿠키의 썸네일 정보를 삭제한다.
//        if(imgDel != null && imgDel.equals("Y")) {
//            web.removeCookie("profileThumbnail");
//        }
//        
//        // 프로필 이미지가 있을 경우 썸네일을 생성하여 쿠키에 별도로 저장
//        String newProfileImg = editInfo.getProfileImg();
//        if(newProfileImg != null) {
//            try {
//                String profileThumbnail = upload.createThumbnail(newProfileImg, 24, 24, true);
//                web.setCookie("profileThumbnail", profileThumbnail, -1);
//            } catch (Exception e) {
//                web.redirect(null, e.getLocalizedMessage());
//                return null;
//            }
//        }
//        
//        // 세션을 갱신한다.
//        web.removeSession("loginInfo");
//        web.setSession("loginInfo", editInfo);
//        
//        /** (11) 수정이 완료되었으므로 메인페이지로 이동 */
//        web.redirect(web.getRootPath() + "/index.do", null);
//        
//        /**
//         * Insert, Update, Delete 처리를 수행하는 action 페이지들은 자체적으로
//         * View를 갖지 않고 결과를 확인할 수 있는 다른 페이지로 강제 이동시켜야 한다.
//         * 그러므로 View의 경로를 리턴하지 않는다. (중복실행 방지)
//         */
//        return null;
//    }
//
//}
