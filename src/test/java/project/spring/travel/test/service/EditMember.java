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
// * @fileName    : EditMember.java
// * @author      : JoonsungHong
// * @description : 회원가입 정보를 수정을 위한 Main -> 수정을 위한 처리 절차
// * @lastUpdate  : 2019-02-19
// */
//public class EditMember {
//
//    public static void main(String[] args) {
//        Logger logger = LogManager.getFormatterLogger(EditMember.class.getName());
//        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
//        MemberService memberService = new MemberServiceImpl(sqlSession, logger);
//        
//        // 회원정보 수정을 하기 위해 본인인증을 위한 정보를 입력받는다.
//        Member memberSearch = new Member();
//        memberSearch.setUserId("MasterHong");
//        memberSearch.setUserPw("EnaEoadl!@#");
//        
//        try {
//            // 회원정보 수정을 하기 위해 본인인증
//            Member memSearch = memberService.memberSearch(memberSearch);
//            // 본인인증이 완료되면 회원정보 수정 진행
//            if(memSearch != null) {
//                // 회원정보 수정을 위한 변경 정보들을 입력받는다.
//                Member member = new Member();
//                member.setMemberId(memSearch.getMemberId());
//                member.setUserPw("EnaEoadl!@#");
//                member.setPhone("01012345678");
//                member.setEmail("masterhong5678@gmail.com");
//                member.setPostcode("54321");
//                member.setAddress1("서울시 서초구 서초동");
//                member.setAddress2("서초아파트 1004호");
//                member.setToEmailCheckedDate("2019-02-19");
//                member.setToSmsCheckedDate("2019-02-19");
//
//                try {
//                    // 입력된 정보들을 토대로 회원정보 수정을 진행
//                    memberService.editMember(member);
//                } catch (Exception e) {
//                    System.out.println(e.getLocalizedMessage());
//                    return;
//                } finally {
//                    sqlSession.close();
//                }
//                
//            }
//        } catch (Exception e) {
//            System.out.println(e.getLocalizedMessage());
//            return;
//        } finally {
//            sqlSession.close();
//        }
//        
//        System.out.println("수정되었습니다.");
//    }
//
//}
