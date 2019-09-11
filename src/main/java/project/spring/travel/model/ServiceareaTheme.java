package project.spring.travel.model;

/**
 * @fileName    : ServiceareaTheme.java
 * @author      : 홍준성
 * @description : 휴게소의 테마 데이터가 들어갈 Beans Class
 * @lastUpdate  : 2019-04-22
 */
public class ServiceareaTheme {
    private int servicearea_themeId; // 휴게소 테마 일련번호
    private String stdRestCd; // 표준휴게소코드
    private String stdRestNm; // 표준휴게소명
    private String itemNm; // 테마명
    private String detail; // 테마상세내역
    private String regDtime; // 작성일시
    private String lsttmAltrDttm; // 최종수정일
    private String regDate; // 작성일
    private String editDate = null; // 수정일
    
    public int getServicearea_themeId() {
        return servicearea_themeId;
    }
    public void setServicearea_themeId(int servicearea_themeId) {
        this.servicearea_themeId = servicearea_themeId;
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
    public String getItemNm() {
        return itemNm;
    }
    public void setItemNm(String itemNm) {
        this.itemNm = itemNm;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getRegDtime() {
        return regDtime;
    }
    public void setRegDtime(String regDtime) {
        this.regDtime = regDtime;
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
        return "ServiceareaTheme [servicearea_themeId=" + servicearea_themeId + ", stdRestCd=" + stdRestCd
                + ", stdRestNm=" + stdRestNm + ", itemNm=" + itemNm + ", detail=" + detail + ", regDtime=" + regDtime
                + ", lsttmAltrDttm=" + lsttmAltrDttm + ", regDate=" + regDate + ", editDate=" + editDate + "]";
    }
    
}
