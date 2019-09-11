package project.spring.travel.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.travel.model.ServiceareaFood;
import project.spring.travel.service.ServiceareaFoodService;

/**
 * @fileName    : ServiceareaFoodServiceImpl.java
 * @author      : 홍준성
 * @description : 휴게소 대표메뉴 Interface에 대한 구현체 클래스 정의하기
 * @lastUpdate  : 2019. 5. 02.
 */
@Service // 이 클래스가 Service 계층임을 명시한다.
public class ServiceareaFoodServiceImpl implements ServiceareaFoodService {
    /** 처리 결과를 기록할 Log4J 객체 생성 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    private static Logger logger = LoggerFactory.getLogger(ServiceareaFoodServiceImpl.class); // Log4j 객체는 직접 생성할 수 있다.
    
    /**
     * ===== 구현체에서 MyBatis 연동을 위한 객체 선언 =====
     * - 선언된 객체는 컨트롤러로부터 생성자 파라미터를 통해 주입받는다.
     */
    /** MyBatis */
    // -> import org.apache.ibatis.session.SqlSession
    @Autowired
    SqlSession sqlSession;
    
    /** 코드 패턴에 따른 휴게소 대표메뉴 정보 저장 기능 구현 */
    @Override
    public void insertServiceareaFood(ServiceareaFood serviceareaFood) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : insert, update, delete 중 수행하려는 기능에 대한 메서드
        // #{2} : MyBatis Mapper에 정의한 기능 이름
        // #{3} : 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
        // #{4} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{5} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        try {
            int cnt = sqlSession.update("ServiceareaFoodMapper.updateServiceareaFoodByServiceareaCode", serviceareaFood);
            
            // 업데이트된 데이터의 수가 없을 경우 새로 등록 처리를 한다.
            if(cnt == 0) {
                int result = sqlSession.insert("ServiceareaFoodMapper.insertServiceareaFood", serviceareaFood);
                
                if(result == 0) {
                    // 수행된 행이 없다면 강제로 예외를 발생시킨다.
                    // -> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
                    throw new NullPointerException();
                }
            }
        } catch (NullPointerException e) {
            throw new Exception("저장된 휴게소 대표메뉴 정보가 없습니다.");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("휴게소 대표메뉴 정보 저장에 실패했습니다.");
        }
    } // End insertServiceareaFood Method

    /** 코드 패턴에 따른 휴게소 대표메뉴 정보 조회 기능 구현 */
    @Override
    public ServiceareaFood selectServiceareaFood(ServiceareaFood serviceareaFood) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : 단일행 조회인 경우 객체, 다중행 조회인 경우 컬렉션
        // #{2} : selectOne, selectList 중 하나의 메서드를 호출한다.
        // #{3} : MyBatis Mapper에 정의한 기능 이름
        // #{4} : Where절을 쓸 경우 조건값을 담은 객체
        // #{5} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{6} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        ServiceareaFood result = null;

        try {
            result = sqlSession.selectOne("ServiceareaFoodMapper.selectServiceareaFood", serviceareaFood);
            // 조회된 휴게소 대표메뉴 정보가 없을 수 있으므로 없는 경우에 대한 예외처리는 하지 않음
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage()); // SQL문 자체의 에러인 경우 -> 에러 내용을 로그에 기록하고,
            throw new Exception("휴게소 대표메뉴 정보 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
        }

        return result;
    } // End selectServiceareaFood Method

    /** 코드 패턴에 따른 휴게소 대표메뉴 정보 목록 조회 기능 구현 */
    @Override
    public List<ServiceareaFood> selectServiceareaFoodList(ServiceareaFood serviceareaFood) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : 단일행 조회인 경우 객체, 다중행 조회인 경우 컬렉션
        // #{2} : selectOne, selectList 중 하나의 메서드를 호출한다.
        // #{3} : MyBatis Mapper에 정의한 기능 이름
        // #{4} : Where절을 쓸 경우 조건값을 담은 객체
        // #{5} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{6} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        List<ServiceareaFood> result = null;

        try {
            result = sqlSession.selectList("ServiceareaFoodMapper.selectServiceareaFoodList", serviceareaFood);
            
            if(result == null) {
                // 조회 결과가 없을 경우 강제로 예외를 발생시킨다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("조회된 휴게소 대표메뉴 목록이 없습니다."); // 조회결과가 없을 경우 사용자에게 전달할 메시지를 설정한다.
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage()); // SQL문 자체의 에러인 경우 -> 에러 내용을 로그에 기록하고,
            throw new Exception("휴게소 대표메뉴 목록 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
        }

        return result;
    } // End selectServiceareaFoodList Method

    /** 코드 패턴에 따른 휴게소 대표메뉴 정보 수정 기능 구현 */
    @Override
    public void updateServiceareaFood(ServiceareaFood serviceareaFood) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : insert, update, delete 중 수행하려는 기능에 대한 메서드
        // #{2} : MyBatis Mapper에 정의한 기능 이름
        // #{3} : 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
        // #{4} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{5} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        try {
            int result = sqlSession.update("ServiceareaFoodMapper.updateServiceareaFood", serviceareaFood);
         
            if(result == 0) {
                // 수행된 행이 없다면 강제로 예외를 발생시킨다.
                // -> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("수정된 휴게소 대표메뉴 정보가 없습니다.");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("휴게소 대표메뉴 정보 수정에 실패했습니다.");
        }
    } // End updateServiceareaFood Method

    /** 코드 패턴에 따른 휴게소 대표메뉴 정보 삭제 기능 구현 */
    @Override
    public void deleteServiceareaFood(ServiceareaFood serviceareaFood) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : insert, update, delete 중 수행하려는 기능에 대한 메서드
        // #{2} : MyBatis Mapper에 정의한 기능 이름
        // #{3} : 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
        // #{4} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{5} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        try {
            int result = sqlSession.delete("ServiceareaFoodMapper.deleteServiceareaFood", serviceareaFood);
         
            if(result == 0) {
                // 수행된 행이 없다면 강제로 예외를 발생시킨다.
                // -> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("삭제된 휴게소 대표메뉴 정보가 없습니다.");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("휴게소 대표메뉴 정보 삭제에 실패했습니다.");
        }
    } // End deleteServiceareaFood method

    /**
     * 코드패턴에 따른 휴게소 대표메뉴를 휴게소이름으로 조회하는 기능 구현
     */
    @Override
    public ServiceareaFood selectServiceareaFoodByServiceAreaName(ServiceareaFood serviceareaFood) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : 단일행 조회인 경우 객체, 다중행 조회인 경우 컬렉션
        // #{2} : selectOne, selectList 중 하나의 메서드를 호출한다.
        // #{3} : MyBatis Mapper에 정의한 기능 이름
        // #{4} : Where절을 쓸 경우 조건값을 담은 객체
        // #{5} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{6} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        ServiceareaFood result = null;

        try {
            result = sqlSession.selectOne("ServiceareaFoodMapper.selectServiceareaFoodByServiceAreaName", serviceareaFood);
            // 조회된 휴게소 대표메뉴 정보가 없을 수 있으므로 없는 경우에 대한 예외처리는 하지 않음
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage()); // SQL문 자체의 에러인 경우 -> 에러 내용을 로그에 기록하고,
            throw new Exception("휴게소 대표메뉴를 휴게소이름으로 정보 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
        }

        return result;
    } // End selectServiceareaFood Method

}
