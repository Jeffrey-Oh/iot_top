package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.ServiceareaImage;

/**
 * @fileName    : ServiceareaImageService.java
 * @author      : 홍준성
 * @description : 휴게소 이미지 CRUD Service 계층을 위한 인터페이스
 * @lastUpdate  : 2019. 4. 30.
 */
public interface ServiceareaImageService {
    /**
     * 휴게소 이미지 정보 저장 기능
     * @param ServiceareaImage - 저장할 휴게소 이미지 정보가 담긴 Beans
     * @throws Exception
     */
    public void insertServiceareaImage(ServiceareaImage serviceareaImage) throws Exception;
    
    /**
     * 휴게소 이미지 조회 기능
     * @param ServiceareaImage - 조회할 휴게소 이미지 일련번호가 담긴 Beans
     * @return ServiceareaImage - 조회된 휴게소 이미지 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaImage selectServiceareaImage(ServiceareaImage serviceareaImage) throws Exception;
    
    /**
     * 휴게소 이미지 목록 조회 기능
     * @param ServiceareaImage
     * @return List<ServiceareaImage> - 조회된 휴게소 이미지 목록이 담긴 List
     * @throws Exception
     */
    public List<ServiceareaImage> selectServiceareaImageList(ServiceareaImage serviceareaImage) throws Exception;
    
    /**
     * 휴게소 이미지 정보 수정 기능
     * @param ServiceareaImage - 수정할 휴게소 이미지 정보가 담긴 Beans
     * @throws Exception
     */
    public void updateServiceareaImage(ServiceareaImage serviceareaImage) throws Exception;
    
    /**
     * 휴게소 이미지 정보 삭제 기능
     * @param ServiceareaImage - 삭제할 휴게소 이미지 일련번호가 담긴 Beans
     * @throws Exception
     */
    public void deleteServiceareaImage(ServiceareaImage serviceareaImage) throws Exception;
    
    /**
     * 휴게소 이미지를 질의어로 조회 기능
     * @param ServiceareaImage - 조회할 휴게소 이미지 일련번호가 담긴 Beans
     * @return ServiceareaImage - 조회된 휴게소 이미지 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaImage selectServiceareaImageByQuery(ServiceareaImage serviceareaImage) throws Exception;
    
}
