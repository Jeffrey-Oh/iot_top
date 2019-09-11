package project.spring.travel.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.travel.model.ServiceareaGroup;
import project.spring.travel.service.ServiceareaGroupService;

/**
 * @fileName    : ServiceareaGroupServiceImpl.java
 * @author      : 홍준성
 * @description : 휴게소 완성 Interface에 대한 구현체 클래스 정의하기
 * @lastUpdate  : 2019. 5. 06.
 */
@Service // 이 클래스가 Service 계층임을 명시한다.
public class ServiceareaGroupServiceImpl implements ServiceareaGroupService {
    /** 처리 결과를 기록할 Log4J 객체 생성 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    private static Logger logger = LoggerFactory.getLogger(ServiceareaGroupServiceImpl.class); // Log4j 객체는 직접 생성할 수 있다.
    
    /**
     * ===== 구현체에서 MyBatis 연동을 위한 객체 선언 =====
     * - 선언된 객체는 컨트롤러로부터 생성자 파라미터를 통해 주입받는다.
     */
    /** MyBatis */
    // -> import org.apache.ibatis.session.SqlSession
    @Autowired
    SqlSession sqlSession;
    
    /** 코드 패턴에 따른 휴게소 완성 정보 저장 기능 구현 */
    @Override
    public void insertServiceareaGroup(ServiceareaGroup serviceareaGroup) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : insert, update, delete 중 수행하려는 기능에 대한 메서드
        // #{2} : MyBatis Mapper에 정의한 기능 이름
        // #{3} : 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
        // #{4} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{5} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        try {
            int result = sqlSession.update("ServiceareaGroupMapper.updateServiceareaGroupByServiceareaName", serviceareaGroup);
            
            // 업데이트된 데이터의 수가 없을 경우 새로 등록 처리를 한다.
            if(result == 0) {
                result = sqlSession.insert("ServiceareaGroupMapper.insertServiceareaGroup", serviceareaGroup);
                
                if(result == 0) {
                    // 수행된 행이 없다면 강제로 예외를 발생시킨다.
                    // -> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
                    throw new NullPointerException();
                }
            }
        } catch (NullPointerException e) {
            throw new Exception("저장된 휴게소 완성 정보가 없습니다.");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("휴게소 완성 정보 저장에 실패했습니다.");
        }
    } // End insertServiceareaGroup Method

    /** 코드 패턴에 따른 휴게소 완성 정보 조회 기능 구현 */
    @Override
    public ServiceareaGroup selectServiceareaGroup(ServiceareaGroup serviceareaGroup) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : 단일행 조회인 경우 객체, 다중행 조회인 경우 컬렉션
        // #{2} : selectOne, selectList 중 하나의 메서드를 호출한다.
        // #{3} : MyBatis Mapper에 정의한 기능 이름
        // #{4} : Where절을 쓸 경우 조건값을 담은 객체
        // #{5} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{6} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        ServiceareaGroup result = null;

        try {
            result = sqlSession.selectOne("ServiceareaGroupMapper.selectServiceareaGroup", serviceareaGroup);
            // 조회된 휴게소 완성 정보가 없을 수 있으므로 없는 경우에 대한 예외처리는 하지 않음
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage()); // SQL문 자체의 에러인 경우 -> 에러 내용을 로그에 기록하고,
            throw new Exception("휴게소 완성 정보 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
        }

        return result;
    } // End selectServiceareaGroup Method
    
    /*
     * 전체 휴게소 수를 조회하기 위한 기능
     */
    @Override
    public int selectServiceareaGroupCount(ServiceareaGroup serviceareaGroup) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : 단일행 조회인 경우 객체, 다중행 조회인 경우 컬렉션
        // #{2} : selectOne, selectList 중 하나의 메서드를 호출한다.
        // #{3} : MyBatis Mapper에 정의한 기능 이름
        // #{4} : Where절을 쓸 경우 조건값을 담은 객체
        // #{5} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{6} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        int result = 0;

        try {
            result = sqlSession.selectOne("ServiceareaGroupMapper.selectServiceareaGroupCount", serviceareaGroup);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage()); // SQL문 자체의 에러인 경우 -> 에러 내용을 로그에 기록하고,
            throw new Exception("휴게소 목록 수 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
        }
        
        return result;
    } // End selectServiceareaGroupCount Method

    /** 코드 패턴에 따른 휴게소 완성 정보 목록 조회 기능 구현 */
    @Override
    public List<ServiceareaGroup> selectServiceareaGroupList(ServiceareaGroup serviceareaGroup) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : 단일행 조회인 경우 객체, 다중행 조회인 경우 컬렉션
        // #{2} : selectOne, selectList 중 하나의 메서드를 호출한다.
        // #{3} : MyBatis Mapper에 정의한 기능 이름
        // #{4} : Where절을 쓸 경우 조건값을 담은 객체
        // #{5} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{6} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        List<ServiceareaGroup> result = null;

        try {
            result = sqlSession.selectList("ServiceareaGroupMapper.selectServiceareaGroupList", serviceareaGroup);
            
            if(result == null) {
                // 조회 결과가 없을 경우 강제로 예외를 발생시킨다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("조회된 휴게소 완성 목록이 없습니다."); // 조회결과가 없을 경우 사용자에게 전달할 메시지를 설정한다.
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage()); // SQL문 자체의 에러인 경우 -> 에러 내용을 로그에 기록하고,
            throw new Exception("휴게소 완성 목록 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
        }

        return result;
    } // End selectServiceareaGroupList Method

    /** 코드 패턴에 따른 휴게소 완성 정보 수정 기능 구현 */
    @Override
    public void updateServiceareaGroup(ServiceareaGroup serviceareaGroup) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : insert, update, delete 중 수행하려는 기능에 대한 메서드
        // #{2} : MyBatis Mapper에 정의한 기능 이름
        // #{3} : 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
        // #{4} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{5} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        try {
            int result = sqlSession.update("ServiceareaGroupMapper.updateServiceareaGroup", serviceareaGroup);
         
            if(result == 0) {
                // 수행된 행이 없다면 강제로 예외를 발생시킨다.
                // -> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("수정된 휴게소 완성 정보가 없습니다.");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("휴게소 완성 정보 수정에 실패했습니다.");
        }
    } // End updateServiceareaGroup Method

    /** 코드 패턴에 따른 휴게소 완성 정보 삭제 기능 구현 */
    @Override
    public void deleteServiceareaGroup(ServiceareaGroup serviceareaGroup) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : insert, update, delete 중 수행하려는 기능에 대한 메서드
        // #{2} : MyBatis Mapper에 정의한 기능 이름
        // #{3} : 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
        // #{4} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{5} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        try {
            int result = sqlSession.delete("ServiceareaGroupMapper.deleteServiceareaGroup", serviceareaGroup);
         
            if(result == 0) {
                // 수행된 행이 없다면 강제로 예외를 발생시킨다.
                // -> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("삭제된 휴게소 완성 정보가 없습니다.");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("휴게소 완성 정보 삭제에 실패했습니다.");
        }
    } // End deleteServiceareaGroup method

    @Override
    public void updateServiceareaGroupByLikeUp(ServiceareaGroup serviceareaGroup) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : insert, update, delete 중 수행하려는 기능에 대한 메서드
        // #{2} : MyBatis Mapper에 정의한 기능 이름
        // #{3} : 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
        // #{4} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{5} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        try {
            int result = sqlSession.update("ServiceareaGroupMapper.updateServiceareaGroupByLikeUp", serviceareaGroup);
         
            if(result == 0) {
                // 수행된 행이 없다면 강제로 예외를 발생시킨다.
                // -> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("좋아요 증가 처리할 휴게소가 없습니다.");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("휴게소 좋아요 증가 처리에 실패했습니다.");
        }
    } // End updateServiceareaGroupByLikeUp Method

    @Override
    public void updateServiceareaGroupByLikeDown(ServiceareaGroup serviceareaGroup) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : insert, update, delete 중 수행하려는 기능에 대한 메서드
        // #{2} : MyBatis Mapper에 정의한 기능 이름
        // #{3} : 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
        // #{4} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{5} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        try {
            int result = sqlSession.update("ServiceareaGroupMapper.updateServiceareaGroupByLikeDown", serviceareaGroup);
         
            if(result == 0) {
                // 수행된 행이 없다면 강제로 예외를 발생시킨다.
                // -> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("좋아요 감소 처리할 휴게소가 없습니다.");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("휴게소 좋아요 감소 처리에 실패했습니다.");
        }
    } // End updateServiceareaGroupByLikeDown Method
    
    /**
     * 코드패턴에 따른 노선 개수를 구하는 기능
     */
    @Override
    public int selectRouteNameCount(ServiceareaGroup serviceareaGroup) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : 단일행 조회인 경우 객체, 다중행 조회인 경우 컬렉션
        // #{2} : selectOne, selectList 중 하나의 메서드를 호출한다.
        // #{3} : MyBatis Mapper에 정의한 기능 이름
        // #{4} : Where절을 쓸 경우 조건값을 담은 객체
        // #{5} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{6} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        int result = 0;

        try {
            result = sqlSession.selectOne("ServiceareaGroupMapper.selectRouteNameCount", serviceareaGroup);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage()); // SQL문 자체의 에러인 경우 -> 에러 내용을 로그에 기록하고,
            throw new Exception("노선 수를 노선명으로 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
        }
        
        return result;
    } // End selectRouteNameCount Method

    /**
     * 코드패턴에 따른 노선 목록을 노선명으로 조회하는 기능 구현
     */
    @Override
    public List<ServiceareaGroup> selectRouteNameList(ServiceareaGroup serviceareaGroup)
            throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : 단일행 조회인 경우 객체, 다중행 조회인 경우 컬렉션
        // #{2} : selectOne, selectList 중 하나의 메서드를 호출한다.
        // #{3} : MyBatis Mapper에 정의한 기능 이름
        // #{4} : Where절을 쓸 경우 조건값을 담은 객체
        // #{5} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{6} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        List<ServiceareaGroup> result = null;

        try {
            result = sqlSession.selectList("ServiceareaGroupMapper.selectRouteNameList", serviceareaGroup);
            
            if(result == null) {
                // 조회 결과가 없을 경우 강제로 예외를 발생시킨다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("노선명으로 조회된 노선 목록이 없습니다."); // 조회결과가 없을 경우 사용자에게 전달할 메시지를 설정한다.
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage()); // SQL문 자체의 에러인 경우 -> 에러 내용을 로그에 기록하고,
            throw new Exception("노선 목록을 노선명으로 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
        }

        return result;
    } // End selectRouteNameList Method

    /**
     * 휴게소 목록 수를 노선명으로 조회하는 기능 구현
     */
    @Override
    public int selectServiceareaGroupCountByRouteName(ServiceareaGroup serviceareaGroup) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : 단일행 조회인 경우 객체, 다중행 조회인 경우 컬렉션
        // #{2} : selectOne, selectList 중 하나의 메서드를 호출한다.
        // #{3} : MyBatis Mapper에 정의한 기능 이름
        // #{4} : Where절을 쓸 경우 조건값을 담은 객체
        // #{5} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{6} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        int result = 0;

        try {
            result = sqlSession.selectOne("ServiceareaGroupMapper.selectServiceareaGroupCountByRouteName", serviceareaGroup);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage()); // SQL문 자체의 에러인 경우 -> 에러 내용을 로그에 기록하고,
            throw new Exception("휴게소 목록 수를 노선명으로 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
        }
        
        return result;
    } // End selectServiceareaGroupCountByRouteName Method

    /**
     * 휴게소 완성 정보 목록을 노선명으로 조회하는 기능
     */
    @Override
    public List<ServiceareaGroup> selectServiceareaGroupListByRouteName(ServiceareaGroup serviceareaGroup)
            throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : 단일행 조회인 경우 객체, 다중행 조회인 경우 컬렉션
        // #{2} : selectOne, selectList 중 하나의 메서드를 호출한다.
        // #{3} : MyBatis Mapper에 정의한 기능 이름
        // #{4} : Where절을 쓸 경우 조건값을 담은 객체
        // #{5} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{6} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        List<ServiceareaGroup> result = null;

        try {
            result = sqlSession.selectList("ServiceareaGroupMapper.selectServiceareaGroupListByRouteName", serviceareaGroup);
            
            if(result == null) {
                // 조회 결과가 없을 경우 강제로 예외를 발생시킨다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("노선명으로 조회된 휴게소 완성 목록이 없습니다."); // 조회결과가 없을 경우 사용자에게 전달할 메시지를 설정한다.
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage()); // SQL문 자체의 에러인 경우 -> 에러 내용을 로그에 기록하고,
            throw new Exception("휴게소 완성 정보 목록을 노선명으로 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
        }

        return result;
    } // End selectServiceareaGroupListByRouteName Method
    
    /**
     * 휴게소 목록 수를 위치로 조회하는 기능 구현
     */
    @Override
    public int selectServiceareaGroupCountByPlaceId(ServiceareaGroup serviceareaGroup) throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : 단일행 조회인 경우 객체, 다중행 조회인 경우 컬렉션
        // #{2} : selectOne, selectList 중 하나의 메서드를 호출한다.
        // #{3} : MyBatis Mapper에 정의한 기능 이름
        // #{4} : Where절을 쓸 경우 조건값을 담은 객체
        // #{5} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{6} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        int result = 0;

        try {
            result = sqlSession.selectOne("ServiceareaGroupMapper.selectServiceareaGroupCountByPlaceId", serviceareaGroup);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage()); // SQL문 자체의 에러인 경우 -> 에러 내용을 로그에 기록하고,
            throw new Exception("휴게소 목록 수를 위치로 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
        }
        
        return result;
    } // End selectServiceareaGroupCountByPlaceId Method

    /**
     * 휴게소 완성 정보 목록을 노선명으로 조회하는 기능
     */
    @Override
    public List<ServiceareaGroup> selectServiceareaGroupListByPlaceId(ServiceareaGroup serviceareaGroup)
            throws Exception {
        /** MyBatis의 기능을 호출하는 경우 다음의 처리 패턴을 따른다. */
        // #{1} : 단일행 조회인 경우 객체, 다중행 조회인 경우 컬렉션
        // #{2} : selectOne, selectList 중 하나의 메서드를 호출한다.
        // #{3} : MyBatis Mapper에 정의한 기능 이름
        // #{4} : Where절을 쓸 경우 조건값을 담은 객체
        // #{5} : 처리 결과가 없는 경우 사용자에게 표시할 메시지
        // #{6} : 처리 도중 에러가 발생한 경우 사용자에게 표시할 메시지
        List<ServiceareaGroup> result = null;

        try {
            result = sqlSession.selectList("ServiceareaGroupMapper.selectServiceareaGroupListByPlaceId", serviceareaGroup);
            
            if(result == null) {
                // 조회 결과가 없을 경우 강제로 예외를 발생시킨다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("위치로 조회된 휴게소 완성 목록이 없습니다."); // 조회결과가 없을 경우 사용자에게 전달할 메시지를 설정한다.
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage()); // SQL문 자체의 에러인 경우 -> 에러 내용을 로그에 기록하고,
            throw new Exception("휴게소 완성 정보 목록을 위치로 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
        }

        return result;
    } // End selectServiceareaGroupListByPlaceId Method

}
