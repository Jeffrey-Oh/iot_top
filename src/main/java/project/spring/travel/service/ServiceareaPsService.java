package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.ServiceareaPs;

/**
 * @fileName    : ServiceareaPsService.java
 * @author      : 홍준성
 * @description : 휴게소 편의시설 CRUD Service 계층을 위한 인터페이스
 * @lastUpdate  : 2019. 4. 24.
 */
public interface ServiceareaPsService {
    /**
     * 휴게소 편의시설 정보 저장 기능
     * @param ServiceareaPs - 저장할 휴게소 편의시설 정보가 담긴 Beans
     * @throws Exception
     */
    public void insertServiceareaPs(ServiceareaPs serviceareaPs) throws Exception;
    
    /**
     * 휴게소 편의시설 조회 기능
     * @param ServiceareaPs - 조회할 휴게소 편의시설 일련번호가 담긴 Beans
     * @return ServiceareaPs - 조회된 휴게소 편의시설 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaPs selectServiceareaPs(ServiceareaPs serviceareaPs) throws Exception;
    
    /**
     * 휴게소 편의시설 목록 조회 기능
     * @param ServiceareaPs
     * @return List<ServiceareaPs> - 조회된 휴게소 편의시설 목록이 담긴 List
     * @throws Exception
     */
    public List<ServiceareaPs> selectServiceareaPsList(ServiceareaPs serviceareaPs) throws Exception;
    
    /**
     * 휴게소 편의시설 정보 수정 기능
     * @param ServiceareaPs - 수정할 휴게소 편의시설 정보가 담긴 Beans
     * @throws Exception
     */
    public void updateServiceareaPs(ServiceareaPs serviceareaPs) throws Exception;
    
    /**
     * 휴게소 편의시설 정보 삭제 기능
     * @param ServiceareaPs - 삭제할 휴게소 편의시설 일련번호가 담긴 Beans
     * @throws Exception
     */
    public void deleteServiceareaPs(ServiceareaPs serviceareaPs) throws Exception;
    
    /**
     * 휴게소 편의시설 목록을 휴게소이름으로 조회 기능
     * @param ServiceareaPs - 휴게소이름이 담긴 Beans
     * @return List<ServiceareaPs> - 조회된 휴게소 편의시설 목록이 담긴 List
     * @throws Exception
     */
    public List<ServiceareaPs> selectServiceareaPsListByStdRestNm(ServiceareaPs serviceareaPs) throws Exception;
    
}
