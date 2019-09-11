package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.Member;

/**
 * @fileName    : MemberService.java
 * @author      : JoonsungHong
 * @description : 프로그램이 요구하는 기능들을 명시한 Interface
 * @lastUpdate  : 2019-04-13
 */
/**
 * 회원 관리를 위한 비즈니스 로직
 * 프로그램이 요구하는 기능들을 명시한 Interface
 * 이 Interface의 구현체(~Impl) 클래스에서는 세부기능을 구현해야 한다.
 * 인터페이스에서는 프로그램의 초기 컨셉 설정 단계에서 도출한
 * 요구사항을 메서드로 정의한다.
 * 각각의 요구사항은 동작단위에 따라 그룹으로 나뉘게 되는데
 * 인터페이스가 그룹의 역할을 한다.
 * ex)회원관리(인터페이스) -> 메서드: 가입, 탈퇴, 수정, 로그인 등
 * 회원 관리 기능과 관련된 MyBatis Mapper를 간접적으로 호출하기 위한
 * 기능 명세.
 * 하나의 처리를 위해서 두 개 이상의 기능을 연동할 필요가 있을 경우,
 * 이 인터페이스의 구현체(Impl)을 통해서 처리한다.
 */
public interface MemberService {
    /**
     * # 회원가입(본인인증 및 가입 가능 여부 검사)
     * 조회 시도 시 정보를 조회하기 위한 메서드 정의.
     * 조회 회원의 정보가 있으면 userName을 리턴한다.
     * 
     * 조회된 행의 수가 0이거나, SQL에 에러가 있는 경우는
     * 예외를 발생시켜서, 이 메서드를 호출하는 위치에서 try~catch
     * 구문을 강제적으로 사용하도록 throws를 명시한다.
     * @param member - 조회될 정보를 담고 있는 Beans
     * @return Member - 조회된 데이터
     * @throws Exception - SQL처리에 실패한 경우
     */
    public Member memberCheck(Member member) throws Exception;
    
    /**
     * 이메일 중복검사
     * @param member - 이메일
     * @return int
     */
    public int emailCheck(Member member) throws Exception;
    
    /**
     * 아이디 중복검사
     * @param member - 아이디
     * @return int
     * @throws Exception - 중복된 데이터인 경우 예외 발생함
     */
    public int userIdCheck(Member member) throws Exception;
    
    /**
     * 데이터 추가(회원 저장)
     * 회원가입 기능에 대한 요구사항을 수행하기 위한 메서드 정의.
     * 회원 한명에 대한 입력 정보를 저장한다.
     * 입력한 정보와 일치하는 데이터가이미 존재한다면
     * 저장하지 않도록 수행해야 한다.
     * 
     * 정보를 새로 저장하고 저장된 정보를 조회하여 리턴한다.
     * 저장된 행의 수가 0이거나, SQL에 에러가 있는 경우는
     * 예외를 발생시켜서, 이 메서드를 호출하는 위치에서 try~catch
     * 구문을 강제적으로 사용하도록 throws를 명시한다.
     * @param member - 저장될 정보를 담고 있는 Beans
     * @throws Exception - SQL처리에 실패한 경우
     */
    public void addMember(Member member)
        throws Exception;
    
    /**
     * 비밀번호 검사
     * @param member
     * @throws Exception
     */
    public void selectMemberPasswordCount(Member member) throws Exception;
    
    /**
     * 데이터 수정(회원 수정)
     * 회원정보 수정에 대한 요구사항을 수행하기 위한 메서드 정의.
     * 회원 한명에 대한 수정 정보를 저장한다.
     * 
     * 수정된 정보를 저장하고 저장된 정보를 조회하여 리턴한다.
     * 저장된 행의 수가 0이거나, SQL에 에러가 있는 경우는
     * 예외를 발생시켜서, 이 메서드를 호출하는 위치에서 try~catch
     * 구문을 강제적으로 사용하도록 throws를 명시한다.
     * @param member - 수정될 정보를 담고 있는 Beans
     * @throws Exception - SQL처리에 실패한 경우
     */
    public void editMember(Member member) throws Exception;
    
    /**
     * 일련번호에 의한 회원정보 조회
     * @param member - 조회할 회원의 일련번호를 담은 Beans
     * @return Member - 조회된 회원의 정보를 담은 Benas
     * @throws Exception
     */
    public Member selectMember(Member member) throws Exception;
    
    /**
     * 회원탈퇴
     * @param member - 회원번호, 비밀번호
     * @throws Exception
     */
    public void deleteMember(Member member) throws Exception;
    
    
    /**
     * 데이터 삭제(휴면 계정에 대해서만)
     * 휴면 계정이 4년간 활동이 없을 경우 회원 탈퇴 진행을 수행하기 위한 메서드 정의.
     * 휴면 계정을 삭제한다.
     * 
     * 삭제될 정보를 조회하고 리턴하고 정보를 삭제한다.
     * 삭제된 행의 수가 0이거나, SQL에 에러가 있는 경우는
     * 예외를 발생시켜서, 이 메서드를 호출하는 위치에서 try~catch
     * 구문을 강제적으로 사용하도록 throws를 명시한다.
     * @param member - 삭제될 정보를 담고 있는 Beans
     * @throws Exception - SQL처리에 실패한 경우
     */
    public void inactiveMemberDelete(Member member) throws Exception;
    
    /**
     * 데이터 수정(계정 활성으로 전환하기)
     * 회원정보 수정에 대한 요구사항을 수행하기 위한 메서드 정의.
     * 회원 한명에 대한 수정 정보를 저장한다.
     * 
     * 수정된 정보를 저장하고 저장된 정보를 조회하여 리턴한다.
     * 저장된 행의 수가 0이거나, SQL에 에러가 있는 경우는
     * 예외를 발생시켜서, 이 메서드를 호출하는 위치에서 try~catch
     * 구문을 강제적으로 사용하도록 throws를 명시한다.
     * @param member - 수정될 정보를 담고 있는 Beans
     * @throws Exception - SQL처리에 실패한 경우
     */
    public void activeMember(Member member) throws Exception;
    
    /**
     * 데이터 조회(아이디 찾기)
     * 아이디 찾기 시도 시 정보를 조회하기 위한 메서드 정의.
     * 조회 회원의 정보를 리턴한다.
     * 
     * 조회된 행의 수가 0이거나, SQL에 에러가 있는 경우는
     * 예외를 발생시켜서, 이 메서드를 호출하는 위치에서 try~catch
     * 구문을 강제적으로 사용하도록 throws를 명시한다.
     * @param member - 조회될 정보를 담고 있는 Beans
     * @return Member - 조회된 데이터가 저장된 Beans
     * @throws Exception - SQL처리에 실패한 경우
     */
    public Member userIdSearch(Member member) throws Exception;
    
    /**
     * 데이터 조회(비밀번호 찾기)
     * 비밀번호 찾기 시도 시 정보를 조회하기 위한 메서드 정의.
     * 조회 회원의 정보를 리턴한다.
     * 
     * 조회된 행의 수가 0이거나, SQL에 에러가 있는 경우는
     * 예외를 발생시켜서, 이 메서드를 호출하는 위치에서 try~catch
     * 구문을 강제적으로 사용하도록 throws를 명시한다.
     * @param member - 조회될 정보를 담고 있는 Beans
     * @return Member - 조회된 데이터가 저장된 Beans
     * @throws Exception - SQL처리에 실패한 경우
     */
    public Member userPwSearch(Member member) throws Exception;
    
    /**
     * 데이터 수정(비밀번호 재설정)
     * 비밀번호 재설정 시도 시 정보를 수정하기 위한 메서드 정의.
     * 수정 회원의 정보를 리턴한다.
     * 
     * 수정된 행의 수가 0이거나, SQL에 에러가 있는 경우는
     * 예외를 발생시켜서, 이 메서드를 호출하는 위치에서 try~catch
     * 구문을 강제적으로 사용하도록 throws를 명시한다.
     * @param member - 수정될 정보를 담고 있는 Beans
     * @throws Exception - SQL처리에 실패한 경우
     */
    public void userPwChange(Member member) throws Exception;
    
    /**
     * # 회원정보 수정
     * # 회원 비활성
     * # 회원 로그인
     * 
     * 조회된 행의 수가 0이거나, SQL에 에러가 있는 경우는
     * 예외를 발생시켜서, 이 메서드를 호출하는 위치에서 try~catch
     * 구문을 강제적으로 사용하도록 throws를 명시한다.
     * @param member - 조회될 정보를 담고 있는 Beans
     * @return Member - 조회된 데이터가 저장된 Beans
     * @throws Exception - SQL처리에 실패한 경우
     */
    public Member memberSearch(Member member) throws Exception;
    
    /**
     * 회원 목록을 조회
     * @param member - 조회할 회원을 담고 있는 Beans
     * @return List<Member> - 조회된 회원 목록이 저장된 Beans
     * @throws Exception
     */
    public List<Member> selectMemberList(Member member) throws Exception;
    
    /**
     * 전체 회원 목록 수를 조회하기 위한 기능
     * @param member
     * @return int - 조회 결과
     * @throws Exception
     */
    public int selectMemberCount(Member member) throws Exception;
    
    /**
     * 회원 등급을 수정하기
     * @param member - 일련번호와 등급을 담은 Beans
     * @throws Exception
     */
    public void updateMemberGrade(Member member) throws Exception;
    
}
