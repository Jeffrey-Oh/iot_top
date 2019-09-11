package project.spring.travel.controller.member;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
import org.springframework.web.servlet.ModelAndView;

import project.spring.helper.MailHelper;
import project.spring.helper.PageHelper;
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
 * @fileName    : MemberManagementController.java
 * @author      : 홍준성
 * @description : 회원 관리와 관련된 컨트롤러 모음
 * @lastUpdate  : 2019. 4. 18.
 */
/** 컨트롤러 선언 */
@Controller
public class MemberManagementController {
    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    Logger logger = LoggerFactory.getLogger(MemberManagementController.class); // Log4j 객체 직접 생성
    // -> import org.apache.ibatis.session.SqlSession;
    @Autowired
    SqlSession sqlSession;
    // -> import project.spring.helper.WebHelper
    @Autowired
    WebHelper web;
    // -> import project.spring.helper.MailHelper
    @Autowired
    MailHelper mail;
    // -> import project.spring.helper.RegexHelper;
    @Autowired
    RegexHelper regex;
    // -> import java.util.Calendar;
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
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
    // -> import project.spring.helper.PageHelper;
    @Autowired
    PageHelper pageHelper;
    // -> import project.spring.travel.service.HotKeywordService;
    @Autowired
    HotKeywordService hotKeywordService;
    
    /**
     * 회원 관리 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_management.do", method = RequestMethod.GET)
    public ModelAndView memberManagement(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 관리자 계정 로그인 여부 검사 */
        // 관리자 계정으로 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(loginInfo == null) {
            return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
        } else if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
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
        
        /**
         * ===== 검색어 파라미터 전달받기 =====
         * - 사용자가 입력한 검색어는 최종적으로
         *     SQL의 Where 조건에 사용되어야 한다.
         * - SQL을 수행하는 Mapper에게 검색어를 전달하기 위하여
         *     JavaBeans 객체에 사용자의 입력값을 저장한다.
         */
        /** (4) 검색어 파라미터 받기 + Beans 설정 */
        String keyword = web.getString("keyword", "");
        // 로그에 기록
        logger.debug("keyword = " + keyword);
        // View의 검색영역에 사용하기 위해 등록
        model.addAttribute("keyword", keyword);
        Member member = new Member();
        member.setUserName(keyword);
        
        /**
         * ===== 페이지 번호 파라미터 받기 =====
         * - 처음 페이지가 열릴 때 기본값 1로 설정된다.
         * - 페이지 번호를 클릭할 때 마다
         *     JSP페이지가 자기 스스로에게 page번호값을 전송하게 된다.
         */
        // 현재 페이지 번호에 대한 파라미터 받기
        int nowPage = web.getInt("page", 1);
        // 로그에 기록
        logger.debug("nowPage = " + nowPage);
        
        /** (5) Service를 통한 SQL 수행 */
        /**
         * ===== 전체 게시물 수 구하기 =====
         * - 검색어가 있는 경우 검색결과를 반영해야 하기 때문에,
         *     전체 데이터 수를 구하는 작업은
         *     검색어 파라미터 처리 이후에 수행되어야 한다.
         */
        // 전체 데이터 수 조회하기
        int totalCount = 0;
        
        try {
            totalCount = memberService.selectMemberCount(member);
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        // 페이지 번호에 대한 연산 수행 후, 조회조건값 지정을 위한 Beans에 추가하기
        pageHelper.pageProcess(nowPage, totalCount, 10, 5);
        member.setLimitStart(pageHelper.getLimitStart());
        member.setListCount(pageHelper.getListCount());
        // View의 페이지 구현에 사용하기 위해 등록
        model.addAttribute("pageHelper", pageHelper);
        
        // 조회 결과를 저장하기 위한 객체
        List<Member> list = null;
        
        try {
            /**
             * ===== 검색어를 저장하고 있는 Beans를 Service 객체에게 주입하기 =====
             * - GET 파라미터로 전달받은 검색어를 JavaBeans에 담은 상태로
             *     Service 객체에 전달한다.
             * - 이렇게 전달된 객체는 MyBatis의 Mapper에게 전달될 것이다.
             */
            list = memberService.selectMemberList(member);
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        // View에서 사용하기 위해 등록
        model.addAttribute("list", list);
        
        return new ModelAndView("member/member_management");
    } // End memberManagement Method
    
    /**
     * 회원관리에서 회원 상세 정보 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_management_view.do", method = RequestMethod.GET)
    public ModelAndView memberManagementView(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_management.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 관리자 계정 로그인 여부 검사 */
        // 관리자 계정으로 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(loginInfo == null) {
            return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
        } else if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /**
         * ===== 목록에서 선택한 항목을 식별하기 위한 파라미터 처리 =====
         * - 목록에서 상세 페이지로 회원의 일련번호(Primary Key)값을
         *     GET 파라미터로 전달하고, 상세페이지는 전달받은 값을 사용하여
         *     조회 대상을 식별한다.
         */
        /** (4) 이전 페이지에서 전달된 회원번호 받기 */
        int memberId = web.getInt("memberId");
        // 전달된 값 로그에 기록
        logger.debug("memberId = " + memberId);
        // View에서 사용하기 위해 등록
        model.addAttribute("memberId", memberId);
        
        /** (5) 유효성 검사 */
        if(memberId == 0) {
            return web.redirect(null, "회원번호가 없습니다.");
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
        
        /** (6) Beans 생성 */
        // MyBatis의 Where절에 사용할 값을 담은 객체
        Member member = new Member();
        member.setMemberId(memberId);
        
        /**
         * ===== Service 계층을 통한 데이터 조회 처리 =====
         * - 목록에서 전달받은 파라미터를 Beans로 묶어서 Service에 전달한다.
         * - Service는 MyBatis (DAO계층) 와의 연동을 통해서
         *     상세 정보를 조회하여 리턴하게 된다.
         */
        /** (7) Service를 통한 SQL 수행 */
        // 조회 결과를 저장하기 위한 객체
        Member item = null;
        try {
            item = memberService.selectMember(member);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        // 로그에 기록
        logger.debug("item = " + item);
        // View에서 사용하기 위해 등록
        model.addAttribute("item", item);
        
        return new ModelAndView("member/member_management_view");
    } // End memberManagementView Method
    
    /**
     * 휴면계정 회원 삭제를 처리하기 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/member/member_management_inactive_delete_ok.do", method = RequestMethod.POST)
    public String memberManagamentInactiveDeleteOk(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** 컨텐츠 타입 명시 */
        response.setContentType("application/json");
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_management.do"))) {
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
        
        /** (3) 관리자 계정 로그인 여부 검사 */
        // 관리자 계정으로 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(loginInfo == null) {
            web.printJsonRt("X-LOGIN");
            return null;
        } else if(!loginInfo.getGrade().equals("Master")) {
            web.printJsonRt("X-MASTER");
            return null;
        }
        
        /** (4) Service를 통한 SQL 수행 */
        // 조회 결과를 저장하기 위한 객체
        List<Member> list = null;
        try {
            list = memberService.selectMemberList(null);
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            web.printJsonRt(e.getLocalizedMessage());
            return null;
        }
        
        for(int i = 0; i < list.size(); i++) {
            Member member = list.get(i);
            if(member.getDeleteDate() != null) {
                // 회원정보에서 삭제예정일을 추출
                String deleteDate = member.getDeleteDate();
                // TimeStamp에서 사용할 년월일 값으로 추출
                int year = Integer.parseInt(deleteDate.substring(0, 4));
                int month = Integer.parseInt(deleteDate.substring(5, 7));
                int day = Integer.parseInt(deleteDate.substring(8, 10));
                cal2.set(year, month - 1, day);
                // 삭제 예정일과 현재 시각에 대한 TimeStamp를 밀리세컨드 단위로 얻기
                long delMs = cal2.getTimeInMillis();
                long nowMs = cal1.getTimeInMillis();
                long resultMs = delMs - nowMs;
                // 일 단위로 변환
                int date = (int) Math.floor(resultMs / (24 * 60 * 60 * 1000));
                logger.debug("date = " + date);
                // 삭제예정일까지 한달 이내라면?
                if(date <= 30) {
                    // 삭제 대상일 경우
                    if(date <= 0) {
                        logger.debug("삭제 완료 member.toString >> " + member.toString());
                        // 게시물과의 참조 관계 해제를 위한 조건값 설정
                        BoardList boardList = new BoardList();
                        boardList.setMemberId(member.getMemberId());
                        // 게시물 덧글과의 참조 관계 해제를 위한 조건값 설정
                        BoardReply boardReply = new BoardReply();
                        boardReply.setMemberId(member.getMemberId());
                        // 댓글에 등급을 유지시켜주기 위한 값
                        boardReply.setGrade(member.getGrade());
                        // 게시물 좋아요의 참조 관계 해제를 위한 조건값 설정
                        CategoryLike categoryLike = new CategoryLike();
                        categoryLike.setMemberId(member.getMemberId());
                        // 게시물 덧글의 좋아요 참조 관계 해제를 위한 조건값 설정
                        BoardReplyLike boardReplyLike = new BoardReplyLike();
                        boardReplyLike.setMemberId(member.getMemberId());
                        // 게시물 대댓글의 참조 관계 해를 위한 조건값 설정
                        BoardReplyReply boardReplyReply = new BoardReplyReply();
                        boardReplyReply.setMemberId(member.getMemberId());
                        // 대댓글에 등급을 유지시켜주기 위한 값
                        boardReplyReply.setGrade(member.getGrade());
                        // 게시물 대댓글의 좋아요 참조 관계 해제를 위한 조건값 설정
                        BoardReplyReplyLike boardReplyReplyLike = new BoardReplyReplyLike();
                        boardReplyReplyLike.setMemberId(member.getMemberId());
                        // 찜리스트 참조 관계 해제를 위한 조건값 설정
                        Favorite favorite = new Favorite();
                        favorite.setMemberId(member.getMemberId());
                        
                        /** 삭제예정에 해당하는 회원을 삭제처리 */
                        try {
                            /**
                             * ===== 회원 삭제의 경우 발생되는 문제점 인식 =====
                             * - 작성한 게시물이나 덧글이 존재하는 회원의 경우
                             *     삭제가 불가능한 문제점 발생
                             * - 참조키 제약조건 때문에 발생되는 문제
                             * - 회원 삭제에 따라 게시물이 모두 삭제된다면
                             *     사이트 운영상 컨텐츠 수집에 지장을 초래한다.
                             *     - 이 경우 회원을 삭제하더라도 게시물은 남겨놓는 것이
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
                            
                            // 회원 삭제 성공 시 진행할 값을 미리 저장
                            String removeFilePath = member.getProfileImg();
                            
                            // 회원 삭제
                            memberService.inactiveMemberDelete(null);
                            
                            /**
                             * - 회원 프로필 이미지의 경로를 UploadHelper.removeFile() 메서드에게 전달한다.
                             * - 전달된 경로를 분석하여 원본 이미지와 썸네일 이미지를 일괄 삭제한다.
                             */
                            // 탈퇴되었다면 프로필 이미지를 삭제한다.
                            upload.removeFile(removeFilePath);
                        } catch (Exception e) {
                            web.printJsonRt(e.getLocalizedMessage());
                            return null;
                        } // End try~catch
                    } else {
                        logger.debug("삭제 예정 member.toString >> " + member.toString());
                        /** 발급된 인증번호를 메일로 발송하기 */
                        String sender = "ghdwnstjd13@gmail.com";
                        String email = member.getEmail();
                        String subject = member.getUserName() + "님! 완벽한 여행 휴면계정에 대한 안내문이 도착했습니다.";
                        String content = 
                                "<span style='font-size: 20px;'>" + member.getUserName() + 
                                "님! 오랜기간 접속을 안하셨군요... 아쉽네요. 휴면계정에 대한 삭제 예정까지 남은 시간은 <strong>" + date + 
                                "일</strong> 입니다. 사이트에 방문하셔서 <strong>로그인 성공 시</strong> 휴면계정에서 벗어나 삭제가 이루어지지 않습니다.</span>";
                        
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
                    } // End if~else
                } // End if
            } // End if
        } // End for
        
        /** 처리 결과를 JSON으로 출력하기 */
        web.printJsonRt("OK");
        
        return null;
    } // End memberManagamentInactiveDeleteOk Method
    
}
