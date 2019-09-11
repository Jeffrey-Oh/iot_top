package project.spring.travel.service;

import project.spring.travel.model.MemberCertification;

/**
 * @fileName    : MemberCertificationService.java
 * @author      : 홍준성
 * @description : 회원 본인인증 CRUD Service 계층을 위한 인터페이스
 * @lastUpdate  : 2019. 4. 23.
 */
public interface MemberCertificationService {
    /**
     * 회원 본인인증 정보 저장 기능
     * @param MemberCertification - 저장할 회원 본인인증 정보가 담긴 Beans
     * @throws Exception
     */
    public void insertMemberCertification(MemberCertification memberCertification) throws Exception;
    
    /**
     * 회원 본인인증 조회 기능
     * @param MemberCertification - 조회할 회원 본인인증 일련번호가 담긴 Beans
     * @return int - 본인인증 성공 여부
     * @throws Exception
     */
    public int selectMemberCertification(MemberCertification memberCertification) throws Exception;
    
}
