package project.spring.travel.model;

/**
 * @fileName    : MemberPolicy.java
 * @author      : 홍준성
 * @description : 프로그램에서 사용할 member_policy 데이터 구조 정의를 위한 JavaBeans Class
 * @lastUpdate  : 2019. 4. 3.
 */
public class MemberPolicy {
    private int policyId;
    private String agreementDoc = null;
    private String infoCollectionDoc = null;
    private String communityDoc = null;
    private String emailCollectionDoc = null;
    private String regDate;
    private String editDate = null;
    
    /** 기본 생성자 */
    public MemberPolicy() {
        super();
    }
    
    /**
     * 정책안내 member_policy 테이블의 Beans 생성자
     * @param policyId - 정책안내 일련번호
     * @param agreementDoc - 이용약관
     * @param infoCollectionDoc - 개인정보처리방침
     * @param communityDoc - 게시글관리규정
     * @param emailCollectionDoc - 이메일무단수집거부
     * @param regDate - 작성일
     * @param editDate - 수정일
     */
    public MemberPolicy(int policyId, String agreementDoc, String infoCollectionDoc, String communityDoc,
            String emailCollectionDoc, String regDate, String editDate) {
        super();
        this.policyId = policyId;
        this.agreementDoc = agreementDoc;
        this.infoCollectionDoc = infoCollectionDoc;
        this.communityDoc = communityDoc;
        this.emailCollectionDoc = emailCollectionDoc;
        this.regDate = regDate;
        this.editDate = editDate;
    }
    
    public int getPolicyId() {
        return policyId;
    }
    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }
    public String getAgreementDoc() {
        return agreementDoc;
    }
    public void setAgreementDoc(String agreementDoc) {
        this.agreementDoc = agreementDoc;
    }
    public String getInfoCollectionDoc() {
        return infoCollectionDoc;
    }
    public void setInfoCollectionDoc(String infoCollectionDoc) {
        this.infoCollectionDoc = infoCollectionDoc;
    }
    public String getCommunityDoc() {
        return communityDoc;
    }
    public void setCommunityDoc(String communityDoc) {
        this.communityDoc = communityDoc;
    }
    public String getEmailCollectionDoc() {
        return emailCollectionDoc;
    }
    public void setEmailCollectionDoc(String emailCollectionDoc) {
        this.emailCollectionDoc = emailCollectionDoc;
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
        return "MemberPolicy [policyId=" + policyId + ", agreementDoc=" + agreementDoc + ", infoCollectionDoc="
                + infoCollectionDoc + ", communityDoc=" + communityDoc + ", emailCollectionDoc=" + emailCollectionDoc
                + ", regDate=" + regDate + ", editDate=" + editDate + "]";
    }
    
}
