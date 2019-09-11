package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.ServiceareaGroup;

/**
 * @fileName    : ServiceareaGroupService.java
 * @author      : 홍준성
 * @description : 휴게소 완성 CRUD Service 계층을 위한 인터페이스
 * @lastUpdate  : 2019. 5. 06.
 */
public interface ServiceareaGroupService {
    /**
     * 휴게소 완성 정보 저장 기능
     * @param ServiceareaGroup - 저장할 휴게소 완성 정보가 담긴 Beans
     * @throws Exception
     */
    public void insertServiceareaGroup(ServiceareaGroup serviceareaGroup) throws Exception;
    
    /**
     * 휴게소 완성 조회 기능
     * @param ServiceareaGroup - 조회할 휴게소 완성 일련번호가 담긴 Beans
     * @return ServiceareaGroup - 조회된 휴게소 완성 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaGroup selectServiceareaGroup(ServiceareaGroup serviceareaGroup) throws Exception;
    
    /**
     * 전체 휴게소 완성 목록 수를 조회하기 위한 기능
     * @param ServiceareaGroup
     * @return int - 조회 결과
     * @throws Exception
     */
    public int selectServiceareaGroupCount(ServiceareaGroup serviceareaGroup) throws Exception;
    
    /**
     * 휴게소 완성 목록 조회 기능
     * @param ServiceareaGroup
     * @return List<ServiceareaGroup> - 조회된 휴게소 완성 목록이 담긴 List
     * @throws Exception
     */
    public List<ServiceareaGroup> selectServiceareaGroupList(ServiceareaGroup serviceareaGroup) throws Exception;
    
    /**
     * 휴게소 완성 정보 수정 기능
     * @param ServiceareaGroup - 수정할 휴게소 완성 정보가 담긴 Beans
     * @throws Exception
     */
    public void updateServiceareaGroup(ServiceareaGroup serviceareaGroup) throws Exception;
    
    /**
     * 휴게소 완성 정보 삭제 기능
     * @param ServiceareaGroup - 삭제할 휴게소 완성 일련번호가 담긴 Beans
     * @throws Exception
     */
    public void deleteServiceareaGroup(ServiceareaGroup serviceareaGroup) throws Exception;
    
    /**
     * 휴게소 게시물 좋아요 증가 기능
     * @param ServiceareaGroup - 수정할 휴게소 완성 정보가 담긴 Beans
     * @throws Exception
     */
    public void updateServiceareaGroupByLikeUp(ServiceareaGroup serviceareaGroup) throws Exception;
    
    /**
     * 휴게소 게시물 좋아요 감소 기능
     * @param ServiceareaGroup - 수정할 휴게소 완성 정보가 담긴 Beans
     * @throws Exception
     */
    public void updateServiceareaGroupByLikeDown(ServiceareaGroup serviceareaGroup) throws Exception;
    
    /**
     * 전체 노선 수를 노선명으로 조회하기 위한 기능
     * @param ServiceareaGroup
     * @return int - 조회 결과
     * @throws Exception
     */
    public int selectRouteNameCount(ServiceareaGroup serviceareaGroup) throws Exception;
    
    /**
     * 노선 목록을 노선명으로 조회 기능
     * @param ServiceareaGroup - 노선명이 담긴 beans
     * @return List<ServiceareaGroup> - 조회된 휴게소 완성 목록이 담긴 List
     * @throws Exception
     */
    public List<ServiceareaGroup> selectRouteNameList(ServiceareaGroup serviceareaGroup) throws Exception;
    
    /**
     * 전체 휴게소 완성 목록 수를 노선명으로 조회하기 위한 기능
     * @param ServiceareaGroup
     * @return int - 조회 결과
     * @throws Exception
     */
    public int selectServiceareaGroupCountByRouteName(ServiceareaGroup serviceareaGroup) throws Exception;
    
    /**
     * 휴게소 완성 목록을 노선명으로 조회 기능
     * @param ServiceareaGroup - 노선명이 담긴 beans
     * @return List<ServiceareaGroup> - 조회된 휴게소 완성 목록이 담긴 List
     * @throws Exception
     */
    public List<ServiceareaGroup> selectServiceareaGroupListByRouteName(ServiceareaGroup serviceareaGroup) throws Exception;
    
    /**
     * 전체 휴게소 완성 목록 수를 위치로 조회하기 위한 기능
     * @param ServiceareaGroup
     * @return int - 조회 결과
     * @throws Exception
     */
    public int selectServiceareaGroupCountByPlaceId(ServiceareaGroup serviceareaGroup) throws Exception;
    
    /**
     * 휴게소 완성 목록을 위치으로 조회 기능
     * @param ServiceareaGroup - 위치 값이 담긴 beans
     * @return List<ServiceareaGroup> - 조회된 휴게소 완성 목록이 담긴 List
     * @throws Exception
     */
    public List<ServiceareaGroup> selectServiceareaGroupListByPlaceId(ServiceareaGroup serviceareaGroup) throws Exception;
    
}
