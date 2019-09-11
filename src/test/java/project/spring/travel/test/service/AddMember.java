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
// * @fileName    : AddMember.java
// * @author      : JoonsungHong
// * @description : 회원가입 정보를 저장을 위한 Main -> 저장을 위한 처리 절차
// * @lastUpdate  : 2019-02-19
// */
//public class AddMember {
//
//    public static void main(String[] args) {
//        Logger logger = LogManager.getFormatterLogger(AddMember.class.getName());
//        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
//        MemberService memberService = new MemberServiceImpl(sqlSession, logger);
//        
//        // 기존 가입 회원인지 확인하기 위해 정보를 입력받는다.
//        Member memberCheck = new Member();
//        memberCheck.setUserName("홍준성");
//        memberCheck.setGender("M");
//        memberCheck.setBirthDate("1991-10-30");
//        memberCheck.setEmail("masterHong1234@gmail.com");
//        
//        try {
//            // 기존 가입 회원인지 검사
//            Member memCheck = memberService.memberCheck(memberCheck);
//            // 미가입 회원일 경우 회원가입 진행
//            if(memCheck == null) {
//                // 회원가입을 위해 정보를 입력받는다.
//                Member member = new Member();
//                member.setUserName(memberCheck.getUserName());
//                member.setGender(memberCheck.getGender());
//                member.setBirthDate(memberCheck.getBirthDate());
//                member.setUserId("MasterHong");
//                member.setUserPw("EnaEoadl!@#");
//                member.setPhone("01055919711");
//                member.setEmail(memberCheck.getEmail());
//                member.setPostcode("12345");
//                member.setAddress1("서울시 양천구 목동");
//                member.setAddress2("목동아파트 1004호");
//                member.setRegDate("2019-02-19");
//                member.setGrade("master");
//
//                try {
//                    // 입력된 정보를 토대로 회원가입 진행 후 성공하면 저장
//                    memberService.addMember(member);
//                    
//                    System.out.println(member.getMemberId() + "번 데이터 저장됨.");
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
//        
//    }
//
//}
