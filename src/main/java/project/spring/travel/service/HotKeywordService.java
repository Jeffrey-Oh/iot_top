package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.HotKeyword;

/**
 * @fileName    : HotKeywordService.java
 * @author      : 홍준성
 * @description : 인기검색어 CRUD Service 계층을 위한 인터페이스
 * @lastUpdate  : 2019. 5. 10.
 */
public interface HotKeywordService {
    /**
     * 인기검색어 정보 저장 기능
     * @param HotKeyword - 저장할 인기검색어 정보가 담긴 Beans
     * @throws Exception
     */
    public void insertHotKeyword(HotKeyword hotKeyword) throws Exception;
    
    /**
     * 전체 인기검색어 목록 수를 조회하기 위한 기능
     * @param HotKeyword
     * @return int - 조회 결과
     * @throws Exception
     */
    public int selectHotKeywordCount(HotKeyword hotKeyword) throws Exception;
    
    /**
     * 인기검색어 목록 조회 기능
     * @return List<HotKeyword> - 조회된 인기검색어 목록이 담긴 List
     * @throws Exception
     */
    public List<HotKeyword> selectHotKeywordList() throws Exception;
    
}
