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
//import project.java.travel.model.Domestic;
//import project.java.travel.service.DomesticService;
//import project.java.travel.service.impl.DomesticServiceImpl;
//import project.jsp.helper.BaseController;
//import project.jsp.helper.PageHelper;
//import project.jsp.helper.WebHelper;
//
///**
// * Servlet implementation class AirSchedult
// */
//@WebServlet("/air/AirSchedule.do")
//public class AirSchedule extends BaseController {
//	private static final long serialVersionUID = -909764701444449914L;
//	
//	WebHelper web;
//	Logger logger;
//	SqlSession sqlSession;
//	DomesticService domesticService;
//
//	@Override
//	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		web = WebHelper.getInstance(request, response);
//
//		logger = LogManager.getFormatterLogger("airlive.jsp");
//		
//		sqlSession = MyBatisConnectionFactory.getSqlSession();
//
//		domesticService = new DomesticServiceImpl(logger, sqlSession);
//		
//		String arrivalCity = web.getString("arrivalCity", "");
//		logger.debug("arrivalCity = " + arrivalCity);
//		request.setAttribute("arrivalCity", arrivalCity);
//		
//		String airlineKorean = web.getString("airlineKorean", "");
//		logger.debug("airlineKorean = " + airlineKorean);
//		request.setAttribute("airlineKorean", airlineKorean);
//		
//		String domesticNum = web.getString("domesticNum", "").toUpperCase();
//		logger.debug("domesticNum = " + domesticNum);
//		request.setAttribute("domesticNum", domesticNum);
//		
//		int nowPage = web.getInt("list", 1);
//		logger.debug("list = " + nowPage);
//
//		Domestic params = new Domestic();
//		
//		List<Domestic> list = null;
//		
//		if (arrivalCity != null) {
//			params.setArrivalCity(arrivalCity);
//		}
//		if (airlineKorean != null) {
//			params.setAirlineKorean(airlineKorean);
//		}
//		if (domesticNum != null) {
//			params.setDomesticNum(domesticNum);
//		}
//		
//		int totalCount = 0;
//		
//		try {
//			totalCount = domesticService.getCount(params); // 게시물 전체 개수
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
//			list = domesticService.getItemAll(params);
//			request.setAttribute("list", list);
//		} catch (Exception e) {
//			web.redirect(null, e.getLocalizedMessage());
//			return null;
//		} finally {
//			sqlSession.close();
//		}
//		
//		return "air/AirSchedule";
//	}
//       
//
//}
