package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.ServiceareaTheme;

/**
 * @fileName    : ServiceareaThemeService.java
 * @author      : 홍준성
 * @description : 휴게소 테마 CRUD Service 계층을 위한 인터페이스
 * @lastUpdate  : 2019. 5. 02.
 */
public interface ServiceareaThemeService {
    /**
     * 휴게소 테마 정보 저장 기능
     * @param ServiceareaTheme - 저장할 휴게소 테마 정보가 담긴 Beans
     * @throws Exception
     */
    public void insertServiceareaTheme(ServiceareaTheme serviceareaTheme) throws Exception;
    
    /**
     * 휴게소 테마 조회 기능
     * @param ServiceareaTheme - 조회할 휴게소 테마 일련번호가 담긴 Beans
     * @return ServiceareaTheme - 조회된 휴게소 테마 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaTheme selectServiceareaTheme(ServiceareaTheme serviceareaTheme) throws Exception;
    
    /**
     * 휴게소 테마 목록 조회 기능
     * @param ServiceareaTheme
     * @return List<ServiceareaTheme> - 조회된 휴게소 테마 목록이 담긴 List
     * @throws Exception
     */
    public List<ServiceareaTheme> selectServiceareaThemeList(ServiceareaTheme serviceareaTheme) throws Exception;
    
    /**
     * 휴게소 테마 정보 수정 기능
     * @param ServiceareaTheme - 수정할 휴게소 테마 정보가 담긴 Beans
     * @throws Exception
     */
    public void updateServiceareaTheme(ServiceareaTheme serviceareaTheme) throws Exception;
    
    /**
     * 휴게소 테마 정보 삭제 기능
     * @param ServiceareaTheme - 삭제할 휴게소 테마 일련번호가 담긴 Beans
     * @throws Exception
     */
    public void deleteServiceareaTheme(ServiceareaTheme serviceareaTheme) throws Exception;
    
    /**
     * 휴게소 테마를 휴게소이름으로 조회 기능
     * @param ServiceareaTheme - 조회할 휴게소이름이 담긴 Beans
     * @return ServiceareaTheme - 조회된 휴게소 테마 정보가 담긴 Beans
     * @throws Exception
     */
    public ServiceareaTheme selectServiceareaThemeByStdRestNm(ServiceareaTheme serviceareaTheme) throws Exception;
    
}
