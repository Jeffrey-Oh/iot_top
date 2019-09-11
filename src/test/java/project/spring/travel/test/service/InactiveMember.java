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
// * @fileName    : InactiveMember.java
// * @author      : JoonsungHong
// * @description : 회원 비활성을 위한 Main -> 휴면 계정으로 전환을 위한 처리 절차
// * @lastUpdate  : 2019-02-20
// */
//public class InactiveMember {
//
//    public static void main(String[] args) {
//        Logger logger = LogManager.getFormatterLogger(InactiveMember.class.getName());
//        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
//        MemberService memberService = new MemberServiceImpl(sqlSession, logger);
//        
//        // 회원 비활성을 하기 위해 본인인증을 위한 정보 입력을 받는다.
//        Member memberSearch = new Member();
//        memberSearch.setUserId("MasterHong");
//        memberSearch.setUserPw("EnaEoadl!@#");
//        
//        try {
//            // 회원 비활성을 하기 위해 본인인증 진행
//            Member memSearch = memberService.memberSearch(memberSearch);
//            // 본인인증이 성공하면 회원 비활성을 진행
//            if(memSearch != null) {
//                // 휴면 계정으로 전환할 회원정보를 가져온다.
//                Member member = new Member();
//                member.setMemberId(memSearch.getMemberId());
//
//                try {
//                    // 휴면 계정으로 전환
////                    memberService.inactiveMember(member);
//                } catch (Exception e) {
//                    System.out.println(e.getLocalizedMessage());
//                    return;
//                } finally {
//                    sqlSession.close();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e.getLocalizedMessage());
//            return;
//        } finally {
//            sqlSession.close();
//        }
//        
//        System.out.println("비활성화 되었습니다.");
//    }
//
//}
