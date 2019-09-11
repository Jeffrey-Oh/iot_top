//package project.spring.travel.test.service;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import project.java.travel.dao.MyBatisConnectionFactory;
//import project.java.travel.model.Member;
//import project.java.travel.service.MemberService;
//import project.java.travel.service.impl.MemberServiceImpl;
//
///**
// * @fileName    : UserIdSearch.java
// * @author      : JoonsungHong
// * @description : 회원 아이디를 조회하기 위한 Main -> 조회를 위한 처리 절차
// * @lastUpdate  : 2019-02-20
// */
//public class UserIdSearch {
//
//    public static void main(String[] args) {
//        Logger logger = LogManager.getFormatterLogger(UserIdSearch.class.getName());
//        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
//        MemberService memberService = new MemberServiceImpl(sqlSession, logger);
//        
//        // 아이디 찾기를 위해 본인인증을 위한 정보를 입력받는다.
//        Member userIdSearch = new Member();
//        userIdSearch.setUserName("홍준성");
//        userIdSearch.setGender("M");
//        userIdSearch.setBirthDate("1991-10-30");
//        userIdSearch.setEmail("masterHong1234@gmail.com");
//        
//        try {
//            // 아이디 찾기를 진행
//            Member member = memberService.userIdSearch(userIdSearch);
//            System.out.println(member.toString());
//            
//            // 아이디를 찾은 회원이 비밀번호를 재설정 하고자 할 경우 변경할 정보를 입력받는다.
//            Member userPwChange = new Member();
//            userPwChange.setMemberId(member.getMemberId());
//            userPwChange.setUserPw("EnaEoadl!@#");
//            // 비밀번호 재설정을 진행
//            memberService.userPwChange(userPwChange);
//            
//            System.out.println(userPwChange.toString());
//        } catch (Exception e) {
//            System.out.println(e.getLocalizedMessage());
//            return;
//        } finally {
//            sqlSession.close();
//        }
//        
//        
//    }
//
//}
