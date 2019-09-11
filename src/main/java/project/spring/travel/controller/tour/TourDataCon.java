// /**
//  * @Author: choseonjun
//  * @Date:   2019-04-05T20:23:04+09:00
//  * @Email:  seonjun92@naver.com
//  * @ProjectName:
//  * @Filename: TourDataCon.java
//  * @Last modified by:   choseonjun
//  * @Last modified time: 2019-04-08T14:48:51+09:00
//  */
//
// package project.spring.travel.controller.tour;
//
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.PrintWriter;
// import java.net.URL;
// import java.net.URLEncoder;
//
// import javax.servlet.ServletException;
//
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.json.JSONArray;
// import org.json.JSONObject;
//
// /**
//  * Servlet implementation class TourDataCon
//  */
// @WebServlet("/Seonjun/Test.do")
// public class TourDataCon extends HttpServlet {
// 	private static final long serialVersionUID = 1L;
//
// 	/**
// 	 * @see HttpServlet#HttpServlet()
// 	 */
// 	public TourDataCon() {
// 		super();
// 		// TODO Auto-generated constructor stub
// 	}
// 
// 	/**
// 	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
// 	 *      response)
// 	 */
// 	protected void doGet(HttpServletRequest request, HttpServletResponse response)
// 			throws ServletException, IOException {
// 		// TODO Auto-generated method stub
// 		request.setCharacterEncoding("utf-8");
// 		response.setContentType("text/html; charset=utf-8");
//
// 		String addr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword?ServiceKey=";
// 		String servicekey = "jlqwmvLJg1mfY2FrnI2dWSq624frmaOFZYLixlo7hXAGug%2B0dvGO%2B517BZKAJJ3Deq8nVKkVjOLCwVmOmg8Cqg%3D%3D";
// 		String parameter = "";
//
// 		/* servicekey = URLEncoder.encode(servicekey, "UTF-8"); */
//
// 		PrintWriter out = response.getWriter();
// 		parameter += "&keyword=" + URLEncoder.encode("강원", "UTF-8");
// 		parameter += "&" + "MobileOS=ETC";
// 		parameter += "&" + "MobileApp=AppTest";
// 		/* parameter = parameter + "&" + "numOfRows=10"; */
// //		parameter = parameter + "&" + "listYN=Y";
// //		parameter = parameter + "&" + "arrange=A";
// 		parameter += "&" + "_type=json";
//
// 		System.out.println(parameter);
//
// 		addr = addr + servicekey + parameter;
// 		URL url = new URL(addr);
// 		System.out.println(url);
//
// 		InputStream in = url.openStream();
// 		CachedOutputStream bos = new CachedOutputStream();
// 		IOUtils.copy(in, bos);
// 		in.close();
// 		bos.close();
//
// //      out.println(addr);
// 		String data = bos.getOut().toString();
//
// 		JSONObject json = new JSONObject();
//
// 		json.put("data", data);
//
// 		out.println(data);
//
// 	}
//
// 	@Override
// 	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
// 		// TODO Auto-generated method stub
// 		super.doPost(req, resp);
// 	}
//
//
// }
