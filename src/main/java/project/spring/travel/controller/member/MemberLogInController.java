package project.spring.travel.controller.member;

import java.util.List;
import java.util.Locale;

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

import project.spring.helper.RegexHelper;
import project.spring.helper.UploadHelper;
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
import project.spring.travel.service.BoardService;
import project.spring.travel.service.CategoryLikeService;
import project.spring.travel.service.FavoriteService;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.MemberService;
import project.spring.travel.service.ReplyLikeService;
import project.spring.travel.service.ReplyService;

/**
 * @fileName    : MemberLogInController.java
 * @author      : 홍준성
 * @description : 회원 로그인 관련 컨트롤러 모음
 * @lastUpdate  : 2019. 4. 18.
 */
/** 컨트롤러 선언 */
@Controller
public class MemberLogInController {
    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    Logger logger = LoggerFactory.getLogger(MemberLogInController.class); // Log4j 객체 직접 생성
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
    // -> import project.spring.travel.service.FavoriteService;
    @Autowired
    FavoriteService favoriteService;
    // -> import project.spring.travel.service.HotKeywordService;
    @Autowired
    HotKeywordService hotKeywordService;
    
    /**
     * 로그인 View를 사용하기 위한 컨트롤러 기본 구성
     */
    @RequestMapping(value = "/member/member_login.do", method = RequestMethod.GET)
    public ModelAndView memberLogin(Locale locale, Model model, 
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
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        logger.debug("loginInfo = " + loginInfo);
        if(loginInfo != null) {
            return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
        } else {
            // 세션값 가져와서 등록하기
            model.addAttribute("loginInfo", loginInfo);
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
        
        // 쿠키값 가져오기
        String remember_userId = web.getCookie("remember_userId");
        logger.debug("remember_userId = " + remember_userId);
        model.addAttribute("remember_userId", remember_userId);
        
        /** 로그인을 시도하기 전의 페이지를 기록한다. */
        String movePage = request.getHeader("referer");
        if(movePage.indexOf("member_join_success") != -1 || movePage.indexOf("member_userId_search_success.do") != -1 || movePage.indexOf("ember_userPw_update") != -1) {
            movePage = web.getRootPath() + "/index.do";
        }
        logger.debug("movePage = ", movePage);
        web.setCookie("movePage", movePage, -1);
        
        /** (4) 사용할 View의 이름 리턴 */
        return new ModelAndView("member/member_login");
    } // End memberLogin Method
    
    /**
     * 로그인 처리 과정을 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/member/member_login_ok.do", method = RequestMethod.POST)
    public ModelAndView memberLoginOk(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_login.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        if(web.getSession("loginInfo") != null) {
            return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
        }
        
        /** (4) 사용자 입력값 받기 */
        String userId = web.getString("userId");
        String userPw = web.getString("userPw");
        String remember_userId = web.getString("remember_userId");
        
        // 사용자 입력값 기록
        logger.debug("userId = " + userId);
        logger.debug("userPw = " + userPw);
        logger.debug("remember_userId = " + remember_userId);
        
        /** (5) 필수 값의 존재여부 검사 */
        // 아이디, 비밀번호 검사
        if(!regex.isValue(userId) || !regex.isValue(userPw)) {
            return web.redirect(null, "아이디나 비밀번호가 없습니다.");
        }
        
        /** (6) 전달받은 파라미터를 Beans에 설정한다. */
        Member member = new Member();
        member.setUserId(userId);
        member.setUserPw(userPw);
        
        /**
         * ===== Service 계층의 기능을 통한 사용자 인증 =====
         * - 파라미터가 저장된 Beans를 Service에게 전달한다.
         * - Service는 MyBatis의 Mapper를 호출하고 조회 결과를 리턴할 것이다.
         */
        /** (7) Service를 통한 회원 인증 */
        Member loginInfo = null;
        
        try {
            loginInfo = memberService.memberSearch(member);
            
            // 휴면계정일 경우 활성화 처리
            if(loginInfo != null) {
                memberService.activeMember(loginInfo);
            }
            
            // 관리자 계정이 아닐 경우에만 등급 조정
            if(!loginInfo.getGrade().equals("Master")) {
                /** ===== 회원등급을 적용하기 위한 처리 ===== */
                // 게시물과의 Count로 등급 적용을 위한 조건값 설정
                BoardList boardList = new BoardList();
                boardList.setMemberId(loginInfo.getMemberId());
                // 게시물 덧글과의 Count로 등급 적용을 위한 조건값 설정
                BoardReply boardReply = new BoardReply();
                boardReply.setMemberId(loginInfo.getMemberId());
                // 게시물 좋아요의 Count로 등급 적용을 위한 조건값 설정
                CategoryLike categoryLike = new CategoryLike();
                categoryLike.setMemberId(loginInfo.getMemberId());
                // 게시물 덧글의 좋아요 Count로 등급 적용을 위한 조건값 설정
                BoardReplyLike boardReplyLike = new BoardReplyLike();
                boardReplyLike.setMemberId(loginInfo.getMemberId());
                // 게시물 대댓글의 Count로 등급 적용을 위한 조건값 설정
                BoardReplyReply boardReplyReply = new BoardReplyReply();
                boardReplyReply.setMemberId(loginInfo.getMemberId());
                // 게시물 대댓글의 좋아요 Count로 등급 적용을 위한 조건값 설정
                BoardReplyReplyLike boardReplyReplyLike = new BoardReplyReplyLike();
                boardReplyReplyLike.setMemberId(loginInfo.getMemberId());
                // 찜리스트 Count로 등급 적용을 위한 조건값 설정
                Favorite favorite = new Favorite();
                favorite.setMemberId(loginInfo.getMemberId());
                
                // Count
                int b_count = boardService.countByMemberId(boardList) * 30;
                logger.debug("사용자 게시물 등록 점수 = " + b_count);
                int bl_count = categoryLikeService.countByMemberId(categoryLike) * 20;
                logger.debug("사용자 게시물 좋아요 등록 점수 = " + bl_count);
                int r_count = replyService.countByMemberId(boardReply) * 15;
                logger.debug("사용자 게시물 덧글 등록 점수 = " + r_count);
                int brl_count = replyLikeService.countByMemberId(boardReplyLike) * 10;
                logger.debug("사용자 게시물 덧글 좋아요 등록 점수 = " + brl_count);
                int brr_count = replyService.reCountByMemberId(boardReplyReply) * 8;
                logger.debug("사용자 게시물 대댓글 등록 점수 = " + brr_count);
                int brrl_count = replyLikeService.reCountByMemberId(boardReplyReplyLike) * 6;
                logger.debug("사용자 게시물 대댓글 좋아요 등록 점수 = " + brrl_count);
                int f_count = favoriteService.favoriteCount(favorite) * 5;
                logger.debug("찜리스트 등록 점수 = " + f_count);
                int clu_count = categoryLikeService.countByUser(categoryLike) * 4;
                logger.debug("타인에게서 게시물 좋아요 받은 점수 = " + clu_count);
                int fu_count = favoriteService.favoriteCount(favorite) * 3;
                logger.debug("타인에게서 찜리스트 등록 받은 점수 = " + fu_count);
                int brlu_count = replyLikeService.countByUser(boardReplyLike) * 2;
                logger.debug("타인에게서 게시물 덧글 좋아요 받은 점수 = " + brlu_count);
                int brrlu_count = replyLikeService.reCountByUser(boardReplyReplyLike) * 1;
                logger.debug("타인에게서 게시물 대댓글 좋아요 받은 점수 = " + brrlu_count);
                
                // 경험치 적용
                int grade_exp = b_count + bl_count + r_count + brl_count + brr_count + brrl_count + f_count + clu_count + fu_count + brlu_count + brrlu_count;
                logger.debug("grade_exp = " + grade_exp);
                web.setCookie("grade_exp", "" + grade_exp, -1);
                // 등급 계산
                String grade = "lv1"; // 토트백
                if(grade_exp >= 500 && grade_exp < 1000) {
                    grade = "lv2"; // 쇼퍼백
                } else if(grade_exp >= 1000 && grade_exp < 4000) {
                    grade = "lv3"; // 소형백팩
                } else if(grade_exp >= 4000 && grade_exp < 8000) {
                    grade = "lv4"; // 비즈니스백
                } else if(grade_exp >= 8000 && grade_exp < 20000) {
                    grade = "lv5"; // 대형배낭여행백
                } else if(grade_exp >= 20000) {
                    grade = "lv6"; // 캐리어
                }
                // 갱신할 등급을 Beans에 담는다.
                Member updateGrade = new Member();
                updateGrade.setGrade(grade);
                updateGrade.setMemberId(loginInfo.getMemberId());
                // 등급 적용
                memberService.updateMemberGrade(updateGrade);
                // 수정된 정보로 다시 조회
                loginInfo = memberService.memberSearch(member);
            } // End if
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        
        /**
         * ===== 조회된 회원정보 객체에 프로필 이미지의 경로가 있는 경우 =====
         * - 원본 프로필 이미지를 썸네일로 구성한다. 생성된 썸네일의 경로는
         *     사이트 내의 모든 경로에서 접근할 수 있도록 쿠키에 저장한다.
         * - 리턴된 회원 정보는 사이트 내의 모든 페이지에서 접근할 수 있도록
         *     세션에 저장한다.
         */
        /** (8) 프로필 이미지 처리 */
        // 프로필 이미지가 있을 경우 썸네일을 생성하여 쿠키에 별도로 저장
        String profileImg = loginInfo.getProfileImg();
        if(profileImg != null) {
            try {
                String profileThumbnail = upload.createThumbnail(profileImg, 24, 24, true);
                web.setCookie("profileThumbnail", profileThumbnail, -1);
            } catch (Exception e) {
                return web.redirect(null, e.getLocalizedMessage());
            }
        }
        
        /** (9) 조회된 회원 정보를 세션에 저장 */
        /**
         * 로그인 처리된 아이디와 비밀번호를 기반으로 조회된 정보를
         * 세션에 보관하는 과정을 말한다.
         * 로그인에 대한 판별은 저장된 세션정보의 존재 여부를 판별한다.
         */
        web.setSession("loginInfo", loginInfo);
        /**
         * ### 썸네일 정보를 세션에 보관하는 것도 가능하지만,
         *     모든 기능을 활용하는 것이 더 적합하다고 생각되어
         *     쿠키를 활용하였다. (서버에 부담이 가지 않도록 한다.)
         */
        
        /** 기억하기가 체크된 경우 아이디만 쿠키로 생성 */
        // -> 30일
        if(remember_userId != null) {
            web.setCookie("remember_userId", userId, 60 * 60 * 24 * 30);
        } else {
            web.removeCookie("remember_userId");
        }
        
        /**
         * ===== 로그인 완료 후 페이지 이동 =====
         * String movePage : 이전 페이지로의 URL을 조회한다.
         * if : 이전 페이지의 URL이 존재하지 않는 경우 사이트의 첫 페이지로 강제 설정한다.
         * sqlSession.close() : 페이지 이동 직전에 데이터베이스와의 연결을 해제한다.
         */
        /** (10) 페이지 이동 */
        // 이전 페이지 구하기 (Javascript로 이동된 경우 조회 안됨)
        String movePage = web.getCookie("movePage");
        if(movePage == null) {
            movePage = web.getRootPath() + "/index.do";
        }
        
        return web.redirect(movePage, null);
    } // End memberLoginOk Method
    
    /**
     * 로그아웃 처리를 위한 서블릿
     */
    @RequestMapping(value = "/member/member_logout.do")
    public ModelAndView memberLogout(Locale locale, Model model, 
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
        // 로그인중인 회원 정보 가져오기
        Member loginInfo = (Member) web.getSession("loginInfo");
        // 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
        if(loginInfo == null) {
            return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
        }
        
        /** (4) 로그아웃 */
        // 로그아웃은 모든 세션 정보를 삭제하는 처리
        web.removeAllSession();
        web.removeCookie("profileThumbnail");
        
        /** (5) 페이지 이동 */
        /** 로그인을 시도하기 전의 페이지를 기록한다. */
        String movePage = request.getHeader("referer");
        if(movePage == null) {
            movePage = web.getRootPath() + "/index.do";
        }
        
        return web.redirect(movePage, null);
    } // End memberLogout Method
}
