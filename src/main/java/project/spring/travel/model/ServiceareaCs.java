package project.spring.travel.model;

/**
 * @fileName    : ServiceareaCs.java
 * @author      : 홍준성
 * @description : 휴게소의 전기차 충전소 데이터가 들어갈 Beans Class
 * @lastUpdate  : 2019-04-22
 */
public class ServiceareaCs {
    private int servicearea_csId; // 전기차 충전소 일련번호
    private String chgerId; // 충전기ID
    private String statId; //충전소ID
    private int stat; // 충전기상태 (1. 통신이상 2. 충전대기 3. 충전중 4. 운영중지 5. 점검중 )
    private String chgerType; //충전기타입 (01:DC차데모,03: DC차데모+AC3상, 06: DC차데모+AC3상 +DC콤보)
    private String lat; // 위도
    private String lng; // 경도
    private String addrDoro; // 주소
    private String statNm; // 충전소명
    private String useTime; // 충전기 이용시간
    private String regDate; // 작성일
    private String editDate = null; // 수정일
    
    public int getServicearea_csId() {
        return servicearea_csId;
    }
    public void setServicearea_csId(int servicearea_csId) {
        this.servicearea_csId = servicearea_csId;
    }
    public String getChgerId() {
        return chgerId;
    }
    public void setChgerId(String chgerId) {
        this.chgerId = chgerId;
    }
    public String getStatId() {
        return statId;
    }
    public void setStatId(String statId) {
        this.statId = statId;
    }
    public int getStat() {
        return stat;
    }
    public void setStat(int stat) {
        this.stat = stat;
    }
    public String getChgerType() {
        return chgerType;
    }
    public void setChgerType(String chgerType) {
        this.chgerType = chgerType;
    }
    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getLng() {
        return lng;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }
    public String getAddrDoro() {
        return addrDoro;
    }
    public void setAddrDoro(String addrDoro) {
        this.addrDoro = addrDoro;
    }
    public String getStatNm() {
        return statNm;
    }
    public void setStatNm(String statNm) {
        this.statNm = statNm;
    }
    public String getUseTime() {
        return useTime;
    }
    public void setUseTime(String useTime) {
        this.useTime = useTime;
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
        return "ServiceareaCs [servicearea_csId=" + servicearea_csId + ", chgerId=" + chgerId + ", statId=" + statId
                + ", stat=" + stat + ", chgerType=" + chgerType + ", lat=" + lat + ", lng=" + lng + ", addrDoro="
                + addrDoro + ", statNm=" + statNm + ", useTime=" + useTime + ", regDate=" + regDate + ", editDate="
                + editDate + "]";
    }

}