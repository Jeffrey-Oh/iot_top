package project.spring.travel.model;

/**
 * @fileName    : Member.java
 * @author      : JoonsungHong
 * @description : 프로그램에서 사용할 member 데이터 구조 정의를 위한 JavaBeans Class
 * @lastUpdate  : 2019-03-25
 */
/**
 * MyBatis 프로젝트에 라이브러리 설정하기
 * 기본 생성자의 사용을 위해서 파라미터가 포함된 생성자를 생략한다.
 * -> MyBatis에서는 본디 이 형태를 권장한다. (코드 단순화)
 */
public class Member {
    // 데이터베이스 테이블의 컬럼을 표현하는 멤버변수
    private int memberId;
    private String userName;
    private String gender;
    private String birthDate;
    private String userId;
    private String userPw;
    private String phone;
    private String email;
    private String postcode;
    private String address1;
    private String address2;
    private String marketingCheckedDate = null;
    private String toEmailCheckedDate = null;
    private String toSmsCheckedDate = null;
    private String regDate;
    private String editDate = null;
    private String deleteDate = null;
    private String grade;
    private String profileImg = null;
    // 회원정보 수정 시 변경할 신규 비밀번호를 저장할 용도로 추가 
    private String newUserPw = null; 
    // SQL의 Limit절에 사용하기 위한 변수를 JavaBeans에 추가하기(페이지 구현에 쓰임)
    private int limitStart;
    private int listCount;
    
    // MyBatise를 위한 기본 생성자
    public Member() {
        super();
    }
    
    /**
     * member 테이블 Beans 생성자
     * @param memberId - 회원일련번호 Primary Key Auto_increment
     * @param userName - 이름
     * @param gender - 성별
     * @param birthDate - 생년월일
     * @param userId - 아이디
     * @param userPw - 비밀번호
     * @param phone - 휴대전화
     * @param email - 이메일
     * @param postcode - 우편번호
     * @param address1 - 주소
     * @param address2 - 상세주소
     * @param marketingCheckedDate - 마케팅 활용 동의여부
     * @param toEmailCheckedDate - 이메일 수신여부
     * @param toSmsCheckedDate - SMS 수신여부
     * @param regDate - 회원 가입일
     * @param editDate - 회원정보 수정일
     * @param deleteDate - 회원 탈퇴일
     * @param grade - 회원 등급
     * @param profileImg - 프로필 이미지
     * @param newUserPw - 회원정보 수정 시 변경할 신규 비밀번호
     * @param limitStart - 검색 범위의 시작 위치
     * @param listCount - 한 페이지에 보여질 글의 목록 수
     */
    public Member(int memberId, String userName, String gender, String birthDate, String userId, String userPw,
            String phone, String email, String postcode, String address1, String address2, String marketingCheckedDate,
            String toEmailCheckedDate, String toSmsCheckedDate, String regDate, String editDate, String deleteDate,
            String grade, String profileImg, String newUserPw, int limitStart, int listCount) {
        super();
        this.memberId = memberId;
        this.userName = userName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.userId = userId;
        this.userPw = userPw;
        this.phone = phone;
        this.email = email;
        this.postcode = postcode;
        this.address1 = address1;
        this.address2 = address2;
        this.marketingCheckedDate = marketingCheckedDate;
        this.toEmailCheckedDate = toEmailCheckedDate;
        this.toSmsCheckedDate = toSmsCheckedDate;
        this.regDate = regDate;
        this.editDate = editDate;
        this.deleteDate = deleteDate;
        this.grade = grade;
        this.profileImg = profileImg;
        this.newUserPw = newUserPw;
        this.limitStart = limitStart;
        this.listCount = listCount;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getMarketingCheckedDate() {
        return marketingCheckedDate;
    }

    public void setMarketingCheckedDate(String marketingCheckedDate) {
        this.marketingCheckedDate = marketingCheckedDate;
    }

    public String getToEmailCheckedDate() {
        return toEmailCheckedDate;
    }

    public void setToEmailCheckedDate(String toEmailCheckedDate) {
        this.toEmailCheckedDate = toEmailCheckedDate;
    }

    public String getToSmsCheckedDate() {
        return toSmsCheckedDate;
    }

    public void setToSmsCheckedDate(String toSmsCheckedDate) {
        this.toSmsCheckedDate = toSmsCheckedDate;
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

    public String getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getNewUserPw() {
        return newUserPw;
    }

    public void setNewUserPw(String newUserPw) {
        this.newUserPw = newUserPw;
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
        return "Member [memberId=" + memberId + ", userName=" + userName + ", gender=" + gender + ", birthDate="
                + birthDate + ", userId=" + userId + ", userPw=" + userPw + ", phone=" + phone + ", email=" + email
                + ", postcode=" + postcode + ", address1=" + address1 + ", address2=" + address2
                + ", marketingCheckedDate=" + marketingCheckedDate + ", toEmailCheckedDate=" + toEmailCheckedDate
                + ", toSmsCheckedDate=" + toSmsCheckedDate + ", regDate=" + regDate + ", editDate=" + editDate
                + ", deleteDate=" + deleteDate + ", grade=" + grade + ", profileImg=" + profileImg + ", newUserPw="
                + newUserPw + ", limitStart=" + limitStart + ", listCount=" + listCount + "]";
    }

}
