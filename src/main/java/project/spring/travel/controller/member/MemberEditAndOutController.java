package project.spring.travel.controller.member;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import project.spring.helper.FileInfo;
import project.spring.helper.RegexHelper;
import project.spring.helper.UploadHelper;
import project.spring.helper.Util;
import project.spring.helper.WebHelper;
import project.spring.travel.model.BoardList;
import project.spring.travel.model.BoardReply;
import project.spring.travel.model.BoardReplyLike;
import project.spring.travel.model.BoardReplyReply;
import project.spring.travel.model.BoardReplyReplyLike;
import project.spring.travel.model.CategoryLike;
import project.spring.travel.model.Favorite;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Member;
import project.spring.travel.model.MemberOutReason;
import project.spring.travel.service.BoardService;
import project.spring.travel.service.CategoryLikeService;
import project.spring.travel.service.FavoriteService;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.MemberOutReasonService;
import project.spring.travel.service.MemberService;
import project.spring.travel.service.ReplyLikeService;
import project.spring.travel.service.ReplyService;

/**
 * @fileName    : MemberEditAndOutController.java
 * @author      : 홍준성
 * @description : 회원 수정과 탈퇴 관련된 컨트롤러 모음
 * @lastUpdate  : 2019. 5. 07.
 */
/** 컨트롤러 선언 */
@Controller
public class MemberEditAndOutController {
    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    Logger logger = LoggerFactory.getLogger(MemberEditAndOutController.class); // Log4j 객체 직접 생성
    // -> import org.apache.ibatis.session.SqlSession;
    @Autowired
    SqlSession sqlSession;
    // -> import project.spring.helper.WebHelper
    @Autowired
    WebHelper web;
    // -> import project.spring.helper.RegexHelper
    @Autowired
    RegexHelper regex;
    // -> import project.spring.helper.UploadHelper
    @Autowired
    UploadHelper upload;
    // -> import project.spring.helper.Util;
    @Autowired
    Util util;
    // -> import java.util.Calendar;
    Calendar cal = Calendar.getInstance();
    // -> import project.java.travel.service.BoardService;
    @Autowired
    BoardService boardService;
    // -> import project.java.travel.service.ReplyService;
    @Autowired
    ReplyService replyService;
    // -> import project.java.travel.service.CategoryLikeService;
    @Autowired
    CategoryLikeService categoryLikeService;
    // -> import project.java.travel.service.ReplyLikeService;
    @Autowired
    ReplyLikeService replyLikeService;
    // -> import project.java.travel.service.MemberService
    @Autowired
    MemberService memberService;
    // -> import project.java.travel.service.MemberOutReasonService;
    @Autowired
    MemberOutReasonService memberOutReasonService;
    // -> import project.spring.travel.service.FavoriteService;
    @Autowired
    FavoriteService favoriteService;
    // -> import project.spring.travel.service.HotKeywordService;
    @Autowired
    HotKeywordService hotKeywordService;

    /**
     * 회원정보 수정하기 전 비밀번호 입력을 위한 View 컨트롤러
     */
    @RequestMapping(value = "/mypage/member_edit_door.do", method = RequestMethod.GET)
    public ModelAndView memberEditDoor(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        /** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }

        /** (3) 로그인 여부 검사 */
        // 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
        if(web.getSession("loginInfo") == null) {
            return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
        }

        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for(int i = 0; i < hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if(hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */

        return new ModelAndView("mypage/MypageEditDoor");
    } // End memberEditDoor Method

    /**
     * 회원정보 수정을 위한 정보입력 View 컨트롤러
     */
    @RequestMapping(value = "/member/member_edit_form.do")
    public ModelAndView memberEditForm(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        /** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null || regex.isIndexCheck(referer, "member_edit_door.do")) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }

        /** (3) 로그인 여부 검사 */
        // 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(loginInfo == null) {
            return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
        }

        /** (3) 파라미터 받기 */
        String userPw = web.getString("userPw");
        // 로그에 기록
        logger.debug("userPw = " + userPw);

        if(!regex.isValue(userPw)) {
            return web.redirect(null, "비밀번호를 입력해 주세요.");
        }

        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for(int i = 0; i < hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if(hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */

        // 비밀번호 검사를 하기 위한 Beans로 묶기
        Member member = new Member();
        member.setMemberId(loginInfo.getMemberId());
        member.setUserPw(userPw);
        logger.debug("member.toString >> " + member.toString());

        try {
            // 입력한 비밀번호와 로그인 상태의 아이디가 일치하는 회원인지 조회
            memberService.selectMemberPasswordCount(member);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }

        // member_edit_ok에서 사용하기 위해 세션 등록
        web.setSession("edit_door_userPw", userPw);

        String birthDate = loginInfo.getBirthDate();
        logger.debug("birthDate = " + birthDate);
        // View에 보여질 년월일 형식으로 변경
        String year = birthDate.substring(0, 4);
        String month = birthDate.substring(5, 7);
        String day = birthDate.substring(8, 10);
        String tempBirthDate = String.format("%s년 %s월 %s일", year, month, day);
        // View에서 사용하기 위해 등록
        model.addAttribute("tempBirthDate", tempBirthDate);

        return new ModelAndView("member/member_edit_form");
    } // End memberEditForm Method

    /**
     * 회원정보 수정 처리를 위한 입력에 따른 액션 컨트롤러
     */
    @RequestMapping(value = "/member/member_edit_ok.do", method = RequestMethod.POST)
    public ModelAndView memberEditOk(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_edit_form.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            sqlSession.close();
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            web.redirect(web.getRootPath() + "/error/error_page.do", null);
            return null;
        }

        /** (3) 로그인 여부 검사 */
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(loginInfo == null) {
            return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
        }

        /** member_edit_door에서 전달한 세션 받기 */
        String edit_door_userPw = (String) web.getSession("edit_door_userPw");
        // 로그에 기록
        logger.debug("edit_door_userPw = " + edit_door_userPw);
        // 사용한 세션 삭제
        web.removeSession("edit_door_userPw");

        /** 본인 검사 */
        if(!regex.isValue(edit_door_userPw)) {
            return web.redirect(null, "비밀번호가 잘못되었습니다.");
        }

        /** (4) 파일이 포함된 POST 파라미터 받기 */
        /**
         * ===== 사용자의 입력값 받기 =====
         * - 회원가입 페이지에 포함된 프로필 이미지 업로드로 인해
         *     <form>태그에 ecntype="multipart/form-data"가 적용되어 있다.
         * - 이 경우 WebHelper를 통한 파라미터 수신이 동작하지 않기 때문에,
         *     UploadHelper를 통해 받아야 한다.
         *     - WebHelper의 getString()|getInt() 메서드는 더 이상 사용할 수 없다.
         */
        try {
            upload.multipartRequest();
        } catch (Exception e) {
            return web.redirect(null, "multipart 데이터가 아닙니다.");
        }

        // UploadHelper에서 텍스트 형식의 파라미터를 분류한 Map을 리턴받아서 값을 추출한다.
        Map<String, String> paramMap = upload.getParamMap();
        String email_check_ok = paramMap.get("email_check_ok");
        String cert_ok = paramMap.get("cert_ok");
        String userPw = paramMap.get("userPw");
        String userPwCheck = paramMap.get("userPwCheck");
        String phone = paramMap.get("phone");
        String email = paramMap.get("email");
        String certNum = paramMap.get("certNum");
        String postcode = paramMap.get("postcode");
        String addr1 = paramMap.get("addr1");
        String addr2 = paramMap.get("addr2");
        String emailCheck = paramMap.get("emailCheck");
        String smsCheck = paramMap.get("smsCheck");
        // 추가 - 이미지 삭제 여부에 대한 체크박스
        String imgDel = paramMap.get("img_del");

        /** 전달받은 데이터에 대한 로그 확인 */
        // member_edit_form에서 보낸 파라미터를 기록한다.
        logger.debug("email_check_ok = " + email_check_ok);
        logger.debug("cert_ok = " + cert_ok);
        logger.debug("userPw = " + userPw);
        logger.debug("userPwCheck = " + userPwCheck);
        logger.debug("phone = " + phone);
        logger.debug("email = " + email);
        logger.debug("certNum = " + certNum);
        logger.debug("postcode = " + postcode);
        logger.debug("addr1 = " + addr1);
        logger.debug("addr2 = " + addr2);
        logger.debug("emailCheck = " + emailCheck);
        logger.debug("smsCheck = " + smsCheck);
        logger.debug("img_del = " + imgDel);

        /** (5) 입력값의 유효성 검사 (아이디 검사 수행안함) */
        if(!email.equals(loginInfo.getEmail())) {
            // 이메일 중복 검사
            if(!email_check_ok.equals("true")) {
                upload.removeTempFile();
                return web.redirect(null, "사용할수 없는 이메일 입니다.");
            }

            // 이메일 인증 검사
            if(!cert_ok.equals("true")) {
                upload.removeTempFile();
                return web.redirect(null, "이메일 인증에 성공하지 못하였습니다.");
            }

            // 이메일 인증번호 검사
            if(!regex.isValue(certNum)) {
                upload.removeTempFile();
                return web.redirect(null, "이메일 인증번호를 입력해 주세요.");
            }
        }

        /**
         * ===== 비밀번호 변경에 대한 유효성 검사 =====
         * - 신규 비밀번호가 입력된 경우는 변경으로 간주하고,
         *     입력하지 않은 경우는 변경하지 않도록 처리한다.
         *     그러므로 입력된 경우만 검사해야 한다.
         */
        if(regex.isValue(userPw) || regex.isValue(userPwCheck)) {
            // 비밀번호 값 검사
            if(!regex.isMinLength(userPw, 6)) {
                upload.removeTempFile();
                return web.redirect(null, "비밀번호는 최소 6자 이상 입력하셔야 합니다.");
            }
            if(!regex.isMaxLength(userPw, 20)) {
                upload.removeTempFile();
                return web.redirect(null, "비밀번호는 최대 20자 까지만 입력 가능합니다.");
            }
            if(!regex.isPassword(userPw)) {
                upload.removeTempFile();
                return web.redirect(null, "비밀번호는 적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함되어야 합니다.");
            }
            if(!regex.isIndexCheck(userPw, loginInfo.getUserId())) {
                upload.removeTempFile();
                return web.redirect(null, "비밀번호에는 아이디가 포함되면 안됩니다.");
            }
            // 비밀번호 확인 검사
            if(!regex.isValue(userPwCheck)) {
                upload.removeTempFile();
                return web.redirect(null, "비밀번호 확인을 입력해 주세요.");
            }
            if(!regex.isCompareTo(userPw, userPwCheck)) {
                upload.removeTempFile();
                return web.redirect(null, "비밀번호 확인이 잘못되었습니다.");
            }
        }

        // 연락처 검사
        if(!regex.isValue(phone)) {
            upload.removeTempFile();
            return web.redirect(null, "연락처를 입력해 주세요.");
        }
        if(!regex.isPhone(phone)) {
            upload.removeTempFile();
            return web.redirect(null, "연락처의 형식이 잘못되었습니다.");
        }

        // 이메일 검사
        if(!regex.isValue(email)) {
            upload.removeTempFile();
            return web.redirect(null, "이메일을 입력해 주세요.");
        }
        if(!regex.isEmail(email)) {
            upload.removeTempFile();
            return web.redirect(null, "이메일 주소의 형식이 잘못되었습니다.");
        }

        // 우편번호, 주소, 상세주소 검사
        if(!regex.isValue(postcode)) {
            upload.removeTempFile();
            return web.redirect(null, "우편번호를 입력해 주세요.");
        }
        if(!regex.isValue(addr1)) {
            upload.removeTempFile();
            return web.redirect(null, "주소를 입력해 주세요.");
        }
        if(!regex.isValue(addr2)) {
            upload.removeTempFile();
            return web.redirect(null, "상세주소를 입력해 주세요.");
        }

        // 이메일 수신여부 검사
        if(!regex.isValue(emailCheck)) {
            upload.removeTempFile();
            return web.redirect(null, "이메일 수신여부를 선택해 주세요.");
        }
        // SMS 수신여부 검사
        if(!regex.isValue(smsCheck)) {
            upload.removeTempFile();
            return web.redirect(null, "SMS 수신여부를 선택해 주세요.");
        }

        // 이미지 삭제여부 검사
        if(regex.isValue(imgDel)) {
            if(!imgDel.endsWith("Y")) {
                upload.removeTempFile();
                return web.redirect(null, "이미지 삭제 선택이 잘못되었습니다.");
            }
        }

        /** (6) 업로드 된 파일 정보 추출 */
        // -> 이미지 수정을 원하지 않는 경우, 삭제만 원하는 경우
        //     데이터 없음
        List<FileInfo> fileList = upload.getFileList();
        // 업로드 된 프로필 사진 경로가 저장될 변수
        String profileImg = null;

        // 업로드 된 파일이 존재할 경우만 변수값을 할당한다.
        if(fileList.size() > 0) {
            // 단일 업로드이므로 0번째 항목만 가져온다.
            FileInfo info = fileList.get(0);
            profileImg = info.getFileDir() + "/" + info.getFileName();

            // 파일 확장자 체크
            String ext = info.getOrginName().substring(info.getOrginName().indexOf(".") + 1).toLowerCase();
            if (!ext.equals("gif") && !ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("png")) {
                upload.removeTempFile();
                return web.redirect(null , "gif, jpg, jpeg, png 파일만 업로드 해주세요.");
            }

            // 파일 용량 체크
            int fileSize = (int) info.getFileSize();
            float fileSizeF = fileSize;
            String fSMB = String.format("%.2f", (fileSizeF / (1024 * 1024)));
            int maxSize = 1024 * 1024 * 5;
            int mSMB = (maxSize / (1024 * 1024));
            if (info.getFileSize() > maxSize) {
                upload.removeTempFile();
                return web.redirect(null , info.getOrginName() + "(이)가 용량 5MB을 초과했습니다.\n\n" + fSMB + "MB / " + mSMB + "MB");
            }
        }

        // 파일경로를 로그로 기록
        logger.debug("profileImg = " + profileImg);

        /** (7) 전달받은 파라미터를 Beans 객체에 담는다. */
        // 이름, 성별, 생년월일, 아이디는 변경할 수 없으므로 제외한다.
        Member member = new Member();
        // Where절에 사용할 회원번호는 세션에서 취득
        member.setMemberId(loginInfo.getMemberId());
        member.setPhone(phone);
        member.setEmail(email);
        member.setPostcode(postcode);
        member.setAddress1(addr1);
        member.setAddress2(addr2);
        // 오늘 날짜를 형식에 맞게 조합을 한다.
        String nowDate = util.getPrintDate2(cal);
        member.setToEmailCheckedDate(emailCheck.equals("agree") ? nowDate : null);
        member.setToSmsCheckedDate(smsCheck.equals("agree") ? nowDate : null);
        member.setNewUserPw(userPw);
        /**
         * ===== 프로필 사진에 대한 데이터 처리 =====
         * - else if : 이미지 파일이 업로드 되지 않은 경우
         *     - if : 이미지 삭제가 체크된 경우
         *         - 이 두개를 동시에 충족하는 경우
         *             Mapper에 공백 문자열을 전달하여
         *             이미지에 대한 항목을 변경하지 않는 경우와 구분해야 한다.
         */
        if(profileImg != null) {
            // 이미지가 업로드 되었다면?
            // -> 이미지 교체를 위해서 업로드 된 파일의 정보를 Beans에 등록
            member.setProfileImg(profileImg);
        } else if(profileImg == null) {
            // 이미지가 업로드 되지 않았다면?
            // -> 삭제만 체크했을 경우
            if(imgDel != null && imgDel.equals("Y")) {
                // SQL에서는 공백일 경우 null로 업데이트 하도록 분기하고 있다.
                member.setProfileImg("");
            }
        }

        /** (8) Service를 통한 데이터베이스 저장 처리 */
        /**
         * ===== Service 기능을 호출하여 데이터 갱신 처리 =====
         * 이메일 중복검사를 한다. 중복될 경우 즉시 중단.
         * 비밀번호를 검사하여 본인인지 검사한다. 비밀번호가 잘못된 경우 catch 블록으로 이동한다.
         *
         */
        if(!email.equals(loginInfo.getEmail())) {
            int emailCheckResult = 0;
            try {
                emailCheckResult = memberService.emailCheck(member);
            } catch (Exception e) {
                upload.removeTempFile();
                // 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
                return web.redirect(null, e.getLocalizedMessage());
            }

            if(emailCheckResult > 0) {
                upload.removeTempFile();
                // 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
                return web.redirect(null, "이미 사용중인 이메일 입니다.");
            }
        }

        // 본인이 맞는지 검사할 Beans
        Member userPwMember = new Member();
        userPwMember.setMemberId(loginInfo.getMemberId());
        userPwMember.setUserPw(edit_door_userPw);

        // 변경된 정보를 저장하기 위한 객체
        Member editInfo = null;
        try {
            memberService.selectMemberPasswordCount(userPwMember);
            memberService.editMember(member);
            editInfo = memberService.selectMember(member);
        } catch (Exception e) {
            upload.removeTempFile();
            return web.redirect(null, e.getLocalizedMessage());
        }

        /**
         * ===== 프로필 사진에 대한 파일 처리 =====
         * - upload.removeFile :
         *     파일 삭제 체크박스가 선택된 경우 실제 이미지 파일을 삭제한다.
         */
        /** (9) 프로필 사진의 삭제가 요청된 경우 */
        if(regex.isValue(imgDel) && imgDel.equals("Y")) {
            // 세션에 보관되어 있는 이미지 경로를 취득
            upload.removeFile(loginInfo.getProfileImg());
        }

        /** (10) 세션, 쿠키 갱신 */
        // 일단 쿠키의 썸네일 정보를 삭제한다.
        if(imgDel != null && imgDel.equals("Y")) {
            web.removeCookie("profileThumbnail");
        }

        // 프로필 이미지가 있을 경우 썸네일을 생성하여 쿠키에 별도로 저장
        String newProfileImg = editInfo.getProfileImg();
        if(newProfileImg != null) {
            try {
                String profileThumbnail = upload.createThumbnail(newProfileImg, 24, 24, true);
                web.setCookie("profileThumbnail", profileThumbnail, -1);
            } catch (Exception e) {
                return web.redirect(null, e.getLocalizedMessage());
            }
        }

        // 세션을 갱신한다.
        web.removeSession("loginInfo");
        web.setSession("loginInfo", editInfo);

        /** (11) 수정이 완료되었으므로 메인페이지로 이동 */
        /**
         * Insert, Update, Delete 처리를 수행하는 action 페이지들은 자체적으로
         * View를 갖지 않고 결과를 확인할 수 있는 다른 페이지로 강제 이동시켜야 한다.
         * 그러므로 View의 경로를 리턴하지 않는다. (중복실행 방지)
         */
        return web.redirect(web.getRootPath() + "/index.do", null);
    } // End memberEditOk Method

    /**
     * 회원탈퇴 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/mypage/mypage_delete_member_reason.do", method = RequestMethod.GET)
    public ModelAndView mypageDeleteMemberReason(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_edit_form.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }

        /** (3) 로그인 여부 검사 */
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(loginInfo == null) {
            return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
        }

        /** member_edit_door에서 전달한 세션 받기 */
        String edit_door_userPw = (String) web.getSession("edit_door_userPw");
        // 로그에 기록
        logger.debug("edit_door_userPw = " + edit_door_userPw);
        // 사용한 세션 삭제
        web.removeSession("edit_door_userPw");

        /** 본인 검사 */
        if(!regex.isValue(edit_door_userPw)) {
            return web.redirect(null, "비밀번호가 잘못되었습니다.");
        }

        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for(int i = 0; i < hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if(hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */

        // 본인이 맞는지 검사할 Beans
        Member userPwMember = new Member();
        userPwMember.setMemberId(loginInfo.getMemberId());
        userPwMember.setUserPw(edit_door_userPw);

        try {
            memberService.selectMemberPasswordCount(userPwMember);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }

        // MemberOutOk에서 사용할 세션 등록
        web.setSession("edit_door_userPw", edit_door_userPw);

        return new ModelAndView("mypage/MypageDeleteMemberReason");
    } // End mypageDeleteMemberReason Method

    /**
     * 회원 탈퇴를 처리하기 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/member/member_out_ok.do", method = RequestMethod.POST)
    public ModelAndView memberOutOk(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "mypage_delete_member_reason.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }

        /** (3) 로그인 여부 검사 */
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(loginInfo == null) {
            return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
        }

        /** (4) 파라미터 받기 */
        /** member_edit_door에서 전달한 세션 받기 */
        String edit_door_userPw = (String) web.getSession("edit_door_userPw");
        // 로그에 기록
        logger.debug("edit_door_userPw = " + edit_door_userPw);
        // 사용한 세션 삭제
        web.removeSession("edit_door_userPw");

        // mypage_delete_member_reason에서 전달된 파라미터 받기
        String[] reason = web.getStringArray("reason");
        String reasonEtc = web.getString("reasonEtc");
        // 전달받은 값 로그에 기록
        logger.debug("reason = " + reason);
        logger.debug("reasonEtc = " + reasonEtc);

        /** 유효성 검사 */
        // 비밀번호 검사
        if(!regex.isValue(edit_door_userPw)) {
            return web.redirect(null, "비밀번호가 잘못되었습니다.");
        }
        // 체크박스 검사
        if(!regex.isCheck(reason)) {
            return web.redirect(null, "탈퇴 사유를 선택하셔야 합니다.");
        }

        // 배열을 Beans에 담을 문자열로 변환
        String reasonStr = "";
        for(int i = 0; i < reason.length; i++) {
            // 기타 사유일 경우
            if(reason[i].equals("기타")) {
                if(!regex.isValue(reasonEtc)) {
                    return web.redirect(null, "기타를 선택하신 경우 내용을 적어주셔야 합니다.");
                }
            }

            reasonStr += reason[i];
            if(i + 1 < reason.length) {
                reasonStr += ",";
            }
        }

        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for(int i = 0; i < hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if(hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */

        /** (5) 서비스에 전달하기 위하여 파라미터를 Beans로 묶는다. */
        // 게시물과의 참조 관계 해제를 위한 조건값 설정
        BoardList boardList = new BoardList();
        boardList.setMemberId(loginInfo.getMemberId());
        // 게시물 덧글과의 참조 관계 해제를 위한 조건값 설정
        BoardReply boardReply = new BoardReply();
        boardReply.setMemberId(loginInfo.getMemberId());
        // 댓글에 등급을 유지시켜주기 위한 값
        boardReply.setGrade(loginInfo.getGrade());
        // 게시물 좋아요의 참조 관계 해제를 위한 조건값 설정
        CategoryLike categoryLike = new CategoryLike();
        categoryLike.setMemberId(loginInfo.getMemberId());
        // 게시물 덧글의 좋아요 참조 관계 해제를 위한 조건값 설정
        BoardReplyLike boardReplyLike = new BoardReplyLike();
        boardReplyLike.setMemberId(loginInfo.getMemberId());
        // 게시물 대댓글의 참조 관계 해를 위한 조건값 설정
        BoardReplyReply boardReplyReply = new BoardReplyReply();
        boardReplyReply.setMemberId(loginInfo.getMemberId());
        // 대댓글에 등급을 유지시켜주기 위한 값
        boardReplyReply.setGrade(loginInfo.getGrade());
        // 게시물 대댓글의 좋아요 참조 관계 해제를 위한 조건값 설정
        BoardReplyReplyLike boardReplyReplyLike = new BoardReplyReplyLike();
        boardReplyReplyLike.setMemberId(loginInfo.getMemberId());
        // 찜리스트 참조 관계 해제를 위한 조건값 설정
        Favorite favorite = new Favorite();
        favorite.setMemberId(loginInfo.getMemberId());

        // 회원정보를 담은 Beans
        Member member = new Member();
        member.setMemberId(loginInfo.getMemberId()); // 회원번호는 세션을 통해서 획득한 로그인 정보에서 취득
        member.setUserPw(edit_door_userPw); // 입력한 비밀번호

        // 탈퇴사유를 담은 Beans
        MemberOutReason memberOutReason = new MemberOutReason();
        memberOutReason.setReason(reasonStr);
        memberOutReason.setReasonEtc(reasonEtc);

        /** (6) Service를 통한 탈퇴 시도 */
        try {
            /**
             * ===== 회원 탈퇴의 경우 발생되는 문제점 인식 =====
             * - 작성한 게시물이나 덧글이 존재하는 회원의 경우
             *     탈퇴가 불가능한 문제점 발생
             * - 참조키 제약조건 때문에 발생되는 문제
             * - 회원 탈퇴에 따라 게시물이 모두 삭제된다면
             *     사이트 운영상 컨텐츠 수집에 지장을 초래한다.
             *     - 이 경우 회원이 탈퇴하더라도 게시물은 남겨놓는 것이
             *         일반적인 처리 방법인데,
             *         이를 위해 회원 일련번호를 참조하는 컬럼을 Null로 변경해야 한다.
             *         - 참조키 컬럼에서 제약조건에 위배되지 않는 유일한 경우는
             *             Null값인 상태 뿐이다.
             *         - 게시물 테이블과 덧글 테이블은 이러한 상황을 대비하여
             *             회원 일련번호 필드에 Null을 허용해 두었다.
             */
            // 댓글과 대댓글에 등급값을 저장
            replyService.updateReplyLastGrade(boardReply);
            replyService.updateReplyReplyLastGrade(boardReplyReply);

            // 참조관계 해제
            boardService.updateDocumentMemberOut(boardList);
            replyService.updateCommentMemberOut(boardReply);
            categoryLikeService.updateCategoryLikeMemberOut(categoryLike);
            replyLikeService.updateBoardReplyLikeMemberOut(boardReplyLike);
            replyService.updateReCommentMemberOut(boardReplyReply);
            replyLikeService.updateBoardReplyReplyLikeMemberOut(boardReplyReplyLike);
            favoriteService.updateFavoriteByMemberOut(favorite);

            // 비밀번호 검사 -> 비밀번호가 잘못된 경우 예외발생
            memberService.selectMemberPasswordCount(member);
            // 탈퇴처리
            memberService.deleteMember(member);
            // 탈퇴 사유를 저장
            memberOutReasonService.insertMemberOutReason(memberOutReason);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /**
         * - 회원 프로필 이미지의 경로를 UploadHelper.removeFile() 메서드에게 전달한다.
         * - 전달된 경로를 분석하여 원본 이미지와 썸네일 이미지를 일괄 삭제한다.
         */
        // 탈퇴되었다면 프로필 이미지를 삭제한다.
        upload.removeFile(loginInfo.getProfileImg());

        /** (7) 정상적으로 탈퇴된 경우 강제 로그아웃 및 페이지 이동 */
        web.removeAllSession();

        return web.redirect(web.getRootPath() + "/index.do", "탈퇴되었습니다.");
    } // End memberOutOk Method

}
