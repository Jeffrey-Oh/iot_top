package project.spring.travel.model;

/**
 * @fileName    : ServiceareaFood2.java
 * @author      : 홍준성
 * @description : 휴게소의 전체 푸드메뉴 데이터가 들어갈 Beans Class
 * @lastUpdate  : 2019-04-22
 */
public class ServiceareaFoodAll {
    private int servicearea_food_allId; // 휴게소 전체 푸드메뉴 일련번호
    private String restCd; // 휴게소코드
    private String stdRestCd; // 표준휴게소코드
    private String stdRestNm; // 표준휴게소이름
    private String seq; // 음식 시퀀스
    private String foodNm; // 음식 이름
    private String foodCost; // 음식 가격
    private String foodMaterial = null; // 요리재료
    private String etc = null; // 상세내역
    private String recommendyn; // 추천메뉴구분
    private String bestfoodyn; // 베스트푸드구분
    private String premiumyn; // 프리미엄메뉴 구분
    private String seasonMenu; // 계절메뉴 구분 (4:4계절,S:여름계절메뉴,W:겨울계절메뉴)
    private String app; // 앱 표출여부
    private String lastDtime; // 최초등록일시
    private String lsttmAltrDttm; // 최종수정일시
    private String regDate; // 작성일
    private String editDate = null; // 수정일
    
    public int getServicearea_food_allId() {
        return servicearea_food_allId;
    }
    public void setServicearea_food_allId(int servicearea_food_allId) {
        this.servicearea_food_allId = servicearea_food_allId;
    }
    public String getRestCd() {
        return restCd;
    }
    public void setRestCd(String restCd) {
        this.restCd = restCd;
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
    public String getSeq() {
        return seq;
    }
    public void setSeq(String seq) {
        this.seq = seq;
    }
    public String getFoodNm() {
        return foodNm;
    }
    public void setFoodNm(String foodNm) {
        this.foodNm = foodNm;
    }
    public String getFoodCost() {
        return foodCost;
    }
    public void setFoodCost(String foodCost) {
        this.foodCost = foodCost;
    }
    public String getFoodMaterial() {
        return foodMaterial;
    }
    public void setFoodMaterial(String foodMaterial) {
        this.foodMaterial = foodMaterial;
    }
    public String getEtc() {
        return etc;
    }
    public void setEtc(String etc) {
        this.etc = etc;
    }
    public String getRecommendyn() {
        return recommendyn;
    }
    public void setRecommendyn(String recommendyn) {
        this.recommendyn = recommendyn;
    }
    public String getBestfoodyn() {
        return bestfoodyn;
    }
    public void setBestfoodyn(String bestfoodyn) {
        this.bestfoodyn = bestfoodyn;
    }
    public String getPremiumyn() {
        return premiumyn;
    }
    public void setPremiumyn(String premiumyn) {
        this.premiumyn = premiumyn;
    }
    public String getSeasonMenu() {
        return seasonMenu;
    }
    public void setSeasonMenu(String seasonMenu) {
        this.seasonMenu = seasonMenu;
    }
    public String getApp() {
        return app;
    }
    public void setApp(String app) {
        this.app = app;
    }
    public String getLastDtime() {
        return lastDtime;
    }
    public void setLastDtime(String lastDtime) {
        this.lastDtime = lastDtime;
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
        return "ServiceareaFoodAll [servicearea_food_allId=" + servicearea_food_allId + ", restCd=" + restCd
                + ", stdRestCd=" + stdRestCd + ", stdRestNm=" + stdRestNm + ", seq=" + seq + ", foodNm=" + foodNm
                + ", foodCost=" + foodCost + ", foodMaterial=" + foodMaterial + ", etc=" + etc + ", recommendyn="
                + recommendyn + ", bestfoodyn=" + bestfoodyn + ", premiumyn=" + premiumyn + ", seasonMenu=" + seasonMenu
                + ", app=" + app + ", lastDtime=" + lastDtime + ", lsttmAltrDttm=" + lsttmAltrDttm + ", regDate="
                + regDate + ", editDate=" + editDate + "]";
    }
    
}
