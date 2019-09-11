package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.ServiceareaPlace;

/**
 * @fileName    : ServiceareaPlaceService.java
 * @author      : 홍준성
 * @description : 휴게소 위치 CRUD Service 계층을 위한 인터페이스
 * @lastUpdate  : 2019. 5. 02.
 */
public interface ServiceareaPlaceService {
    /**
     * 휴게소 위치 정보 저장 기능
     * @param ServiceareaPlace - 저장할 휴게소 위치 정보가 담긴 Beans
     * @throws Exception
     */
    public void insertServiceareaPlace(ServiceareaPlace serviceareaPlace) throws Exception;
    
    /**
     * 휴게소 위치 조회 기능
     * @param ServiceareaPlace - 조회할 휴게소 위치 일련번호가 담긴 Beans
     * @return ServiceareaPlace - 조회된 휴게소 위치 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaPlace selectServiceareaPlace(ServiceareaPlace serviceareaPlace) throws Exception;
    
    /**
     * 휴게소 위치 목록 조회 기능
     * @param ServiceareaPlace
     * @return List<ServiceareaPlace> - 조회된 휴게소 위치 목록이 담긴 List
     * @throws Exception
     */
    public List<ServiceareaPlace> selectServiceareaPlaceList(ServiceareaPlace serviceareaPlace) throws Exception;
    
    /**
     * 휴게소 위치 정보 수정 기능
     * @param ServiceareaPlace - 수정할 휴게소 위치 정보가 담긴 Beans
     * @throws Exception
     */
    public void updateServiceareaPlace(ServiceareaPlace serviceareaPlace) throws Exception;
    
    /**
     * 휴게소 위치 정보 삭제 기능
     * @param ServiceareaPlace - 삭제할 휴게소 위치 일련번호가 담긴 Beans
     * @throws Exception
     */
    public void deleteServiceareaPlace(ServiceareaPlace serviceareaPlace) throws Exception;
    
    /**
     * 휴게소 위치를 휴게소이름으로 조회 기능
     * @param ServiceareaPlace - 조회할 휴게소이름이 담긴 Beans
     * @return ServiceareaPlace - 조회된 휴게소 위치 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaPlace selectServiceareaPlaceByQuery(ServiceareaPlace serviceareaPlace) throws Exception;
    
}
