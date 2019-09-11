package project.spring.travel.model;

/**
 * @fileName    : ServiceareaPs.java
 * @author      : 홍준성
 * @description : 휴게소의 편의시설 데이터가 들어갈 Beans Class
 * @lastUpdate  : 2019-04-22
 */
public class ServiceareaPs {
    private int servicearea_psId; // 휴게소 편의시설 일련번호
    private String stdRestCd; // 표준휴게소코드
    private String stdRestNm = null; // 표준휴게소명
    private String psCode; // 편의시설 코드
    private String psName; // 편의시설 명칭
    private String psDesc = null; // 상세내용
    private String stime; // 시작구간
    private String etime; // 종료구간
    private String redDtime; // 작성일시
    private String lsttmAltrDttm; // 최종수정일
    private String regDate; // 작성일
    private String editDate = null; // 수정일
    
    public int getServicearea_psId() {
        return servicearea_psId;
    }
    public void setServicearea_psId(int servicearea_psId) {
        this.servicearea_psId = servicearea_psId;
    }
    public String getStdRestCd() {
        return stdRestCd;
    }
    public void setStdRestCd(String stdRestCd) {
        this.stdRestCd = stdRestCd;
    }
    public String getStdRestNm() {
        return stdRestNm;
    }
    public void setStdRestNm(String stdRestNm) {
        this.stdRestNm = stdRestNm;
    }
    public String getPsCode() {
        return psCode;
    }
    public void setPsCode(String psCode) {
        this.psCode = psCode;
    }
    public String getPsName() {
        return psName;
    }
    public void setPsName(String psName) {
        this.psName = psName;
    }
    public String getPsDesc() {
        return psDesc;
    }
    public void setPsDesc(String psDesc) {
        this.psDesc = psDesc;
    }
    public String getStime() {
        return stime;
    }
    public void setStime(String stime) {
        this.stime = stime;
    }
    public String getEtime() {
        return etime;
    }
    public void setEtime(String etime) {
        this.etime = etime;
    }
    public String getRedDtime() {
        return redDtime;
    }
    public void setRedDtime(String redDtime) {
        this.redDtime = redDtime;
    }
    public String getLsttmAltrDttm() {
        return lsttmAltrDttm;
    }
    public void setLsttmAltrDttm(String lsttmAltrDttm) {
        this.lsttmAltrDttm = lsttmAltrDttm;
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
        return "ServiceareaPs [servicearea_psId=" + servicearea_psId + ", stdRestCd=" + stdRestCd + ", stdRestNm="
                + stdRestNm + ", psCode=" + psCode + ", psName=" + psName + ", psDesc=" + psDesc + ", stime=" + stime
                + ", etime=" + etime + ", redDtime=" + redDtime + ", lsttmAltrDttm=" + lsttmAltrDttm + ", regDate="
                + regDate + ", editDate=" + editDate + "]";
    }
    
}
