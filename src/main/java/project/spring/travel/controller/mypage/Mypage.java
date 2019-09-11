package project.spring.travel.controller.mypage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import project.spring.helper.PageHelper;
import project.spring.helper.WebHelper;
import project.spring.travel.model.BoardList;
import project.spring.travel.model.Favorite;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Member;
import project.spring.travel.service.BoardService;
import project.spring.travel.service.FavoriteService;
import project.spring.travel.service.HotKeywordService;

/**
 * @fileName    : MypageHome.java
 * @author      : 홍준성, 임형진
 * @description : 마이페이지 홈 View를 위한 컨트롤러
 * @lastUpdate  : 2019. 5. 10.
 */
@Controller
public class Mypage {
    
    
    /**
     * ===== 로그인 중이 아니라면 접근할 수 없는 페이지 =====
     * - 마이홈 페이지는 로그인 상태가 아니라면 접근할 수 없도록 처리해야 하기 때문에
     *     세션이 존재한지 않는다면 (null이라면) 접근할 수 없도록 차단한다.
     */
    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
    // -> import project.jsp.helper.WebHelper
    Logger logger = LoggerFactory.getLogger(Mypage.class);
	
    @Autowired
    SqlSession sqlSession;
    
    @Autowired
    FavoriteService favorite;
    
    @Autowired
    BoardService boardService;
    
    @Autowired
	WebHelper web;
    
    @Autowired
    PageHelper pageHelper;
    
    
     
    // -> import project.spring.travel.service.HotKeywordService;
    @Autowired
    HotKeywordService hotKeywordService;
    
    /**
     * 마이페이지 메인을 띄우는 메서드
     * @param locale
     * @param model
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
	@RequestMapping(value="/mypage/mypage_home.do", method=RequestMethod.GET)
    public ModelAndView MypageHome(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
    	/** (1) 객체 초기화 */
    	web.init();
    	
           
    	/** (2) 악의적 접근에 대한 방어 코드 */
        /** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
            
        }
        
        
        /** (3) 로그인 여부 검사 */
        // 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        logger.debug("loginInfo = " + loginInfo);
        if(loginInfo == null) {
            return web.redirect(web.getRootPath() + "/member/member_login.do", "로그인 후에 이용 가능합니다.");
            
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
        
       
        List<BoardList> myboard = null;
        BoardList board = new BoardList();
        board.setMemberId(loginInfo.getMemberId());
        board.setLimitStart(0);
        board.setListCount(5);
        try {
			myboard = boardService.getItemListByMemberId(board);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        logger.debug("myboardSize=" + myboard.size());
        
        model.addAttribute("getMyBoardList", myboard);
        
        /** (4) 해당 멤버 아이디로 검색된 찜리스트 가져오기 */
        List<Favorite> list = null;
        Favorite favor = new Favorite();
        favor.setMemberId(loginInfo.getMemberId());
        favor.setLimitStart(0);
        favor.setListCount(8);
       
        

        List<BoardList> myQna = null;
        BoardList boardQna = new BoardList();
        boardQna.setMemberId(loginInfo.getMemberId());
        boardQna.setLimitStart(0);
        boardQna.setListCount(5);

        try {
             
             list = favorite.selectFavoriteByMemberId(favor);
             model.addAttribute("getFavoriteAll", list);
             myboard = boardService.getItemListByMemberId(board);
             model.addAttribute("getMyBoardList", myboard);
             myQna = boardService.getBoardListCenter(boardQna);
             model.addAttribute("getMyQnaList", myQna);
        } catch (Exception e) {
             return web.redirect(null, e.getLocalizedMessage());
        }
        
        
        
        
        return new ModelAndView("mypage/MypageHome");
    }
	/**
	 * 즐겨찾기 목록 뷰페이지
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/mypage/mypage_favorite.do", method=RequestMethod.GET)
	public ModelAndView MypageFavorite(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		/** (1) 객체 초기화 */
    	web.init();
    	
           
    	/** (2) 악의적 접근에 대한 방어 코드 */
        /** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
            
        }
        
        
        /** (3) 로그인 여부 검사 */
        // 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        logger.debug("loginInfo = " + loginInfo);
        if(loginInfo == null) {
        	return web.redirect(web.getRootPath() + "/member/member_login.do", "로그인 후에 이용 가능합니다.");
            
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
        
        int type = web.getInt("type", 1);
        model.addAttribute("type", type);
        
        
        if (type == 1) {
	        List<Favorite> list = null;
	        Favorite favor = new Favorite();
	        favor.setMemberId(loginInfo.getMemberId());
	
	        int totalFavoriteCount = 0;
	        
	        try {
				totalFavoriteCount = favorite.favoriteCount(favor);
			} catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
	        int nowPage = web.getInt("page", 1);
	        pageHelper.pageProcess(nowPage, totalFavoriteCount, 10, 5);
	        favor.setLimitStart(pageHelper.getLimitStart());
	        favor.setListCount(pageHelper.getListCount());
	        
	        model.addAttribute("pageHelper", pageHelper);
	        
	        try {			            
		        list = favorite.selectFavoriteByMemberId(favor);
		        model.addAttribute("getFavoriteAll", list);
		        
			} catch (Exception e) {
				return web.redirect(web.getRootPath()+ "/index.do", e.getLocalizedMessage());
			}
        
        }
        
       
        if (type == 2) {
        	
        
        
	        List<BoardList> myboard = null;
	        BoardList board = new BoardList();
	        board.setMemberId(loginInfo.getMemberId());
	        
	        int boardCount = 0;
	        
	        try {
				boardCount = boardService.countByMemberId(board);
			} catch (Exception e) {			
				return web.redirect(web.getRootPath()+ "/index.do", e.getLocalizedMessage());
			}
	        
	        int nowPage = web.getInt("page", 1);
	        pageHelper.pageProcess(nowPage, boardCount, 10, 5);
	        board.setLimitStart(pageHelper.getLimitStart());
	        board.setListCount(pageHelper.getListCount());
	        
	        model.addAttribute("pageHelper", pageHelper);
	        try {
	            
	            myboard = boardService.getItemListByMemberId(board);
	            model.addAttribute("getMyBoardList", myboard);
	            
	            
	       } catch (Exception e) {
	            return web.redirect(null, e.getLocalizedMessage());
	       }
        
        
       }
        
        
        if (type == 3) {
        	
        
        
        
	        List<BoardList> myQna = null;
	        BoardList boardQna = new BoardList();
	        boardQna.setMemberId(loginInfo.getMemberId());
	        int myQnaCount = 0;
	        
	        try {
				myQnaCount = boardService.getCountCenterByMemberId(boardQna);
			} catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
	        
	        int nowPage = web.getInt("page", 1);
	        pageHelper.pageProcess(nowPage, myQnaCount, 10, 5);
	        boardQna.setLimitStart(pageHelper.getLimitStart());
	        boardQna.setListCount(pageHelper.getListCount());
	        model.addAttribute("pageHelper", pageHelper);
	        
	        try {
				myQna = boardService.getBoardItemCenter(boardQna);
				model.addAttribute("getMyQnaList", myQna);
			} catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
	        
	        
	        
	        
        
        }
        
        
        
        
         
        return new ModelAndView("mypage/MypageFavorite");
	}
	
	
	/**
	 * 즐겨찾기를 등록하는 메서드
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/mypage/mypage_favorite_ok.do", method=RequestMethod.POST)
	public ModelAndView favoriteAdd(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		/** (2) 사용하고자 하는 Helper + Service 객체 생성 */
        // -> import org.apache.logging.log4j.LogManager;
        
        // -> import study.jsp.myschool.dao.MyBatisConnectionFactory;
       
		web.init();
		
		
		/** 컨텐츠 타입 명시 */
		response.setContentType("application/json");
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		
		System.out.println(1);
		if (loginInfo == null) {
			
			
			web.printJsonRt("권한이 없습니다.");
			
			return null;
		}
		System.out.println(2);
		
		logger.debug("memberId >> " + loginInfo.getMemberId());
		
		String link = web.getString("link");
		System.out.println(link);
		
		
		Favorite favor = new Favorite();
		favor.setMemberId(loginInfo.getMemberId());
		favor.setLink(link);
		System.out.println(favor.getLink());
		
		System.out.println(3);
		if (link.indexOf("Comm") > -1) {
			favor.setRefType("Comm");
			favor.setBoardId(Integer.parseInt(link.substring(link.lastIndexOf("boardId=")+8, link.lastIndexOf("boardId=")+8+link.substring(link.lastIndexOf("boardId=")+8).indexOf("&"))));
			
		} else if (link.indexOf("servicearea") > -1) {
			favor.setRefType("servicearea");
			favor.setServicearea_groupId(
					Integer.parseInt(link.substring(link.lastIndexOf("servicearea_groupId=")+20)));
		} else if (link.indexOf("seq") > -1) {
			favor.setRefType("culture");
			favor.setCultureId(
					Integer.parseInt(link.substring(link.lastIndexOf("seq=")+4)));
		} else if (link.indexOf("contentId") > -1) {
			favor.setRefType("culture");
			favor.setCultureId(
					Integer.parseInt(link.substring(link.lastIndexOf("contentId=")+10)));
		} else {
			favor.setRefType("tour");
			
		}
		
		
		System.out.println(4);
		logger.debug("RefType >> " + favor.getRefType());
		
		try {
			System.out.println(5);
			favorite.addFavorite(favor);
			System.out.println(6);
		} catch (Exception e) {
			e.printStackTrace();
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}
		
		
		
		System.out.println(7);
		JSONObject rss = new JSONObject();
		
		rss.put("rt", "OK");
		
		try {
			System.out.println(8);
			response.getWriter().print(rss);
			System.out.println(9);
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}
		
		return null;
		
	}
	
	/**
	 * 찜리스트 삭제처리를 하는 메서드
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/mypage/mypage_favorite_delete_ok.do", method=RequestMethod.POST)
	public void favoriteDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		web.init();   
        
		
		
		
		/** 컨텐츠 타입 명시 */
		response.setContentType("application/json");
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		if (loginInfo == null) {
			
			web.printJsonRt("권한이 없습니다.");
			
		}
		
		logger.debug("memberId >> " + loginInfo.getMemberId());
		
		String link = web.getString("link");
		logger.debug("link >> " + link);
		

		
		Favorite favor = new Favorite();
		favor.setMemberId(loginInfo.getMemberId());
		favor.setLink(link);		
		
		try {
			favorite.deleteFavorite(favor);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			
		}
		
		
		
		
		JSONObject rss = new JSONObject();
		rss.put("rt", "OK");
		
		try {
			response.getWriter().print(rss);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	/**
	 * 마이페이지에서 일괄삭제를 눌렀을 때 찜리스트를 삭제하는 메서드
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/mypage/mypage_favorite_delete_ok_via_mypage.do", method=RequestMethod.POST)
	public ModelAndView FavoriteDeleteThroughMypage(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		web.init();	        
        
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		if (loginInfo == null) {
			
			return web.redirect(web.getRootPath() + "/index.do", "권한이 없습니다.");
			
		}
		
		logger.debug("memberId >> " + loginInfo.getMemberId());
		
		String[] list = web.getStringArray("checkItem");
		
		
		for (int i = 0; i < list.length; i++) {
			int item = Integer.parseInt(list[i]);
			Favorite favor = new Favorite();
			
			favor.setMemberId(loginInfo.getMemberId());
			favor.setFavoriteId(item);
			
			try {
				favorite.deleteFavoriteViaMypage(favor);
			} catch (Exception e) {
				return web.redirect(web.getRootPath() + "/index.do", "알 수 없는 에러가 발생했습니다.");
				
			}
		}
					
		return web.redirect(web.getRootPath() + "/mypage/mypage_favorite.do", "즐겨찾기가 삭제되었습니다.");
		
		
	}

}
