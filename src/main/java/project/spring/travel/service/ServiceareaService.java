package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.Servicearea;

/**
 * @fileName    : ServiceareaService.java
 * @author      : 홍준성
 * @description : 휴게소 기본 CRUD Service 계층을 위한 인터페이스
 * @lastUpdate  : 2019. 5. 01.
 */
public interface ServiceareaService {
    /**
     * 휴게소 정보 저장 기능
     * @param servicearea - 저장할 휴게소 정보가 담긴 Beans
     * @throws Exception
     */
    public void insertServicearea(Servicearea servicearea) throws Exception;
    
    /**
     * 휴게소 조회 기능
     * @param servicearea - 조회할 휴게소 일련번호가 담긴 Beans
     * @return Servicearea - 조회된 휴게소 정보가 담긴 Beans
     * @throws Exception
     */
    public Servicearea selectServicearea(Servicearea servicearea) throws Exception;
    
    /**
     * 휴게소 목록 조회 기능
     * @param servicearea
     * @return List<Servicearea> - 조회된 휴게소 목록이 담긴 List
     * @throws Exception
     */
    public List<Servicearea> selectServiceareaList(Servicearea servicearea) throws Exception;
    
    /**
     * 휴게소 정보 수정 기능
     * @param servicearea - 수정할 휴게소 정보가 담긴 Beans
     * @throws Exception
     */
    public void updateServicearea(Servicearea servicearea) throws Exception;
    
    /**
     * 휴게소 정보 삭제 기능
     * @param servicearea - 삭제할 휴게소 일련번호가 담긴 Beans
     * @throws Exception
     */
    public void deleteServicearea(Servicearea servicearea) throws Exception;
    
    /**
     * 휴게소 정보 중 비어있는 위치 수정 기능
     * @param servicearea - 수정할 휴게소 위치 정보가 담긴 Beans
     * @throws Exception
     */
    public void updateServiceareaByServiceareaCsOrServiceareaPlace(Servicearea servicearea) throws Exception;
    
    /**
     * 휴게소 조회 기능
     * @param servicearea - 조회할 휴게소 일련번호가 담긴 Beans
     * @return Servicearea - 조회된 휴게소 정보가 담긴 Beans
     * @throws Exception
     */
    public Servicearea selectServiceareaByUnitName(Servicearea servicearea) throws Exception;
    
}
