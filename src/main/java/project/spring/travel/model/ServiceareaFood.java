package project.spring.travel.model;

/**
 * @fileName    : ServiceareaFood.java
 * @author      : 홍준성
 * @description : 휴게소의 대표음식 데이터가 들어갈 Beans Class
 * @lastUpdate  : 2019-04-22
 */
public class ServiceareaFood {
    private int servicearea_foodId; // 휴게소 대표음식 일련번호
    private String serviceAreaCode; // 휴게소코드
    private String serviceAreaName; // 휴게소명
    private String routeCode; // 노선코드
    private String routeName; // 노선명
    private String direction; // 방향
    private String batchMenu = null; // 대표음식
    private String salePrice = null; // 판매가격
    private String regDate; // 작성일
    private String editDate = null; // 수정일
    
    public int getServicearea_foodId() {
        return servicearea_foodId;
    }
    public void setServicearea_foodId(int servicearea_foodId) {
        this.servicearea_foodId = servicearea_foodId;
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
    public String getBatchMenu() {
        return batchMenu;
    }
    public void setBatchMenu(String batchMenu) {
        this.batchMenu = batchMenu;
    }
    public String getSalePrice() {
        return salePrice;
    }
    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
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
        return "ServiceareaFood [servicearea_foodId=" + servicearea_foodId + ", serviceAreaCode=" + serviceAreaCode
                + ", serviceAreaName=" + serviceAreaName + ", routeCode=" + routeCode + ", routeName=" + routeName
                + ", direction=" + direction + ", batchMenu=" + batchMenu + ", salePrice=" + salePrice + ", regDate="
                + regDate + ", editDate=" + editDate + "]";
    }
    
}
