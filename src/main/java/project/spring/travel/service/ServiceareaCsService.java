package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.ServiceareaCs;

/**
 * @fileName    : ServiceareaCsService.java
 * @author      : 홍준성
 * @description : 휴게소 전기차 충전소 CRUD Service 계층을 위한 인터페이스
 * @lastUpdate  : 2019. 4. 27.
 */
public interface ServiceareaCsService {
    /**
     * 휴게소 전기차 충전소 정보 저장 기능
     * @param ServiceareaCs - 저장할 휴게소 전기차 충전소 정보가 담긴 Beans
     * @throws Exception
     */
    public void insertServiceareaCs(ServiceareaCs serviceareaCs) throws Exception;
    
    /**
     * 휴게소 전기차 충전소 조회 기능
     * @param ServiceareaCs - 조회할 휴게소 전기차 충전소 일련번호가 담긴 Beans
     * @return ServiceareaCs - 조회된 휴게소 전기차 충전소 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaCs selectServiceareaCs(ServiceareaCs serviceareaCs) throws Exception;
    
    /**
     * 휴게소 전기차 충전소 목록 조회 기능
     * @param ServiceareaCs
     * @return List<ServiceareaCs> - 조회된 휴게소 전기차 충전소 목록이 담긴 List
     * @throws Exception
     */
    public List<ServiceareaCs> selectServiceareaCsList(ServiceareaCs serviceareaCs) throws Exception;
    
    /**
     * 휴게소 전기차 충전소 정보 수정 기능
     * @param ServiceareaCs - 수정할 휴게소 전기차 충전소 정보가 담긴 Beans
     * @throws Exception
     */
    public void updateServiceareaCs(ServiceareaCs serviceareaCs) throws Exception;
    
    /**
     * 휴게소 전기차 충전소 정보 삭제 기능
     * @param ServiceareaCs - 삭제할 휴게소 전기차 충전소 일련번호가 담긴 Beans
     * @throws Exception
     */
    public void deleteServiceareaCs(ServiceareaCs serviceareaCs) throws Exception;
    
    /**
     * 휴게소 전기차 충전소를 휴게소 이름으로 조회 기능
     * @param ServiceareaCs - 조회할 휴게소 전기차 충전소 일련번호가 담긴 Beans
     * @return ServiceareaCs - 조회된 휴게소 전기차 충전소 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaCs selectServiceareaCsByStatNm(ServiceareaCs serviceareaCs) throws Exception;
    
}
