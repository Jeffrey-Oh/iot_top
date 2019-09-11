//package project.spring.travel.test.service;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import project.java.travel.dao.MyBatisConnectionFactory;
//import project.java.travel.service.MemberService;
//import project.java.travel.service.impl.MemberServiceImpl;
//
///**
// * @fileName    : RemoveMember.java
// * @author      : JoonsungHong
// * @description : 휴면 계정 삭제를 위한 Main -> 데이터 삭제를 위한 처리 절차
// * @lastUpdate  : 2019-02-20
// */
//public class RemoveMember {
//
//    public static void main(String[] args) {
//        Logger logger = LogManager.getFormatterLogger(RemoveMember.class.getName());
//        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
//        MemberService memberService = new MemberServiceImpl(sqlSession, logger);
//        
//        try {
//            // 휴면 계정으로 전환한 회원이 1년 이상 활동이 없을 경우 삭제 진행
//            memberService.inactiveMemberDelete(null);
//            // 삭제된 회원 수
//            System.out.println("회원 정보가 삭제되었습니다.");
//        } catch (Exception e) {
//            System.out.println(e.getLocalizedMessage());
//            return;
//        } finally {
//            sqlSession.close();
//        }
//    }
//
//}
