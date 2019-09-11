package project.spring.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @file_name	: DBHelper.java
 * @author 		: JEFFREY_OH
 * @description : 데이터베이스 접속 및 해제처리를 위한 Helper
 * @last_update : 2019-01-01 
 */
public class DBHelper {

	private Connection conn = null; // null로 초기화 한다.

	/**
	 * 데이터베이스 접속 후, 접속 객체를 리턴한다.
	 * @param db_hostname	 - 데이터베이스 호스트이름
	 * @param db_portnumber	 - 데이터베이스 포트
	 * @param db_database	 - 데이터베이스 이름
	 * @param db_charset	 - 데이터베이스 인코딩
	 * @param db_username	 - 데이터베이스 접속계정
	 * @param db_password	 - 데이터베이스 접속비밀번호
	 * @return conn 		 - 데이터베이스 접속
	 */
	public Connection open(String db_hostname, int db_portnumber, String db_database, String db_charset,
			String db_username, String db_password) {
		// 중복 실행될 경우 발생될 문제를 방지하기 위하여,
		// Connection 객체가 null인 경우만 처리하도록 if문으로 구성
		if (conn == null) {
			/** 데이터 베이스 접속 처리 */
			// 사용하려는 데이터베이스명을 포함한 URL 기술
			// -> jdbc:mysql://localhost:3306/myschool?&characterEncoding=utf8
			String urlFormat = "jdbc:mysql://%s:%d/%s?&characterEncoding=%s";
			String url = String.format(urlFormat, db_hostname, db_portnumber, db_database, db_charset);

			// 접속 과정에서 예외처리가 요구된다.
			try {
				// MySQL JDBC의 드라이버 클래스를 로딩해서 DriverManager클래스에 등록한다.
				Class.forName("com.mysql.jdbc.Driver");

				// DriverManager 객체를 사용하여 DB에 접속한다.
				// -> 접속 URL, 아이디, 비밀번호를 전달
				// -> DriverManager에 등록된 Driver 객체를 사용하여 DB에 접속 후,
				// Connection 객체를 리턴받는다.
				// -> import java.sql.DriverManager 필요함
				conn = DriverManager.getConnection(url, db_username, db_password);

				// 성공시 메시지 출력
				System.out.println("=== DATABASE Connect Success ===");

			} catch (ClassNotFoundException e) {

				// 실패시 메시지와 에러 내용 출력
				System.out.println("=== DATABASE Connect Fail ===");
				System.out.println(e.getMessage());

			} catch (SQLException e) {

				// 실패시 메시지와 에러 내용 출력
				System.out.println("=== DATABASE Connect Fail ===");
				System.out.println(e.getMessage());
			}
		}

		return conn;
	}

	/** 데이터베이스의 접속을 해제한다. */
	public void close() {
		if (conn != null) {
			/** 데이터베이스 접속 해제 처리 */
			try {
				conn.close();
				System.out.println("=== DATABASE Disconnect Success ===");
			} catch (Exception e) {
				System.out.println("=== DATABASE Disconnect Fail ===");
				System.out.println(e.getMessage());
			}

		}
	}
}
