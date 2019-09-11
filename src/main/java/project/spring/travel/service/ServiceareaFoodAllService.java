package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.ServiceareaFoodAll;

/**
 * @fileName    : ServiceareaFoodAllService.java
 * @author      : 홍준성
 * @description : 휴게소 전체 푸드메뉴 CRUD Service 계층을 위한 인터페이스
 * @lastUpdate  : 2019. 5. 04.
 */
public interface ServiceareaFoodAllService {
    /**
     * 휴게소 전체 푸드메뉴 정보 저장 기능
     * @param ServiceareaFoodAll - 저장할 휴게소 전체 푸드메뉴 정보가 담긴 Beans
     * @throws Exception
     */
    public void insertServiceareaFoodAll(ServiceareaFoodAll serviceareaFoodAll) throws Exception;
    
    /**
     * 휴게소 전체 푸드메뉴 조회 기능
     * @param ServiceareaFoodAll - 조회할 휴게소 전체 푸드메뉴 일련번호가 담긴 Beans
     * @return ServiceareaFoodAll - 조회된 휴게소 전체 푸드메뉴 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaFoodAll selectServiceareaFoodAll(ServiceareaFoodAll serviceareaFoodAll) throws Exception;
    
    /**
     * 휴게소 전체 푸드메뉴 목록 조회 기능
     * @param ServiceareaFoodAll
     * @return List<ServiceareaFoodAll> - 조회된 휴게소 전체 푸드메뉴 목록이 담긴 List
     * @throws Exception
     */
    public List<ServiceareaFoodAll> selectServiceareaFoodAllList(ServiceareaFoodAll serviceareaFoodAll) throws Exception;
    
    /**
     * 휴게소 전체 푸드메뉴 정보 수정 기능
     * @param ServiceareaFoodAll - 수정할 휴게소 전체 푸드메뉴 정보가 담긴 Beans
     * @throws Exception
     */
    public void updateServiceareaFoodAll(ServiceareaFoodAll serviceareaFoodAll) throws Exception;
    
    /**
     * 휴게소 전체 푸드메뉴 정보 삭제 기능
     * @param ServiceareaFoodAll - 삭제할 휴게소 전체 푸드메뉴 일련번호가 담긴 Beans
     * @throws Exception
     */
    public void deleteServiceareaFoodAll(ServiceareaFoodAll serviceareaFoodAll) throws Exception;
    
    /**
     * 휴게소 전체 푸드메뉴 목록을 휴게소이름으로 조회 기능
     * @param ServiceareaFoodAll
     * @return List<ServiceareaFoodAll> - 조회된 휴게소 전체 푸드메뉴 목록이 담긴 List
     * @throws Exception
     */
    public List<ServiceareaFoodAll> selectServiceareaFoodAllListByStdRestNm(ServiceareaFoodAll serviceareaFoodAll) throws Exception;
    
}
