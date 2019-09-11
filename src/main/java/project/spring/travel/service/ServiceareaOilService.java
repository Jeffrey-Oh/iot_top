package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.ServiceareaOil;

/**
 * @fileName    : ServiceareaOilService.java
 * @author      : 홍준성
 * @description : 휴게소 유가정보 CRUD Service 계층을 위한 인터페이스
 * @lastUpdate  : 2019. 5. 02.
 */
public interface ServiceareaOilService {
    /**
     * 휴게소 유가정보 정보 저장 기능
     * @param ServiceareaOil - 저장할 휴게소 유가정보 정보가 담긴 Beans
     * @throws Exception
     */
    public void insertServiceareaOil(ServiceareaOil serviceareaOil) throws Exception;
    
    /**
     * 휴게소 유가정보 조회 기능
     * @param ServiceareaOil - 조회할 휴게소 유가정보 일련번호가 담긴 Beans
     * @return ServiceareaOil - 조회된 휴게소 유가정보 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaOil selectServiceareaOil(ServiceareaOil serviceareaOil) throws Exception;
    
    /**
     * 휴게소 유가정보 목록 조회 기능
     * @param ServiceareaOil
     * @return List<ServiceareaOil> - 조회된 휴게소 유가정보 목록이 담긴 List
     * @throws Exception
     */
    public List<ServiceareaOil> selectServiceareaOilList(ServiceareaOil serviceareaOil) throws Exception;
    
    /**
     * 휴게소 유가정보 정보 수정 기능
     * @param ServiceareaOil - 수정할 휴게소 유가정보 정보가 담긴 Beans
     * @throws Exception
     */
    public void updateServiceareaOil(ServiceareaOil serviceareaOil) throws Exception;
    
    /**
     * 휴게소 유가정보 정보 삭제 기능
     * @param ServiceareaOil - 삭제할 휴게소 유가정보 일련번호가 담긴 Beans
     * @throws Exception
     */
    public void deleteServiceareaOil(ServiceareaOil serviceareaOil) throws Exception;
    
    /**
     * 휴게소 유가정보를 휴게소이름으로 조회 기능
     * @param ServiceareaOil - 조회할 휴게소이름이 담긴 Beans
     * @return ServiceareaOil - 조회된 휴게소 유가정보 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaOil selectServiceareaOilByServiceAreaName(ServiceareaOil serviceareaOil) throws Exception;
    
}
