package project.spring.travel.model;

/**
 * @fileName    : HotKeyword.java
 * @author      : 홍준성
 * @description : 인기검색어의 테이블을 표현한 Beans
 * @lastUpdate  : 2019. 5. 10.
 */
public class HotKeyword {
    private int hot_keywordId; // 인기검색어 일련번호
    private String keyword; // 인기검색어
    private String regDate; // 작성일
    
    public int getHot_keywordId() {
        return hot_keywordId;
    }
    public void setHot_keywordId(int hot_keywordId) {
        this.hot_keywordId = hot_keywordId;
    }
    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public String getRegDate() {
        return regDate;
    }
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
    
    @Override
    public String toString() {
        return "HotKeyword [hot_keywordId=" + hot_keywordId + ", keyword=" + keyword + ", regDate=" + regDate + "]";
    }
    
}
