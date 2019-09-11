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
// * @fileName    : UserPwSearch.java
// * @author      : JoonsungHong
// * @description : 회원 비밀번호를 조회하기 위한 Main -> 조회를 위한 처리 절차
// * @lastUpdate  : 2019-02-20
// */
//public class UserPwSearch {
//
//    public static void main(String[] args) {
//        Logger logger = LogManager.getFormatterLogger(UserIdSearch.class.getName());
//        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
//        MemberService memberService = new MemberServiceImpl(sqlSession, logger);
//        
//        // 비밀번호 찾기를 위해 본인인증을 위한 정보를 입력받는다.
//        Member userPwSearch = new Member();
//        userPwSearch.setUserId("MasterHong");
//        userPwSearch.setUserName("홍준성");
//        userPwSearch.setGender("M");
//        userPwSearch.setBirthDate("1991-10-30");
//        userPwSearch.setEmail("masterHong1234@gmail.com");
//        
//        try {
//            // 비밀번호 찾기를 위해 진행
//            Member member = memberService.userPwSearch(userPwSearch);
//            System.out.println(member.toString());
//            
//            // 비밀번호를 찾으려는 회원의 비밀번호를 재설정을 위해 변경할 정보를 입력받는다.
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
//    }
//
//}
