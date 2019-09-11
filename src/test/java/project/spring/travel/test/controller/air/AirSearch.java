package project.spring.travel.test.controller.air;
//package project.jsp.travel.controller.air;
//
//import java.io.IOException;
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
//import project.jsp.helper.BaseController;
//import project.jsp.helper.WebHelper;
//
///**
// * Servlet implementation class AirSearch
// */
//@WebServlet("/air/AirSearch.do")
//public class AirSearch extends BaseController {
//	private static final long serialVersionUID = -2556114087623790154L;
//	
//	WebHelper web;
//	Logger logger;
//	SqlSession sqlSession;
//
//	@Override
//	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		web = WebHelper.getInstance(request, response);
//
//		logger = LogManager.getFormatterLogger("AirSearch.jsp");
//		
//		sqlSession = MyBatisConnectionFactory.getSqlSession();
//		
//		String tap = web.getString("tap");
//		logger.debug("tap = " + tap);
//		request.setAttribute("tap", tap);
//
//		String sdate = web.getString("sdate");
//		logger.debug("sdate = " + sdate);
//		request.setAttribute("sdate", sdate);
//		
//		String edate = web.getString("edate");
//		logger.debug("edate = " + edate);
//		request.setAttribute("edate", edate);
//		
//		String boardingKor = web.getString("boardingKor");
//		logger.debug("boardingKor = " + boardingKor);
//		request.setAttribute("boardingKor", boardingKor);
//		
//		String arrivedKor = web.getString("arrivedKor");
//		logger.debug("arrivedKor = " + arrivedKor);
//		request.setAttribute("arrivedKor", arrivedKor);
//		
//		return "air/AirSearch";
//	}
//
//}
