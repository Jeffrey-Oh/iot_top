package project.spring.travel.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.travel.model.MemberOutReason;
import project.spring.travel.service.MemberOutReasonService;

/**
 * @fileName    : MemberOutReasonServiceImpl.java
 * @author      : 홍준성
 * @description : 회원 탈퇴 사유를 처리하기 위한 Service 계층의 Impl 클래스
 * @lastUpdate  : 2019. 4. 18.
 */
@Service // 이 클래스가 Service 계층임을 명시한다.
public class MemberOutReasonServiceImpl implements MemberOutReasonService {
    /** 처리 결과를 기록할 Log4J 객체 생성 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    private static Logger logger = LoggerFactory.getLogger(MemberOutReasonServiceImpl.class); // Log4j 객체는 직접 생성할 수 있다.
    
    /**
     * ===== 구현체에서 MyBatis 연동을 위한 객체 선언 =====
     * - 선언된 객체는 컨트롤러로부터 생성자 파라미터를 통해 주입받는다.
     */
    /** MyBatis */
    // -> import org.apache.ibatis.session.SqlSession
    @Autowired
    SqlSession sqlSession;
    
    /**
     * 탈퇴 사유를 저장하기 위한 코드 패턴
     */
    @Override
    public void insertMemberOutReason(MemberOutReason memberOutReason) throws Exception {
        try {
            // insert, update, delete 중 하나의 메서드를 호출한다.
            // 파라미터1: MyBatis Mapper에 정의한 기능 이름
            // 파라미터2: 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
            int result = sqlSession.insert("MemberOutReasonMapper.insertMemberOutReason", memberOutReason);
            
            if(result == 0) {
                throw new NullPointerException();
            }
        } catch(NullPointerException e) {
            throw new Exception("저장된 회원탈퇴 사유가 없습니다."); // 처리 결과가 없는 경우 사용자에게 표시할 메시지
        } catch(Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("회원탈퇴 사유 저장에 실패했습니다."); // 처리 도중 에러가 발생할 경우 사용자에게 표시할 메시지
        }
        
    } // End insertMemberOutReason Method

}
