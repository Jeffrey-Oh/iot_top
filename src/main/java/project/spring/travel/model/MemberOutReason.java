package project.spring.travel.model;

/**
 * @fileName    : MemberOutReason.java
 * @author      : 홍준성
 * @description : 회원 탈퇴 사유를 담을 Beans
 * @lastUpdate  : 2019. 4. 9.
 */
public class MemberOutReason {
    private int memberOutReasonId; // 일련번호
    private String reason; // 탈퇴 사유
    private String reasonEtc = null; // 탈퇴 기타 사유 내용
    private String regDate; // 탈퇴 사유 작성일
    private String editDate = null; // 탈퇴 사유 수정일
    
    public int getMemberOutReasonId() {
        return memberOutReasonId;
    }
    public void setMemberOutReasonId(int memberOutReasonId) {
        this.memberOutReasonId = memberOutReasonId;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getReasonEtc() {
        return reasonEtc;
    }
    public void setReasonEtc(String reasonEtc) {
        this.reasonEtc = reasonEtc;
    }
    public String getRegDate() {
        return regDate;
    }
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
    public String getEditDate() {
        return editDate;
    }
    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }
    
    @Override
    public String toString() {
        return "MemberOutReason [memberOutReasonId=" + memberOutReasonId + ", reason=" + reason + ", reasonEtc="
                + reasonEtc + ", regDate=" + regDate + ", editDate=" + editDate + "]";
    }
    
}
