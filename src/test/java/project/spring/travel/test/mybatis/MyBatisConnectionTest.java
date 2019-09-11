package project.spring.travel.test.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @fileName    : MyBatisConnectTest.java
 * @author      : 홍준성
 * @description : MyBatis를 통한 데이터베이스 접속 여부 단위 테스트
 *     - sqlSession 객체를 root-context.xml에 의해 주입받기만 하면
 *         DB접속은 완료된다.
 * @lastUpdate  : 2019. 4. 15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class MyBatisConnectionTest {
    // 데이터베이스 접속처리
    // -> import org.apache.ibatis.session.SqlSession;
    @Autowired
    SqlSession sqlSession;
    
    @Test
    public void testFactory() {
        System.out.println("----- MyBatis Database 연결 성공 -----");
        
        // -> close처리는 자동화된다.
        // sqlSession.close();
        /**
         * End
         * ### close() 기능 호출 금지
         * - 코드상에서 직접 sqlSession을 close할 경우
         *     데이터베이스 접속 해제 기능은 (Spring에 의해서 자동화 되므로)
         *     개발자에 의해서 호출될 수 없다는 에러 메시지가 표시된다.
         */
    }
    
    /** 1차 작업 후, 이 클래스에서 에러가 발생한 경우 다음을 확인 */
    // 1) log4js.xml 파일에서 로그가 저장될 경로 설정이 정확한지 여부
    // 2) config.xml 파일에서 데이터베이스 접속 정보 설정 확인
    // 3) MyBatisConnectionFactory에서 config.xml의 경로 설정 확인
    
    /** Mapper 파일의 경로를 config.xml에 추가할 때 마다 이 클래스로 테스트 한다. */
    // 4) Mapper 추가 후, 에러가 발생할 경우
    //     - config.xml 파일에 명시한 경로를 확인
    //     - Mapper.xml의 파일이름 확인
    //     - Mapper.xml에 추가한 resultMap의 type 속성에 대한 Beans 경로 정상 여부
}
