package project.spring.travel.model;

/**
 * @fileName    : ServiceareaGroup.java
 * @author      : 홍준성
 * @description : 휴게소 완성 테이블을 표현한 JavaBeans
 * @lastUpdate  : 2019. 5. 2.
 */
public class ServiceareaGroup {
    private int servicearea_groupId; // 휴게소 일련번호
    private String serviceareaName; // 휴게소명
    private String direction; // 휴게소 방향
    private String routeName; // 노선명
    private Integer csStat = null; // 충전기상태 (1. 통신이상 2. 충전대기 3. 충전중 4. 운영중지 5. 점검중)
    private String foodBatchMenu = null; // 대표음식
    private String foodSalePrice = null; // 판매가격
    private String imageUrl = null; // 이미지 URL
    private String imageThumbnailUrl = null; // 이미지 썸네일 URL
    private Integer imageWidth = null; // 이미지의 가로 크기
    private Integer imageHeight = null; // 이미지의 세로 크기
    private String oilCompany = null; // 정유사
    private String oilGasolinePrice = null; // 휘발유 가격
    private String oilDiselPrice = null; // 경유 가격
    private String oilLpgYn = null; // LPG 여부
    private String oilLpgPrice = null; // LPG 가격
    private String placeId = null; // 장소 ID
    private String placeX = null; // X 좌표값 혹은 longitude 경도
    private String placeY = null; // Y 좌표값 혹은 latitude 위도
    private String placePhone = null; // 전화번호
    private String placeAddress = null; // 지번주소
    private String placeRoadAddress = null; // 도로명주소
    private String placeUrl = null; // 장소 상세페이지 URL
    private String psName = null; // 편의시설
    private String themeItemName = null; // 테마명
    private String themeDetail = null; // 테마상세내역
    private int serviceareaLike = 0; // 좋아요 갯수
    private String regDate; // 작성일
    private String editDate = null; // 수정일
    // SQL의 Limit절에 사용하기 위한 변수를 JavaBeans에 추가하기(페이지 구현에 쓰임)
    private int limitStart; // 검색 범위의 시작 위치
    private int listCount; // 한 페이지에 보여질 글의 목록 수
    
    public int getServicearea_groupId() {
        return servicearea_groupId;
    }
    public void setServicearea_groupId(int servicearea_groupId) {
        this.servicearea_groupId = servicearea_groupId;
    }
    public String getServiceareaName() {
        return serviceareaName;
    }
    public void setServiceareaName(String serviceareaName) {
        this.serviceareaName = serviceareaName;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public String getRouteName() {
        return routeName;
    }
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
    public Integer getCsStat() {
        return csStat;
    }
    public void setCsStat(Integer csStat) {
        this.csStat = csStat;
    }
    public String getFoodBatchMenu() {
        return foodBatchMenu;
    }
    public void setFoodBatchMenu(String foodBatchMenu) {
        this.foodBatchMenu = foodBatchMenu;
    }
    public String getFoodSalePrice() {
        return foodSalePrice;
    }
    public void setFoodSalePrice(String foodSalePrice) {
        this.foodSalePrice = foodSalePrice;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }
    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
    }
    public Integer getImageWidth() {
        return imageWidth;
    }
    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }
    public Integer getImageHeight() {
        return imageHeight;
    }
    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }
    public String getOilCompany() {
        return oilCompany;
    }
    public void setOilCompany(String oilCompany) {
        this.oilCompany = oilCompany;
    }
    public String getOilGasolinePrice() {
        return oilGasolinePrice;
    }
    public void setOilGasolinePrice(String oilGasolinePrice) {
        this.oilGasolinePrice = oilGasolinePrice;
    }
    public String getOilDiselPrice() {
        return oilDiselPrice;
    }
    public void setOilDiselPrice(String oilDiselPrice) {
        this.oilDiselPrice = oilDiselPrice;
    }
    public String getOilLpgYn() {
        return oilLpgYn;
    }
    public void setOilLpgYn(String oilLpgYn) {
        this.oilLpgYn = oilLpgYn;
    }
    public String getOilLpgPrice() {
        return oilLpgPrice;
    }
    public void setOilLpgPrice(String oilLpgPrice) {
        this.oilLpgPrice = oilLpgPrice;
    }
    public String getPlaceId() {
        return placeId;
    }
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
    public String getPlaceX() {
        return placeX;
    }
    public void setPlaceX(String placeX) {
        this.placeX = placeX;
    }
    public String getPlaceY() {
        return placeY;
    }
    public void setPlaceY(String placeY) {
        this.placeY = placeY;
    }
    public String getPlacePhone() {
        return placePhone;
    }
    public void setPlacePhone(String placePhone) {
        this.placePhone = placePhone;
    }
    public String getPlaceAddress() {
        return placeAddress;
    }
    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }
    public String getPlaceRoadAddress() {
        return placeRoadAddress;
    }
    public void setPlaceRoadAddress(String placeRoadAddress) {
        this.placeRoadAddress = placeRoadAddress;
    }
    public String getPlaceUrl() {
        return placeUrl;
    }
    public void setPlaceUrl(String placeUrl) {
        this.placeUrl = placeUrl;
    }
    public String getPsName() {
        return psName;
    }
    public void setPsName(String psName) {
        this.psName = psName;
    }
    public String getThemeItemName() {
        return themeItemName;
    }
    public void setThemeItemName(String themeItemName) {
        this.themeItemName = themeItemName;
    }
    public String getThemeDetail() {
        return themeDetail;
    }
    public void setThemeDetail(String themeDetail) {
        this.themeDetail = themeDetail;
    }
    public int getServiceareaLike() {
        return serviceareaLike;
    }
    public void setServiceareaLike(int serviceareaLike) {
        this.serviceareaLike = serviceareaLike;
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
    public int getLimitStart() {
        return limitStart;
    }
    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }
    public int getListCount() {
        return listCount;
    }
    public void setListCount(int listCount) {
        this.listCount = listCount;
    }
    
    @Override
    public String toString() {
        return "ServiceareaGroup [servicearea_groupId=" + servicearea_groupId + ", serviceareaName=" + serviceareaName
                + ", direction=" + direction + ", routeName=" + routeName + ", csStat=" + csStat + ", foodBatchMenu="
                + foodBatchMenu + ", foodSalePrice=" + foodSalePrice + ", imageUrl=" + imageUrl + ", imageThumbnailUrl="
                + imageThumbnailUrl + ", imageWidth=" + imageWidth + ", imageHeight=" + imageHeight + ", oilCompany="
                + oilCompany + ", oilGasolinePrice=" + oilGasolinePrice + ", oilDiselPrice=" + oilDiselPrice
                + ", oilLpgYn=" + oilLpgYn + ", oilLpgPrice=" + oilLpgPrice + ", placeId=" + placeId + ", placeX="
                + placeX + ", placeY=" + placeY + ", placePhone=" + placePhone + ", placeAddress=" + placeAddress
                + ", placeRoadAddress=" + placeRoadAddress + ", placeUrl=" + placeUrl + ", psName=" + psName
                + ", themeItemName=" + themeItemName + ", themeDetail=" + themeDetail + ", serviceareaLike="
                + serviceareaLike + ", regDate=" + regDate + ", editDate=" + editDate + ", limitStart=" + limitStart
                + ", listCount=" + listCount + "]";
    }
    
}
