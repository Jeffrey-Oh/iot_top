package project.spring.helper;

/**
 * @fileName    : PageHelper.java
 * @author      : 홍준성
 * @description : 페이지 구현에서 사용되는 변수값들을 선언한다.
 * @lastDate    : 2019. 4. 15.
 */
public class PageHelper {
    /** GET파라미터로 처리할 값 */
    private int page = 1; // 현재 페이지 번호
    
    /** DB에서 조회한 결과 값 */
    private int totalCount = 0; // 전체 글 개수 조회
    
    /** 개발자가 정의해야 하는 값 */
    private int listCount = 10; // 한 페이지에 보여질 글의 목록 수
    private int groupCount = 5; // 한 그룹에 표시할 페이지번호 개수
    
    /** 연산처리가 필요한 값 */
    private int totalPage = 0; // 전체 페이지 수
    private int startPage = 0; // 현재 그룹의 시작 페이지
    private int endPage = 0; // 현재 그룹의 마지막 페이지
    private int prevPage = 0; // 이전 그룹의 마지막 페이지
    private int nextPage = 0; // 다음 그룹의 첫 페이지
    private int limitStart = 0; // MySQL의 Limit 시작 위치
    
    /**
     * # 페이지 구현에서 사용되는 변수 살펴보기
     * 
     * ## 전체 페이지 수 구하기
     * `
     * 전체 페이지 수 =
     *     ((전체 게시물 수 -1) / 한 페이지에 보여질 목록 수) + 1
     * `
     * 
     * ## 현재 페이지 그룹의 시작 페이지 번호
     * `
     * 현재 페이지 그룹의 시작 번호 =
     *     ((현재 페이지 -1) / 한 그룹에 표시될 페이지 번호의 수) * 한 그룹에 표시될 페이지 번호의 수 + 1 
     * `
     * 
     * ## 현재 페이지 그룹의 끝 페이지 번호
     * `
     * 현재 페이지 그룹의 끝 번호 =
     *     (((현재 페이지 그룹의 시작 번호 - 1) + 한 그룹에 표시될 페이지 번호의 수) / 한 그룹에 표시될 페이지 번호의 수) * 한 그룹에 표시될 페이지 번호의 수
     * `
     * - 현재 페이지 그룹의 끝 페이지 번호는 전체 페이지 수 보다 클 수 없다.
     * 
     * ## 이전 그룹 이동 버튼
     * `
     * &laquo; // 이전 그룹 이동 버튼
     * `
     * - 이전 그룹 이동 버튼은 이전 그룹의 마지막 페이지로 이동한다.
     *     단, 첫 번째 그룹에서는 동작하지 않는다.
     * - 한 그룹에 보여지는 페이지 번호 링크의 수는
     *     groupCount변수에 저장되어 있다.
     * - 현재 위치하고 있는 그룹의 첫 번째 페이지가 groupCount 보다 크다면,
     *     두 번째 그룹 이상의 위치에 있다는 의미이다.
     *     - 한 그룹에 5개씩 표시한다고 가정 할 때
     *         현재 페이지 그룹의 첫 페이지가 11인 경우
     *         5보다 크기 때문에, 이전 그룹이 존재하며
     *         이전 그룹은 6~10 페이지까지 표시해야 한다.
     * 
     * ## 다음 그룹 이동 버튼
     * `
     * &raquo; // 다음 그룹 이동 버튼
     * `
     * - 다음 그룹 이동 버튼은 다음 그룹의 첫 번째 페이지로 이동한다.
     * - 현재 그룹의 마지막 페이지 번호(endPage)가 전체 페이지 수와 같다면,
     *     현재 그룹에 마지막 페이지가 포함되어 있다는 의미이므로
     *     더 이상의 이후 그룹은 없게 된다.
     * - 반대로 현재 그룹의 마지막 페이지 번호가 전체 페이지 수 보다 작다면,
     *     현재 그룹 이후에 다른 그룹이 더 존재한다는 의미가 된다.
     * 
     * ## MySQL의 Limit 절에 사용될 변수 계산하기
     * - MySQL에서 게시물의 일부만 조회하기 위해서는
     *     다음과 같은 SQL 구문이 수행되어야 한다.
     * `
     * Select * From 테이블이름 Limit 시작위치, 표시할 목록 수
     * `
     * - 표시할 목록 수는 listCount 변수를 사용할 수 있다.
     *     -> 한 ㅔ이지에 표시할 목록 수
     * - Limit 절의 시작 위치는 다음과 같이 계산한다.
     * `
     * 시작위치 = (현재페이지 -1) * 표시할 목록 수
     * `
     */
    
    /**
     * ===== 페이지 번호를 출력하는데 필요한 연산 =====
     */
    /**
     * ===== 페이지 번호 계산을 위한 pageProcess() 메서드의 접근한정자를
     *     public으로 수정 =====
     * - Controller가 필요한 위치에서 페이지 계산을 직접 처리할 수 있도록
     *     접근한정자를 변경한다.
     */
//    private void pageProcess(int page, int totalCount, int listCount, int groupCount) {
    /**
     * 페이지 구현에 필요한 계산식을 처리하는 메서드
     * @param page - 현재 페이지 번호
     * @param totalCount - 전체 글 개수 
     * @param listCount - 한 페이지에 보여질 글의 목록 수
     * @param groupCount - 한 그룹에 표시할 페이지번호 개수
     */
    public void pageProcess(int page, int totalCount, int listCount, int groupCount) {
        this.page = page;
        this.totalCount = totalCount;
        this.listCount = listCount;
        this.groupCount = groupCount;
        
        // 전체 페이지 수
        totalPage = ((totalCount - 1) / listCount) + 1;
        
        // 현재 페이지에 대한 오차 조절
        if(page < 0) {
            page = 1;
        }
        
        if(page > totalPage) {
            page = totalPage;
        }
        
        // 현재 페이징 그룹의 시작 페이지 번호
        startPage = ((page - 1) / groupCount) * groupCount + 1;
        
        // 현재 페이징 그룹의 끝 페이지 번호
//        endPage = (((startPage - 1) + groupCount) / groupCount) * groupCount; // 뒤의 식의 의미를 모르겠으므로 우선 제하고 사용하자.
        endPage = ((startPage - 1) + groupCount);
        
        // 끝 페이지 번호가 전체 페이지수를 초과하면 오차범위 조절
        if(endPage > totalPage) {
            endPage = totalPage;
        }
        
        // 이전 그룹의 마지막 페이지
        if(startPage > groupCount) {
            prevPage = startPage - 1;
        } else {
            prevPage = 0;
        }
        
        // 다음 그룹의 첫 페이지
        if(endPage < totalPage) {
            nextPage = endPage + 1;
        } else {
            nextPage = 0;
        }
        
        // 검색 범위의 시작 위치
        limitStart = (page - 1) * listCount;
        
    } // End pageProcess Method

    /** 
     * ===== 멤버변수를 JSP에게 전달하기 위한 getter/setter 정의 =====
     */
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getListCount() {
        return listCount;
    }

    public void setListCount(int listCount) {
        this.listCount = listCount;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    /**
     * ===== toString() 메서드 재정의 =====
     */
    @Override
    public String toString() {
        return "PageHelper [page=" + page + ", totalCount=" + totalCount + ", listCount=" + listCount + ", groupCount="
                + groupCount + ", totalPage=" + totalPage + ", startPage=" + startPage + ", endPage=" + endPage
                + ", prevPage=" + prevPage + ", nextPage=" + nextPage + ", limitStart=" + limitStart + "]";
    }
    
}
