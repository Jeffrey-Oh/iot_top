package project.spring.travel.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.travel.model.Member;
import project.spring.travel.service.MemberService;

/**
 * @fileName    : MemberServiceImpl.java
 * @author      : JoonsungHong
 * @description : 회원 관리 Interface에 대한 구현체 클래스 정의하기
 * @lastUpdate  : 2019-04-18
 */
@Service // 이 클래스가 Service 계층임을 명시한다.
public class MemberServiceImpl implements MemberService {
    /** 처리 결과를 기록할 Log4J 객체 생성 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class); // Log4j 객체는 직접 생성할 수 있다.
    
    /**
     * ===== 구현체에서 MyBatis 연동을 위한 객체 선언 =====
     * - 선언된 객체는 컨트롤러로부터 생성자 파라미터를 통해 주입받는다.
     */
    /** MyBatis */
    // -> import org.apache.ibatis.session.SqlSession
    @Autowired
    SqlSession sqlSession;
    
    /**
     * ##### 메서드에 대한 공통 부분 #####
     * SQL문에 문제가 있다면 예외가 발생될 것이다.
     * 예외는 사용자에게 프로그램에 문제가 있음을 알려야 하는 시점이기 때문에
     * 사용자 UI를 관리하는 컨트롤러(=Main)에서 처리하는 것이 바람직하다.
     * throws를 명시하여 발생되는 모든 예외를 메서드를 호출하는 곳으로 이관
     * 시켰기 때문에, 이 메서드에서는 예외처리가 필요없다.
     * 대신 메서드를 호출하는 위치에서는 try~catch의 사용이 강제된다.
     */
    
    /** ################### 회원가입하기 ##################### */
    /** 회원가입 가능 여부 검사 */
    @Override
    public Member memberCheck(Member member) throws Exception {
        Member memberCheck = null;
        
        try {
            // 기존 회원인지 검사
            memberCheck = sqlSession.selectOne("MemberMapper.memberCheck", member);
            
            // 조회된 데이터가 있을 경우 강제로 예외를 발생시킨다.
            if(memberCheck != null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            // 조회된 데이터가 있는 경우
            logger.error(e.getLocalizedMessage());
            throw new Exception(memberCheck.getUserName()+ "님의 저장된 회원정보가 있습니다.");
        } catch (Exception e) {
            // SQL문에 문제가 있는 경우.
            /**
             * 데이터베이스로부터 전달되는 에러메시지를 상세히
             * 확인하기 위하여
             * e.getMassage()의 리턴값을 함께 기록한다.
             */
            logger.error(e.getLocalizedMessage());
            throw new Exception("회원정보 조회에 실패했습니다.");
        } // End try~catch
        
        return memberCheck;
        
    } // End memberCheck Method
    
    /**
     * ===== 이메일 중복 검사 =====
     * - Mapper의 리턴값이 0보다 큰 경우
     *     이미 동일한 값이 존재하고 있다는 의미이므로 호출한 곳에서 예외를 발생시킨다.
     */
    @Override
    public int emailCheck(Member member) throws Exception {
        int result = 0;
        try {
            // selectOne, selectList 중 하나의 메서드를 호출한다.
            // 파라미터1: MyBatis Mapper에 정의한 기능 이름
            // 파라미터2: 조회할 데이터를 담고 있는 Beans 객체
            // MyBatis의 데이터 조회 결과를 미리 준비한 객체에 저장한다.
            result = sqlSession.selectOne("MemberMapper.emailCheck", member);
        } catch(Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("이메일 중복검사에 실패했습니다."); // 처리 도중 에러가 발생할 경우 사용자에게 표시할 메시지
        }
        
        return result;
    } // End emailCheck Method

    /**
     * ===== 아이디 중복 검사 =====
     * - Mapper의 리턴값이 0보다 큰 경우
     *     이미 동일한 값이 존재하고 있다는 의미이므로 호출한 곳에서 예외를 발생시킨다.
     */
    @Override
    public int userIdCheck(Member member) throws Exception {
        int result = 0;
        try {
            // selectOne, selectList 중 하나의 메서드를 호출한다.
            // 파라미터1: MyBatis Mapper에 정의한 기능 이름
            // 파라미터2: 조회할 데이터를 담고 있는 Beans 객체
            // MyBatis의 데이터 조회 결과를 미리 준비한 객체에 저장한다.
            result = sqlSession.selectOne("MemberMapper.userIdCheck", member);
        } catch(Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("아이디 중복검사에 실패했습니다."); // 처리 도중 에러가 발생할 경우 사용자에게 표시할 메시지
        }
        
        return result;
    } // End userIdCheck Method
    
    /** 회원정보 저장하기 + 저장결과를 조회하여 리턴하기 */
    @Override
    public void addMember(Member member) throws Exception {
        // 아이디 중복검사 및 기존회원 검사 호출
        memberCheck(member);
        emailCheck(member);
        userIdCheck(member);
        
        // 데이터 저장처리 = 가입
        // not null로 설정된 값이 설정되지 않았다면 예외 발생됨.
        try {
            // 입력값을 토대로 회원가입 정보 입력 후 저장
            int cnt = sqlSession.insert("MemberMapper.addMember", member);
            
            // 저장된 데이터의 행 수가 없을 경우 강제로 예외를 발생시킨다.
            if(cnt == 0) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            // 저장된 데이터가 없는 경우
            logger.error(e.getLocalizedMessage());
            throw new Exception("저장된 회원정보가 없습니다.");
        } catch (Exception e) {
            // SQL문에 문제가 있는 경우.
            /**
             * 데이터베이스로부터 전달되는 에러메시지를 상세히
             * 확인하기 위하여
             * e.getMassage()의 리턴값을 함께 기록한다.
             */
            logger.error(e.getLocalizedMessage());
            throw new Exception("회원가입에 실패했습니다.");
        }
    } // End addMember Method
    /** ################### End 회원가입하기 ##################### */

    /** ################### 회원정보 수정하기 ################### */
    @Override
    public void editMember(Member member) throws Exception {
        // 이메일 중복검사 호출
        emailCheck(member);
        
        try {
            // 입력값을 토대로 회원가입 정보 입력 후 저장
            int result = sqlSession.update("MemberMapper.editMember", member);
            
            // 수정된 데이터의 행 수가 없을 경우 강제로 예외를 발생시킨다.
            if(result == 0) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            // 수정된 데이터가 없는 경우
            throw new Exception("변경된 회원정보가 없습니다.");
        } catch (Exception e) {
            // SQL문에 문제가 있는 경우.
            /**
             * 데이터베이스로부터 전달되는 에러메시지를 상세히
             * 확인하기 위하여
             * e.getMassage()의 리턴값을 함께 기록한다.
             */
            logger.error(e.getLocalizedMessage());
            throw new Exception("회원정보 수정에 실패했습니다.");
        }
    } // End editMember Method
    
    /**
     * ===== 코드 패턴에 따른 회원정보 수정 처리 구현체 기능 구현 =====
     */
    @Override
    public Member selectMember(Member member) throws Exception {
        Member result = null;
        
        try {
            // selectOne, selectList 중 하나의 메서드를 호출한다.
            // 파라미터1: MyBatis Mapper에 정의한 기능 이름
            // 파라미터2: 조회할 데이터를 담고 있는 Beans 객체
            // MyBatis의 데이터 조회 결과를 미리 준비한 객체에 저장한다.
            result = sqlSession.selectOne("MemberMapper.selectMember", member);
            
            // 회원번호와 일련번호가 일치하는데이터가 null이라면, 일련번호가 잘못된 상태
            if(result == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            // 조회된 데이터가 없는 경우
            throw new Exception("조회된 회원정보가 없습니다."); // 조회된 데이터가 없을 경우 사용자에게 표시할 메시지
        } catch (Exception e) {
            // SQL문에 문제가 있는 경우.
            /**
             * 데이터베이스로부터 전달되는 에러메시지를 상세히
             * 확인하기 위하여
             * e.getMassage()의 리턴값을 함께 기록한다.
             */
            logger.error(e.getLocalizedMessage());
            throw new Exception("회원정보 조회에 실패했습니다."); // 처리 도중 에러가 발생할 경우 사용자에게 표시할 메시지
        } // End try~catch
        
        return result;
    } // End selectMember Method
    /** ################### End 회원정보 수정하기 ################### */
    
    /** ###################### 휴면 계정 삭제하기 ###################### */
    @Override
    public void inactiveMemberDelete(Member member) throws Exception {
        try {
            // 휴면 계정 삭제하기
            sqlSession.delete("MemberMapper.inactiveMemberDelete");
        } catch (Exception e) {
            // SQL문에 문제가 있는 경우.
            logger.error(e.getLocalizedMessage());
            throw new Exception("휴면계정 삭제에 실패했습니다.");
        }
    } // End inactiveMemberDelete Method
    /** ###################### End 휴면 계정 삭제하기 ###################### */
    
    /** ##### 로그인 시 휴면 계정일 경우 다시 계정 활성으로 전환하기 위한 작업 ##### */
    @Override
    public void activeMember(Member member) throws Exception {
        try {
            // 회원 비활성을 위한 데이터 수정
            sqlSession.update("MemberMapper.activeMember", member);
        } catch (Exception e) {
            // SQL문에 문제가 있는 경우.
            logger.error(e.getLocalizedMessage());
            throw new Exception("회원 활성화에 실패했습니다.");
        }
    } // End activeMember Method
    /** ##### End 로그인 시 휴면 계정일 경우 다시 계정 활성으로 전환하기 위한 작업 ##### */
    
    /** ###################### 아이디 찾기 본인인증 검사 ###################### */
    @Override
    public Member userIdSearch(Member member) throws Exception {
        Member userIdSearch = null;
        
        try {
            // 본인인증 가능 여부 검사
            userIdSearch = sqlSession.selectOne("MemberMapper.userIdSearch", member);
        } catch (Exception e) {
            // SQL문에 문제가 있는 경우.
            /**
             * 데이터베이스로부터 전달되는 에러메시지를 상세히
             * 확인하기 위하여
             * e.getMassage()의 리턴값을 함께 기록한다.
             */
            logger.error(e.getLocalizedMessage());
            throw new Exception("아이디 찾기에 실패했습니다.");
        } // End try~catch
        
        return userIdSearch;
    } // End userIdSearch Method
    /** ###################### End 아이디 찾기 본인인증 검사 ###################### */

    /** ###################### 비밀번호 찾기 본인인증 검사 ###################### */
    @Override
    public Member userPwSearch(Member member) throws Exception {
        Member userPwSearch = null;
        
        try {
            // 본인인증 가능 여부 검사
            userPwSearch = sqlSession.selectOne("MemberMapper.userPwSearch", member);
        } catch (Exception e) {
            // SQL문에 문제가 있는 경우.
            /**
             * 데이터베이스로부터 전달되는 에러메시지를 상세히
             * 확인하기 위하여
             * e.getMassage()의 리턴값을 함께 기록한다.
             */
            logger.error(e.getLocalizedMessage());
            throw new Exception("비밀번호 찾기에 실패했습니다.");
        } // End try~catch
        
        return userPwSearch;
    } // End userPwSearch Method
    /** ###################### End 비밀번호 찾기 본인인증 검사 ###################### */
    
    /** ###################### 비밀번호 재설정하기 ###################### */
    @Override
    public void userPwChange(Member member) throws Exception {
        try {
            // 회원 비활성을 위한 데이터 수정
            int cnt = sqlSession.update("MemberMapper.userPwChange", member);
            
            // 수정된 데이터의 행 수가 없을 경우 강제로 예외를 발생시킨다.
            if(cnt == 0) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            // 수정된 데이터가 없는 경우
            logger.error(e.getLocalizedMessage());
            throw new Exception("변경된 비밀번호가 없습니다.");
        } catch (Exception e) {
            // SQL문에 문제가 있는 경우.
            /**
             * 데이터베이스로부터 전달되는 에러메시지를 상세히
             * 확인하기 위하여
             * e.getMassage()의 리턴값을 함께 기록한다.
             */
            logger.error(e.getLocalizedMessage());
            throw new Exception("비밀번호 변경에 실패했습니다.");
        }
    } // End userPwChange Method
    /** ###################### End 비밀번호 재설정하기 ###################### */
    
    /** ####################### 본인인증 공통 ####################### */
    /**
     * # 회원정보 수정
     * # 회원 비활성
     * # 회원 로그인
     */
    @Override
    public Member memberSearch(Member member) throws Exception {
        Member memberSearch = null;
        
        try {
            // 본인인증 가능 여부 검사
            // selectOne, selectList 중 하나의 메서드를 호출한다.
            // 파라미터1: MyBatis Mapper에 정의한 기능 이름
            // 파라미터2: 조회할 데이터를 담고 있는 Beans 객체
            // MyBatis의 데이터 조회 결과를 미리 준비한 객체에 저장한다.
            memberSearch = sqlSession.selectOne("MemberMapper.memberSearch", member);
            
            // 조회된 데이터가 없다는 것은 Where절 조건에 맞는 데이터가 없다는 것.
            // -> Where절은 아이디와 비밀번호가 일치하는 항목을 지정하므로,
            //     조회된 데이터가 없다는 것은 아이디나 비밀번호가 잘못되었음을 의미한다.
            // 조회된 데이터가 없을 경우 강제로 예외를 발생시킨다.
            if(memberSearch == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            // 조회된 데이터가 없는 경우
            throw new Exception("아이디나 비밀번호가 잘못되었습니다."); // 조회된 데이터가 없을 경우 사용자에게 표시할 메시지
        } catch (Exception e) {
            // SQL문에 문제가 있는 경우.
            /**
             * 데이터베이스로부터 전달되는 에러메시지를 상세히
             * 확인하기 위하여
             * e.getMassage()의 리턴값을 함께 기록한다.
             */
            logger.error(e.getLocalizedMessage());
            throw new Exception("본인인증 검사에 실패했습니다."); // 처리 도중 에러가 발생할 경우 사용자에게 표시할 메시지
        } // End try~catch
        
        return memberSearch;
    } // End memberSearch Method
    /** ####################### End 본인인증 공통 ####################### */

    /**
     * ===== 비밀번호 검사 기능 구현하기 =====
     */
    @Override
    public void selectMemberPasswordCount(Member member) throws Exception {
        try {
            // 본인인증 가능 여부 검사
            // selectOne, selectList 중 하나의 메서드를 호출한다.
            // 파라미터1: MyBatis Mapper에 정의한 기능 이름
            // 파라미터2: 조회할 데이터를 담고 있는 Beans 객체
            // MyBatis의 데이터 조회 결과를 미리 준비한 객체에 저장한다.
            int result = sqlSession.selectOne("MemberMapper.selectMemberPasswordCount", member);
            
            // 회원번호와 비밀번호가 일치하는데이터가 0이라면, 비밀번호가 잘못된 상태
            if(result == 0) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            // 조회된 데이터가 없는 경우
            throw new Exception("잘못된 비밀번호 입니다."); // 조회된 데이터가 없을 경우 사용자에게 표시할 메시지
        } catch (Exception e) {
            // SQL문에 문제가 있는 경우.
            /**
             * 데이터베이스로부터 전달되는 에러메시지를 상세히
             * 확인하기 위하여
             * e.getMassage()의 리턴값을 함께 기록한다.
             */
            logger.error(e.getLocalizedMessage());
            throw new Exception("비밀번호 검사에 실패했습니다."); // 처리 도중 에러가 발생할 경우 사용자에게 표시할 메시지
        } // End try~catch
        
    } // End selectMemberPasswordCount Method
    
    /** ##### 회원 탈퇴하기 ##### */
    /**
     * - 코드패턴에 따른 회원 탈퇴 기능 구현하기
     * - Insert, Update, Delete 처리는 트렌젝션이 요구되므로
     *     rollback()과 commit()을 적절히 호출해야 한다.
     */
    @Override
    public void deleteMember(Member member) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : insert, update, delete 중 수행하려는 기능에 대한 메서드
        // #{2} : MyBatis Mapper에 정의한 기능 이름
        // #{3} : 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
        // #{4} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{5} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        try {
            int result = sqlSession.delete("MemberMapper.deleteMember", member);
         
            if(result == 0) {
                // 수행된 행이 없다면 강제로 예외를 발생시킨다.
                // -> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("이미 탈퇴한 회원 입니다.");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
                throw new Exception("회원탈퇴에 실패했습니다.");
        }
        
    } // End deleteMember Method
    /** ##### End 회원 탈퇴하기 ##### */

    /**
     * ===== 회원 목록 조회 =====
     */
    @Override
    public List<Member> selectMemberList(Member member) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : 단일행 조회인 경우 객체, 다중행 조회인 경우 컬렉션
        // #{2} : selectOne, selectList 중 하나의 메서드를 호출한다.
        // #{3} : MyBatis Mapper에 정의한 기능 이름
        // #{4} : Where절을 쓸 경우 조건값을 담은 객체
        // #{5} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{6} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        List<Member> result = null;

        try {
            result = sqlSession.selectList("MemberMapper.selectMemberList", member);
         
            if(result == null) {
                // 조회 결과가 없을 경우 강제로 예외를 발생시킨다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("조회된 회원 목록이 없습니다."); // 조회결과가 없을 경우 사용자에게 전달할 메시지를 설정한다.
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage()); // SQL문 자체의 에러인 경우 -> 에러 내용을 로그에 기록하고,
            throw new Exception("회원 목록 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
        }

        return result;
    } // End selectMemberList Method

    /**
     * ===== 전체 회원 수를 구하기 위한 기능 =====
     */
    @Override
    public int selectMemberCount(Member member) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : 단일행 조회인 경우 객체, 다중행 조회인 경우 컬렉션
        // #{2} : selectOne, selectList 중 하나의 메서드를 호출한다.
        // #{3} : MyBatis Mapper에 정의한 기능 이름
        // #{4} : Where절을 쓸 경우 조건값을 담은 객체
        // #{5} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{6} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        int result = 0;

        try {
            result = sqlSession.selectOne("MemberMapper.selectMemberCount", member);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage()); // SQL문 자체의 에러인 경우 -> 에러 내용을 로그에 기록하고,
            throw new Exception("회원 목록 수 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
        }
        
        return result;
    } // End selectMemberCount Method

    /**
     * ===== 회원 등급을 수정하기 위한 기능 =====
     */
    @Override
    public void updateMemberGrade(Member member) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : insert, update, delete 중 수행하려는 기능에 대한 메서드
        // #{2} : MyBatis Mapper에 정의한 기능 이름
        // #{3} : 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
        // #{4} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{5} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        try {
            int result = sqlSession.update("MemberMapper.updateMemberGrade", member);
         
            if(result == 0) {
                // 수행된 행이 없다면 강제로 예외를 발생시킨다.
                // -> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("수정된 회원의 등급이 없습니다.");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("회원등급 수정에 실패했습니다.");
        }
        
    } // End setGradeMember Method

}
