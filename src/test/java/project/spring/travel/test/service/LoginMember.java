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
// * @fileName    : LoginMember.java
// * @author      : JoonsungHong
// * @description : 회원 로그인을 위한 Main -> 데이터 조회를 위한 처리 절차
// * @lastUpdate  : 2019-02-20
// */
//public class LoginMember {
//
//    public static void main(String[] args) {
//        Logger logger = LogManager.getFormatterLogger(LoginMember.class.getName());
//        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
//        MemberService memberService = new MemberServiceImpl(sqlSession, logger);
//        
//        // 로그인을 하기 위한 정보를 입력받는다.
//        Member memberSearch = new Member();
//        memberSearch.setUserId("MasterHong");
//        memberSearch.setUserPw("EnaEoadl!@#");
//        
//        try {
//            // 로그인을 진행
//            Member memSearch = memberService.memberSearch(memberSearch);
//            System.out.println(memSearch.toString());
//            // 로그인 할 회원이 휴면 계정이라면
//            if(memSearch.getDeleteDate() != null) {
//                // 휴면 계정에서 활성 계정으로 전환하기 위한 회원 정보를 가져온다.
//                Member member = new Member();
//                member.setMemberId(memSearch.getMemberId());
//
//                try {
//                    // 휴면 계정에서 활성 계정으로 전환
//                    memberService.activeMember(member);
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
//        System.out.println("로그인 되었습니다.");
//    }
//
//}
