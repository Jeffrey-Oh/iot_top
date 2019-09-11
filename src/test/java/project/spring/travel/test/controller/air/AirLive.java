package project.spring.travel.test.controller.air;
//package project.jsp.travel.controller.air;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.ibatis.session.SqlSession;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import project.java.travel.dao.MyBatisConnectionFactory;
//import project.java.travel.model.Live;
//import project.java.travel.service.LiveService;
//import project.java.travel.service.impl.LiveServiceImpl;
//import project.jsp.helper.BaseController;
//import project.jsp.helper.PageHelper;
//import project.jsp.helper.WebHelper;
//
///**
// * Servlet implementation class AirLive
// */
//@WebServlet("/air/AirLive.do")
//public class AirLive extends BaseController {
//	private static final long serialVersionUID = -1121644210323279696L;
//	
//	WebHelper web;
//	Logger logger;
//	SqlSession sqlSession;
//	LiveService liveService;
//
//	@Override
//	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		web = WebHelper.getInstance(request, response);
//
//		logger = LogManager.getFormatterLogger("AirLive.jsp");
//		
//		sqlSession = MyBatisConnectionFactory.getSqlSession();
//		
//		liveService = new LiveServiceImpl(logger, sqlSession);
//		
//		String boardingKor = web.getString("boardingKor");
//		logger.debug("boardingKor = " + boardingKor);
//		request.setAttribute("boardingKor", boardingKor);
//
//		String arrivedKor = web.getString("arrivedKor");
//		logger.debug("arrivedKor = " + arrivedKor);
//		request.setAttribute("arrivedKor", arrivedKor);
//
//		String airlineKorean = web.getString("airlineKorean", "");
//		logger.debug("airlineKorean = " + airlineKorean);
//		request.setAttribute("airlineKorean", airlineKorean);
//
//		String airFln = web.getString("airFln", "");
//		logger.debug("airFln = " + airFln);
//		request.setAttribute("airFln", airFln);
//		
//		int nowPage = web.getInt("list", 1);
//		logger.debug("list = " + nowPage);
//
//		Live params = new Live();
//
//		List<Live> list = null;
//		
//		if (boardingKor != null && boardingKor != "" && arrivedKor != null && arrivedKor != "") {
//			params.setBoardingKor(boardingKor);
//			params.setArrivedKor(arrivedKor);
//		}
//		if (airlineKorean != null && airlineKorean != "") {
//			params.setAirlineKorean(airlineKorean);
//		}
//		if (airFln != null && airFln != "") {
//			params.setAirFln(airFln);
//		}
//
//		int totalCount = 0;
//		
//		try {
//			totalCount = liveService.getCount(params); // 게시물 전체 개수
//		} catch (Exception e) {
//			web.redirect(null, e.getLocalizedMessage());
//			return null;
//		}
//		
//		PageHelper pageHelper = PageHelper.getInstance(nowPage, totalCount, 10, 5);
//		params.setLimitStart(pageHelper.getLimitStart());
//		params.setListCount(pageHelper.getListCount());
//		request.setAttribute("pageHelper", pageHelper);
//		
//		try {
//			list = liveService.getItemAll(params);
//			request.setAttribute("list", list);
//		} catch (Exception e) {
//			web.redirect(null, e.getLocalizedMessage());
//			return null;
//		} finally {
//			sqlSession.close();
//		}
//		
//		return "air/AirLive";
//	}
//
//}
