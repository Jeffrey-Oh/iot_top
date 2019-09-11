```java
/** (1) 데이터베이스 접속처리 */
    // -> import org.apache.ibatis.session.SqlSession;
    // -> import study.jsp.myschool.dao.MyBatisConnectionFactory;
    SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
    
    /**
     * ===== 데이터 입력, 수정, 삭제 처리에 대한 Java 구현 패턴 =====
     * - 1) insert, update, delete 중 수행하려는 기능에 대한 메서드
     * - 2) Namespace.id 형식의 기능명
     * - 3) MyBatis에서 parameterType에 명시한 클래스에 대한 객체
     * - 4,5) 사용자에게 표시할 상황에 맞는 에러 메시지 문장
     */
    try {
        int result = sqlSession.[1]("[2]", [3]);
        
        if(result == 0) {
            throw new NullPointerException();
        }
    } catch (NullPointerException e) {
        sqlSession.rollback(); // SQL 수행 내역을 되돌림
        System.out.println("[4]");
        return;
    } catch (Exception e) {
        sqlSession.rollback(); // SQL 수행 내역을 되돌림
        System.out.println(e.getLocalizedMessage());
        System.out.println("[5]");
        return;
    } finally {
        sqlSession.commit(); // 실제 반영을 위해서 commit
        sqlSession.close(); // 데이터베이스 접속 해제
    }
```