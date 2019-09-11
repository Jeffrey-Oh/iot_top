package project.spring.travel.model;

/**
 * @fileName    : ServiceareaOil.java
 * @author      : 홍준성
 * @description : 휴게소의 유가정보 데이터가 들어갈 Beans Class
 * @lastUpdate  : 2019-04-22
 */
public class ServiceareaOil {
    private int servicearea_oilId; // 휴게소 유가정보 일련번호
    private String routeCode; // 노선코드
    private String routeName; // 노선명
    private String direction; // 방향
    private String serviceAreaCode; // 휴게소코드
    private String serviceAreaName; // 휴게소명
    private String oilCompany; // 정유사 
    private String telNo; // 전화번호
    private String gasolinePrice; // 휘발유가격
    private String diselPrice; // 경유가격
    private String lpgYn; // LPG여부
    private String lpgPrice; // LPG가격
    private String regDate; // 작성일
    private String editDate = null; // 수정일
    
    public int getServicearea_oilId() {
        return servicearea_oilId;
    }
    public void setServicearea_oilId(int servicearea_oilId) {
        this.servicearea_oilId = servicearea_oilId;
    }
    public String getRouteCode() {
        return routeCode;
    }
    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }
    public String getRouteName() {
        return routeName;
    }
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public String getServiceAreaCode() {
        return serviceAreaCode;
    }
    public void setServiceAreaCode(String serviceAreaCode) {
        this.serviceAreaCode = serviceAreaCode;
    }
    public String getServiceAreaName() {
        return serviceAreaName;
    }
    public void setServiceAreaName(String serviceAreaName) {
        this.serviceAreaName = serviceAreaName;
    }
    public String getOilCompany() {
        return oilCompany;
    }
    public void setOilCompany(String oilCompany) {
        this.oilCompany = oilCompany;
    }
    public String getTelNo() {
        return telNo;
    }
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
    public String getGasolinePrice() {
        return gasolinePrice;
    }
    public void setGasolinePrice(String gasolinePrice) {
        this.gasolinePrice = gasolinePrice;
    }
    public String getDiselPrice() {
        return diselPrice;
    }
    public void setDiselPrice(String diselPrice) {
        this.diselPrice = diselPrice;
    }
    public String getLpgYn() {
        return lpgYn;
    }
    public void setLpgYn(String lpgYn) {
        this.lpgYn = lpgYn;
    }
    public String getLpgPrice() {
        return lpgPrice;
    }
    public void setLpgPrice(String lpgPrice) {
        this.lpgPrice = lpgPrice;
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
        return "ServiceareaOil [servicearea_oilId=" + servicearea_oilId + ", routeCode=" + routeCode + ", routeName="
                + routeName + ", direction=" + direction + ", serviceAreaCode=" + serviceAreaCode + ", serviceAreaName="
                + serviceAreaName + ", oilCompany=" + oilCompany + ", telNo=" + telNo + ", gasolinePrice="
                + gasolinePrice + ", diselPrice=" + diselPrice + ", lpgYn=" + lpgYn + ", lpgPrice=" + lpgPrice
                + ", regDate=" + regDate + ", editDate=" + editDate + "]";
    }
    
}
