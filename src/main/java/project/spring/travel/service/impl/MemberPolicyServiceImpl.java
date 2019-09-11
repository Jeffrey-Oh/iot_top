package project.spring.travel.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.travel.model.MemberPolicy;
import project.spring.travel.service.MemberPolicyService;

/**
 * @fileName    : MemberPolicyServiceImpl.java
 * @author      : 홍준성
 * @description : 정책안내 서비스 Interface에 대한 구현체 클래스
 * @lastUpdate  : 2019. 4. 18.
 */
@Service // 이 클래스가 Service 계층임을 명시한다.
public class MemberPolicyServiceImpl implements MemberPolicyService {
    /** 처리 결과를 기록할 Log4J 객체 생성 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    private static Logger logger = LoggerFactory.getLogger(MemberPolicyServiceImpl.class); // Log4j 객체는 직접 생성할 수 있다.
    
    /**
     * ===== 구현체에서 MyBatis 연동을 위한 객체 선언 =====
     * - 선언된 객체는 컨트롤러로부터 생성자 파라미터를 통해 주입받는다.
     */
    /** MyBatis */
    // -> import org.apache.ibatis.session.SqlSession
    @Autowired
    SqlSession sqlSession;
    
    /**
     * ===== 코드패턴에 따른 정책안내 저장 기능 구현 =====
     */
    @Override
    public void insertPolicy(MemberPolicy policy) throws Exception {
        try {
            // insert, update, delete 중 하나의 메서드를 호출한다.
            // 파라미터1: MyBatis Mapper에 정의한 기능 이름
            // 파라미터2: 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
            int result = sqlSession.insert("MemberPolicyMapper.insertPolicy", policy);
            
            if(result == 0) {
                throw new NullPointerException();
            }
        } catch(NullPointerException e) {
            throw new Exception("저장된 정책안내가 없습니다."); // 처리 결과가 없는 경우 사용자에게 표시할 메시지
        } catch(Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("정책안내 등록에 실패했습니다."); // 처리 도중 에러가 발생할 경우 사용자에게 표시할 메시지
        }
        
    } // End insertPolicy Method

    /**
     * ===== 코드패턴에 따른 정책안내 수정 기능 구현 =====
     */
    @Override
    public void updatePolicy(MemberPolicy policy) throws Exception {
        try {
            // insert, update, delete 중 하나의 메서드를 호출한다.
            // 파라미터1: MyBatis Mapper에 정의한 기능 이름
            // 파라미터2: 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
            int result = sqlSession.update("MemberPolicyMapper.updatePolicy", policy);
            
            if(result == 0) {
                throw new NullPointerException();
            }
        } catch(NullPointerException e) {
            throw new Exception("수정된 정책안내가 없습니다."); // 처리 결과가 없는 경우 사용자에게 표시할 메시지
        } catch(Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("정책안내 수정에 실패했습니다."); // 처리 도중 에러가 발생할 경우 사용자에게 표시할 메시지
        }
        
    } // End updatePolicy Method

    /**
     * ===== 코드패턴에 따른 정책안내 삭제 기능 구현 =====
     */
    @Override
    public void deletePolicy(MemberPolicy policy) throws Exception {
        try {
            // insert, update, delete 중 하나의 메서드를 호출한다.
            // 파라미터1: MyBatis Mapper에 정의한 기능 이름
            // 파라미터2: 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
            int result = sqlSession.delete("MemberPolicyMapper.deletePolicy", policy);
            
            if(result == 0) {
                throw new NullPointerException();
            }
        } catch(NullPointerException e) {
            throw new Exception("삭제된 정책안내가 없습니다."); // 처리 결과가 없는 경우 사용자에게 표시할 메시지
        } catch(Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("정책안내 삭제에 실패했습니다."); // 처리 도중 에러가 발생할 경우 사용자에게 표시할 메시지
        }
        
    } // End deletePolicy Method

    /**
     * ===== 코드패턴에 따른 정책안내 항목 하나를 조회하는 기능 구현 =====
     */
    @Override
    public MemberPolicy selectPolicy(MemberPolicy policy) throws Exception {
        MemberPolicy result = null;
        
        try {
            // selectOne, selectList 중 하나의 메서드를 호출한다.
            // 파라미터1: MyBatis Mapper에 정의한 기능 이름
            // 파라미터2: 조회할 데이터를 담고 있는 Beans 객체
            // MyBatis의 데이터 조회 결과를 미리 준비한 객체에 저장한다.
            result = sqlSession.selectOne("MemberPolicyMapper.selectPolicy", policy);
            
            if(result == null) {
                throw new NullPointerException();
            }
        } catch(NullPointerException e) {
            throw new Exception("조회된 정책안내가 없습니다."); // 조회된 데이터가 없을 경우 사용자에게 표시할 메시지
        } catch(Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("정책안내 조회에 실패했습니다."); // 처리 도중 에러가 발생할 경우 사용자에게 표시할 메시지
        }
        
        return result;
    } // End selectPolicy Method

    /**
     * ===== 코드패턴에 따른 정책안내 목록 조회 기능 구현 =====
     */
    @Override
    public List<MemberPolicy> selectPolicyList() throws Exception {
        List<MemberPolicy> result = null;
        
        try {
            // selectOne, selectList 중 하나의 메서드를 호출한다.
            // 파라미터1: MyBatis Mapper에 정의한 기능 이름
            // 파라미터2: 조회할 데이터를 담고 있는 Beans 객체
            // MyBatis의 데이터 조회 결과를 미리 준비한 객체에 저장한다.
            result = sqlSession.selectList("MemberPolicyMapper.selectPolicyList", null);
            
            if(result == null) {
                throw new NullPointerException();
            }
        } catch(NullPointerException e) {
            throw new Exception("조회된 정책안내 목록이 없습니다."); // 조회된 데이터가 없을 경우 사용자에게 표시할 메시지
        } catch(Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("정책안내 목록 조회에 실패했습니다."); // 처리 도중 에러가 발생할 경우 사용자에게 표시할 메시지
        }
        
        return result;
    } // End selectPolicyList Method

}
