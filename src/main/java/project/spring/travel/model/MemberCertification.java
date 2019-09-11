package project.spring.travel.model;

/**
 * @fileName    : MemberCertification.java
 * @author      : 홍준성
 * @description : 회원 본인인증 테이블을 위한 JavaBeans
 * @lastUpdate  : 2019. 4. 23.
 */
public class MemberCertification {
    private int member_certificationId; // 회원 본인인증 일련번호
    private String certificationNum; // 인증번호
    private String email; // 인증번호 발급 이메일
    private String regDate; // 작성일
    private String editDate; // 수정일
    
    public int getMember_certificationId() {
        return member_certificationId;
    }
    public void setMember_certificationId(int member_certificationId) {
        this.member_certificationId = member_certificationId;
    }
    public String getCertificationNum() {
        return certificationNum;
    }
    public void setCertificationNum(String certificationNum) {
        this.certificationNum = certificationNum;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
        return "MemberCertification [member_certificationId=" + member_certificationId + ", certificationNum="
                + certificationNum + ", email=" + email + ", regDate=" + regDate + ", editDate=" + editDate + "]";
    }
    
}
