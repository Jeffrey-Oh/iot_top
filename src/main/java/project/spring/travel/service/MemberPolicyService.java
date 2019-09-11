package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.MemberPolicy;

/**
 * ===== 정책안내와 관련된 요구사항에 대한 기능을 명시하기 위한 인터페이스 =====
 * @fileName    : MemberPolicyService.java
 * @author      : 홍준성
 * @description : 정책안내 관리 기능을 제공하기 위한 Service 계층
 * @lastUpdate  : 2019. 4. 3.
 */
public interface MemberPolicyService {
    /**
     * 정책안내 등록하기
     * @param policy - 저장할 정보를 담고 있는 Beans
     * @throws Exception
     */
    public void insertPolicy(MemberPolicy policy) throws Exception;
    
    /**
     * 정책안내 수정하기
     * @param policy - 수정할 정보를 담고 있는 Beans
     * @throws Exception
     */
    public void updatePolicy(MemberPolicy policy) throws Exception;
    
    /**
     * 정책안내 삭제하기
     * @param policy - 삭제할 정책안내의 일련번호를 담고 있는 Beans
     * @throws Exception
     */
    public void deletePolicy(MemberPolicy policy) throws Exception;
    
    /**
     * 정책안내 상세 조회
     * @param policy - 조회할 정책안내의 일련번호를 담고 있는 Beans
     * @return MemberPolicy - 조회된 데이터가 저장된 Beans
     * @throws Exception
     */
    public MemberPolicy selectPolicy(MemberPolicy policy) throws Exception;
    
    /**
     * 정책안내 목록 조회
     * @return List<MemeberPolicy> - 조회 결과에 대한 컬렉션
     * @throws Exception
     */
    public List<MemberPolicy> selectPolicyList() throws Exception;
}
