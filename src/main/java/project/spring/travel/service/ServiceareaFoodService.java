package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.ServiceareaFood;

/**
 * @fileName    : ServiceareaFoodService.java
 * @author      : 홍준성
 * @description : 휴게소 대표메뉴 CRUD Service 계층을 위한 인터페이스
 * @lastUpdate  : 2019. 5. 02.
 */
public interface ServiceareaFoodService {
    /**
     * 휴게소 대표메뉴 정보 저장 기능
     * @param ServiceareaFood - 저장할 휴게소 대표메뉴 정보가 담긴 Beans
     * @throws Exception
     */
    public void insertServiceareaFood(ServiceareaFood serviceareaFood) throws Exception;
    
    /**
     * 휴게소 대표메뉴 조회 기능
     * @param ServiceareaFood - 조회할 휴게소 대표메뉴 일련번호가 담긴 Beans
     * @return ServiceareaFood - 조회된 휴게소 대표메뉴 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaFood selectServiceareaFood(ServiceareaFood serviceareaFood) throws Exception;
    
    /**
     * 휴게소 대표메뉴 목록 조회 기능
     * @param ServiceareaFood
     * @return List<ServiceareaFood> - 조회된 휴게소 대표메뉴 목록이 담긴 List
     * @throws Exception
     */
    public List<ServiceareaFood> selectServiceareaFoodList(ServiceareaFood serviceareaFood) throws Exception;
    
    /**
     * 휴게소 대표메뉴 정보 수정 기능
     * @param ServiceareaFood - 수정할 휴게소 대표메뉴 정보가 담긴 Beans
     * @throws Exception
     */
    public void updateServiceareaFood(ServiceareaFood serviceareaFood) throws Exception;
    
    /**
     * 휴게소 대표메뉴 정보 삭제 기능
     * @param ServiceareaFood - 삭제할 휴게소 대표메뉴 일련번호가 담긴 Beans
     * @throws Exception
     */
    public void deleteServiceareaFood(ServiceareaFood serviceareaFood) throws Exception;
    
    /**
     * 휴게소 대표메뉴를 휴게소이름으로 조회 기능
     * @param ServiceareaFood - 조회할 휴게소 이름이 담긴 Beans
     * @return ServiceareaFood - 조회된 휴게소 대표메뉴 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaFood selectServiceareaFoodByServiceAreaName(ServiceareaFood serviceareaFood) throws Exception;
    
}
