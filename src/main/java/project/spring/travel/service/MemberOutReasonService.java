package project.spring.travel.service;

import project.spring.travel.model.MemberOutReason;

/**
 * @fileName    : MemberOutReasonService.java
 * @author      : 홍준성
 * @description : 회원 탈퇴 사유를 위한 Service 계층의 Interface
 * @lastUpdate  : 2019. 4. 9.
 */
public interface MemberOutReasonService {
    /**
     * 회원 탈퇴 사유를 저장
     * @param memberOutReason - 회원 탈퇴 사유를 담은 Beans
     * @throws Exception
     */
    public void insertMemberOutReason(MemberOutReason memberOutReason) throws Exception;
}
